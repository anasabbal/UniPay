package com.unipay.service.mfa;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.exception.QrGenerationException;
import com.unipay.models.MFASettings;
import com.unipay.models.User;
import com.unipay.repository.UserRepository;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import static com.unipay.constants.Constants.*;



/**
 * Service implementation for managing Multi-Factor Authentication (MFA) for users.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MFAServiceImpl implements MFAService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretGenerator secretGenerator = new DefaultSecretGenerator();
    private final TimeProvider timeProvider = new SystemTimeProvider();
    private final CodeGenerator codeGenerator = new DefaultCodeGenerator();
    private final CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    private final SecureRandom secureRandom = new SecureRandom();


    /**
     * Enables MFA for the given user after verifying the provided TOTP code.
     *
     * @param user The user to enable MFA for.
     * @param code The TOTP code to validate before enabling MFA.
     * @throws BusinessException if MFA is not set up or the code is invalid.
     */
    @Override
    @Transactional
    public void enableMfa(User user, String code) {
        if (user.getMfaSettings() == null) {
            throw new BusinessException(ExceptionPayloadFactory.MFA_NOT_SET_UP.get());
        }
        if (!validateCode(user, code)) {
            throw new BusinessException(ExceptionPayloadFactory.INVALID_MFA_CODE.get());
        }
        user.getMfaSettings().setEnabled(true);
        userRepository.save(user);
    }

    /**
     * Disables MFA for the given user by clearing their MFA settings.
     *
     * @param user The user to disable MFA for.
     */
    @Override
    @Transactional
    public void disableMfa(User user) {
        user.setMfaSettings(null);
        userRepository.save(user);
    }

    /**
     * Generates a QR code image to be scanned by an authenticator app.
     *
     * @param user The user for whom the QR code is generated.
     * @return A BufferedImage containing the QR code.
     * @throws QrGenerationException If the QR code generation fails.
     */
    @Override
    public BufferedImage generateQrCodeImage(User user) throws QrGenerationException {
        String secret = getOrGenerateSecret(user);
        QrData data = new QrData.Builder()
                .label(user.getEmail())
                .secret(secret)
                .issuer("UniPay")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(data.getUri(), BarcodeFormat.QR_CODE, QR_IMAGE_SIZE, QR_IMAGE_SIZE);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new QrGenerationException("Failed to generate QR code", e);
        }
    }

    /**
     * Converts a BufferedImage (e.g., QR code) to a byte array in PNG format.
     *
     * @param image The image to convert.
     * @return A byte array representing the PNG image.
     * @throws IOException If an error occurs during image writing.
     */
    @Override
    public byte[] getImageData(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        }
    }

    /**
     * Generates a list of new MFA recovery codes for the user.
     * These are hashed and stored securely.
     *
     * @param user The user to generate codes for.
     * @return A list of plaintext recovery codes.
     * @throws BusinessException If MFA is not enabled.
     */
    @Override
    @Transactional
    public List<String> generateRecoveryCodes(User user) {
        ensureMfaEnabled(user);

        List<String> codes = secureRandom.ints(RECOVERY_CODE_COUNT, 100000, 999999)
                .mapToObj(i -> String.format("%0" + RECOVERY_CODE_LENGTH + "d", i))
                .collect(Collectors.toList());

        List<String> hashedCodes = codes.stream()
                .map(this::hashCode)
                .collect(Collectors.toList());

        user.getMfaSettings().setRecoveryCodes(hashedCodes);
        userRepository.save(user);
        return codes;
    }

    /**
     * Returns the number of remaining (unused) recovery codes for the user.
     *
     * @param user The user to query.
     * @return Number of remaining recovery codes.
     * @throws BusinessException If MFA is not enabled.
     */
    @Override
    @Transactional(readOnly = true)
    public int getRemainingRecoveryCodes(User user) {
        ensureMfaEnabled(user);
        return user.getMfaSettings().getRecoveryCodes().size();
    }

    /**
     * Validates a TOTP code provided by the user.
     *
     * @param user The user for whom the code is validated.
     * @param code The code to validate.
     * @return True if valid, false otherwise.
     * @throws BusinessException If MFA is not enabled.
     */
    @Override
    @Transactional
    public boolean validateCode(User user, String code) {
        ensureMfaEnabled(user);
        return verifier.isValidCode(user.getMfaSettings().getSecret(), code);
    }

    /**
     * Validates and consumes a recovery code.
     * If matched, the code is removed from storage.
     *
     * @param user The user using the recovery code.
     * @param code The code provided by the user.
     * @return True if valid and consumed, false otherwise.
     */
    @Override
    @Transactional
    public boolean validateRecoveryCode(User user, String code) {
        MFASettings settings = user.getMfaSettings();
        if (settings == null || settings.getRecoveryCodes().isEmpty()) return false;

        List<String> codes = settings.getRecoveryCodes();
        for (int i = 0; i < codes.size(); i++) {
            if (passwordEncoder.matches(code, codes.get(i))) {
                codes.remove(i);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the existing MFA secret or generates a new one if missing.
     *
     * @param user The user to fetch or generate the secret for.
     * @return The MFA secret.
     */
    private String getOrGenerateSecret(User user) {
        MFASettings settings = user.getMfaSettings();
        if (settings == null || settings.getSecret() == null) {
            String secret = secretGenerator.generate();
            user.setMfaSettings(MFASettings.create(false, secret, user));
            userRepository.save(user);
            return secret;
        }
        return settings.getSecret();
    }

    /**
     * Ensures that the user has MFA enabled, otherwise throws an exception.
     *
     * @param user The user to check.
     * @throws BusinessException If MFA is not enabled.
     */
    private void ensureMfaEnabled(User user) {
        MFASettings settings = user.getMfaSettings();
        if (settings == null || !settings.isEnabled()) {
            throw new BusinessException(ExceptionPayloadFactory.MFA_NOT_ENABLED.get());
        }
    }

    /**
     * Hashes a recovery code using Base32 encoding.
     *
     * @param code The code to hash.
     * @return The Base32-encoded string.
     */
    private String hashCode(String code) {
        Base32 base32 = new Base32();
        return base32.encodeToString(code.getBytes(StandardCharsets.UTF_8));
    }
}
