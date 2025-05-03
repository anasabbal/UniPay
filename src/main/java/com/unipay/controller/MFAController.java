package com.unipay.controller;

import com.unipay.exception.BusinessException;
import com.unipay.exception.ExceptionPayloadFactory;
import com.unipay.exception.QrGenerationException;
import com.unipay.models.User;
import com.unipay.payload.UserDetailsImpl;
import com.unipay.service.mfa.MFAService;
import com.unipay.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/users/{userId}/mfa")
@RequiredArgsConstructor
public class MFAController {

    private final MFAService mfaService;
    private final UserService userService;

    @PostMapping("/enable")
    @Operation(
            summary = "Enable MFA for user",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "MFA enabled successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid MFA code"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("#userId == principal.id")
    public void enableMfa(
            @PathVariable String userId,
            @RequestBody String code,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            validateUserAccess(userId, userDetails);
            User user = userService.getUserById(userId);
            mfaService.enableMfa(user, code);
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/disable")
    @Operation(
            summary = "Disable MFA for user",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "MFA disabled successfully"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("#userId == principal.id")
    public void disableMfa(
            @PathVariable String userId,
            @AuthenticationPrincipal User currentUser
    ) {
        User user = userService.getUserById(userId);
        mfaService.disableMfa(user);
    }

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(
            summary = "Get MFA QR code",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("#userId == principal.id")
    public byte[] getMfaQrCode(
            @PathVariable String userId,
            @AuthenticationPrincipal User currentUser,
            HttpServletResponse response
    ) {
        try {
            User user = userService.getUserById(userId);
            response.setHeader("Content-Disposition", "attachment; filename=\"mfa-qrcode.png\"");
            return mfaService.getImageData(mfaService.generateQrCodeImage(user));
        } catch (IOException | BusinessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate QR code");
        } catch (QrGenerationException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/recovery-codes")
    @Operation(
            summary = "Generate new recovery codes",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "New recovery codes generated"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasAuthority('ROLE_USER') and #userId == principal.id")
    public RecoveryCodesResponse generateRecoveryCodes(
            @PathVariable String userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            validateUserAccess(userId, userDetails);
            final User user = userDetails.getUser();
            List<String> codes = mfaService.generateRecoveryCodes(user);
            return new RecoveryCodesResponse(codes);
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/recovery-codes")
    @Operation(
            summary = "Get remaining recovery codes",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Remaining recovery codes count"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("#userId == principal.id")
    public RecoveryCodesResponse getRecoveryCodes(
            @PathVariable String userId,
            @AuthenticationPrincipal User currentUser
    ) {
        User user = userService.getUserById(userId);
        if (user.getMfaSettings() == null) {
            throw new BusinessException(ExceptionPayloadFactory.MFA_NOT_ENABLED.get());
        }
        return new RecoveryCodesResponse(user.getMfaSettings().getRecoveryCodes().size());
    }
    private void validateUserAccess(String userId, UserDetailsImpl userDetails) {
        if (!userDetails.getId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized access attempt");
        }
    }

    // DTOs
    public record RecoveryCodesResponse(List<String> codes, int remaining) {
        public RecoveryCodesResponse(List<String> codes) {
            this(codes, codes.size());
        }

        public RecoveryCodesResponse(int remaining) {
            this(null, remaining);
        }
    }
}
