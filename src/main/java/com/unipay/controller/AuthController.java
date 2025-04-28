package com.unipay.controller;


import com.unipay.command.UserRegisterCommand;
import com.unipay.mapper.UserMapper;
import com.unipay.response.UserRegistrationResponse;
import com.unipay.service.authentication.AuthenticationService;
import com.unipay.service.mail.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final UserMapper userMapper;

    /**
     * Endpoint for registering a new user along with their profile and settings.
     * This method accepts a UserRegisterCommand containing the user's registration details,
     * validates the information, and invokes the UserService to create the user in the system.
     *
     * OpenAPI documentation is included for automatic generation of API docs.
     *
     * @param command The UserRegisterCommand object containing the user's registration data.
     * @return A ResponseEntity containing a UserRegistrationResponse with a success message.
     */
    @Operation(
            summary = "Register a new user",
            description = "Creates a user along with profile and settings",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserRegistrationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(
            @RequestBody UserRegisterCommand command, HttpServletRequest request
    ) {
        authenticationService.register(command, request);
        return ResponseEntity.ok(new UserRegistrationResponse("User registered successfully"));
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        emailService.confirmRegistration(confirmationToken);
        return ResponseEntity.ok("Email verified successfully!");
    }
}
