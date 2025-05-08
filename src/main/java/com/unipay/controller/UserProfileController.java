package com.unipay.controller;


import com.unipay.command.CreateAddressCommand;
import com.unipay.service.profile.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.unipay.constants.ResourcePaths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(V1 + USERS + PROFILE)
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * POST /v1/users/profile/addresses
     * Creates a new address for the currently authenticated user.
     */
    @PostMapping("/addresses")
    public ResponseEntity<String> addAddress(@RequestBody final CreateAddressCommand command){
        userProfileService.addAddress(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Address added successfully.");
    }
}
