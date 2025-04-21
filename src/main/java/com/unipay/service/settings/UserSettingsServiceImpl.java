package com.unipay.service.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.User;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserSettingsRepository;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService{

    private final UserSettingsRepository userSettingsRepository;

    @Override
    public UserSettings create(UserSettingsCommand command) {
        command.validate();
        log.info("Begin creating User settings with payload {}", JSONUtil.toJSON(command));
        final UserSettings userSettings = UserSettings.create(command);
        log.info("User settings with id {} created successfully !", userSettings.getId());
        return userSettingsRepository.save(userSettings);
    }
}
