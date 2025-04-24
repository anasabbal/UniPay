package com.unipay.controller;

import com.unipay.command.UserRegisterCommand;
import com.unipay.mapper.UserMapper;
import com.unipay.response.UserRegistrationResponse;
import com.unipay.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UserController is responsible for handling HTTP requests related to user management.
 * It provides an endpoint for user registration and interacts with the UserService
 * to create a new user, profile, and settings in the system.
 *
 * This class uses OpenAPI annotations for API documentation and specifies the
 * expected behavior of the registration endpoint.
 *
 * Endpoints:
 * - POST /v1/users/register: Registers a new user with their profile and settings.
 *
 * @see UserService
 * @see UserMapper
 * @see UserRegistrationResponse
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
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
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegisterCommand command) {
        userService.create(command);
        return ResponseEntity.ok(new UserRegistrationResponse("User registered successfully"));
    }
}
