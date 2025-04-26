package com.unipay.controller;

import com.unipay.command.UserRegisterCommand;
import com.unipay.dto.UserDto;
import com.unipay.mapper.UserMapper;
import com.unipay.models.User;
import com.unipay.response.UserRegistrationResponse;
import com.unipay.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<UserRegistrationResponse> registerUser(
            @RequestBody UserRegisterCommand command, HttpServletRequest request
    ) {
        userService.create(command, request);
        return ResponseEntity.ok(new UserRegistrationResponse("User registered successfully"));
    }
    /**
     * Endpoint for retrieving a user by their unique ID.
     * This method fetches the user from the system using the UserService.
     *
     * @param userId The unique identifier of the user.
     * @return A ResponseEntity containing the UserResponse if the user is found, or an error message.
     */
    @Operation(
            summary = "Get user by ID",
            description = "Fetches the user with the provided ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        Optional<User> userOpt = userService.getUserById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDto userDto = userMapper.toDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(404).body(new UserDto());
        }
    }
}
