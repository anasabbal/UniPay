package com.unipay.service.profile;

import com.unipay.command.ProfileCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public UserProfile create(ProfileCommand profileCommand, User user) {
        profileCommand.validate();
        log.debug("Start creating User profile for user {}", user.getId());

        UserProfile userProfile = UserProfile.create(profileCommand);
        userProfile.setUser(user);

        try {
            userProfile = userProfileRepository.save(userProfile);
            log.info("User profile created successfully with ID {}", userProfile.getId());
        } catch (Exception e) {
            log.error("Error during user profile creation", e);
            throw e;
        }

        return userProfile;
    }
}
