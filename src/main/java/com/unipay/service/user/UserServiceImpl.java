package com.unipay.service.user;


import com.unipay.command.UserRegisterCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserRepository;
import com.unipay.service.profile.UserProfileService;
import com.unipay.service.settings.UserSettingsService;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserProfileService userProfileService;
    private final UserSettingsService userSettingsService;


    @Override
    @Transactional
    public User create(UserRegisterCommand command) {
        command.validate();
        log.debug("Start creating User with username {}", command.getUsername());

        final User user = User.create(command);
        User savedUser = userRepository.save(user);

        try {
            UserProfile userProfile = userProfileService.create(command.getProfile(), savedUser);
            UserSettings userSettings = userSettingsService.create(command.getSettings(), savedUser);

            savedUser.setProfile(userProfile);
            savedUser.setSettings(userSettings);

            savedUser = userRepository.save(savedUser);
            log.info("User created successfully with ID {}", savedUser.getId());
        } catch (Exception e) {
            log.error("Error during user creation", e);
            throw e;
        }

        return savedUser;
    }
}
