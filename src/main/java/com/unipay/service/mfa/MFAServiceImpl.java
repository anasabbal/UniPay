package com.unipay.service.mfa;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.models.MFASettings;
import com.unipay.models.User;
import com.unipay.repository.UserRepository;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MFAServiceImpl implements MFAService{

    private final UserRepository userRepository;
    private final SecretGenerator secretGenerator = new DefaultSecretGenerator();
    private final TimeProvider timeProvider = new SystemTimeProvider();
    private final CodeGenerator codeGenerator = new DefaultCodeGenerator();
    private final CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    private final SecureRandom secureRandom = new SecureRandom();

    // Generate new MFA secret for a user
    @Transactional
    public String generateNewSecret(User user) {
        String secret = secretGenerator.generate();
        user.getMfaSettings().setSecret(secret);
        userRepository.save(user);
        return secret;
    }

    // Generate QR code image for authenticator apps
    public BufferedImage generateQrCodeImage(User user) throws QrGenerationException {
        String secret = getOrGenerateSecret(user);
        String appName = "UniPay";
        QrData data = new QrData.Builder()
                .label(user.getEmail())
                .secret(secret)
                .issuer(appName)
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    data.getUri(),
                    BarcodeFormat.QR_CODE,
                    250, 250
            );
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new QrGenerationException("Failed to generate QR code", e);
        }
    }
    // Validate user-provided TOTP code
    @Transactional
    public boolean validateCode(User user, String code) {
        MFASettings mfaSettings = user.getMfaSettings();
        if (mfaSettings == null || !mfaSettings.isEnabled()) {
            throw new BusinessException(ExceptionPayloadFactory.MFA_NOT_ENABLED.get());
        }

        return verifier.isValidCode(mfaSettings.getSecret(), code);
    }

    // Generate new recovery codes (replace existing ones)
    @Transactional
    public List<String> generateRecoveryCodes(User user) {
        MFASettings mfaSettings = user.getMfaSettings();
        if (mfaSettings == null) {
            throw new BusinessException(ExceptionPayloadFactory.MFA_NOT_ENABLED.get());
        }

        List<String> codes = secureRandom.ints(10, 100000, 999999)
                .mapToObj(i -> String.format("%06d", i))
                .collect(Collectors.toList());

        // Hash codes before storing
        List<String> hashedCodes = codes.stream()
                .map(this::hashCode)
                .collect(Collectors.toList());

        mfaSettings.setRecoveryCodes(hashedCodes);
        userRepository.save(user);
        return codes;
    }

    // Validate recovery code
    @Transactional
    public boolean validateRecoveryCode(User user, String code) {
        MFASettings mfaSettings = user.getMfaSettings();
        if (mfaSettings == null) {
            return false;
        }

        String hashedCode = hashCode(code);
        boolean valid = mfaSettings.getRecoveryCodes().contains(hashedCode);

        if (valid) {
            // Remove used code
            mfaSettings.getRecoveryCodes().remove(hashedCode);
            userRepository.save(user);
        }

        return valid;
    }

    // Enable MFA for a user
    @Transactional
    public void enableMfa(User user, String code) {
        if (user.getMfaSettings() == null) {
            throw new BusinessException(ExceptionPayloadFactory.MFA_NOT_SET_UP.get());
        }

        if (validateCode(user, code)) {
            user.getMfaSettings().setEnabled(true);
            userRepository.save(user);
        } else {
            throw new BusinessException(ExceptionPayloadFactory.INVALID_MFA_CODE.get());
        }
    }

    // Disable MFA for a user
    @Transactional
    public void disableMfa(User user) {
        user.setMfaSettings(null);
        userRepository.save(user);
    }

    private String getOrGenerateSecret(User user) {
        MFASettings mfaSettings = user.getMfaSettings();
        if (mfaSettings == null || mfaSettings.getSecret() == null) {
            String secret = secretGenerator.generate();
            user.setMfaSettings(MFASettings.create(false, secret, user));
            userRepository.save(user);
            return secret;
        }
        return mfaSettings.getSecret();
    }

    private String hashCode(String code) {
        Base32 base32 = new Base32();
        return base32.encodeToString(code.getBytes(StandardCharsets.UTF_8));
    }

    // Utility method to convert BufferedImage to byte array
    public byte[] getImageData(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
