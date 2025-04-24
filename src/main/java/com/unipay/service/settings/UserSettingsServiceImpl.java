package com.unipay.service.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.User;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService{

    private final UserSettingsRepository userSettingsRepository;

    @Override
    public UserSettings create(UserSettingsCommand command, User user) {
        command.validate();
        log.debug("Start creating User settings for user {}", user.getId());

        UserSettings userSettings = UserSettings.create(command);
        userSettings.setUser(user);

        try {
            userSettings = userSettingsRepository.save(userSettings);
            log.info("User settings created successfully with ID {}", userSettings.getId());
        } catch (Exception e) {
            log.error("Error during user settings creation", e);
            throw e;  // Rethrow or handle appropriately
        }

        return userSettings;
    }
}
