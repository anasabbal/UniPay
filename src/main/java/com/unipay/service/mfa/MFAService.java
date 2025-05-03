package com.unipay.service.mfa;

import com.unipay.exception.QrGenerationException;
import com.unipay.models.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Service interface for managing Multi-Factor Authentication (MFA) operations.
 */
public interface MFAService {

    /**
     * Enables MFA for the given user after verifying the provided code.
     *
     * @param user the user enabling MFA
     * @param code the verification code from the authenticator app
     */
    void enableMfa(User user, String code);

    /**
     * Disables MFA for the given user.
     *
     * @param user the user disabling MFA
     */
    void disableMfa(User user);

    /**
     * Generates a QR code image for the user's MFA setup.
     *
     * @param user the user for whom the QR code is generated
     * @return a BufferedImage of the QR code
     */
    BufferedImage generateQrCodeImage(User user) throws QrGenerationException;

    /**
     * Converts the provided BufferedImage to a byte array.
     *
     * @param image the image to convert
     * @return the byte array representation of the image
     * @throws IOException if an I/O error occurs during image processing
     */
    byte[] getImageData(BufferedImage image) throws IOException;

    /**
     * Generates new MFA recovery codes for the user.
     *
     * @param user the user for whom to generate recovery codes
     * @return a list of recovery codes
     */
    List<String> generateRecoveryCodes(User user);

    /**
     * Returns the count of unused recovery codes for the user.
     *
     * @param user the user
     * @return the number of remaining recovery codes
     */
    int getRemainingRecoveryCodes(User user);

    /**
     * Validates the MFA code provided by the user.
     *
     * @param user the user validating the code
     * @param code the MFA code from the authenticator app
     * @return true if valid, false otherwise
     */
    boolean validateCode(User user, String code);

    /**
     * Validates a recovery code provided by the user.
     *
     * @param user the user validating the recovery code
     * @param code the recovery code
     * @return true if valid, false otherwise
     */
    boolean validateRecoveryCode(User user, String code);
}
