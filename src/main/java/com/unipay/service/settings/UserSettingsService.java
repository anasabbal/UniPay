package com.unipay.service.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.User;
import com.unipay.models.UserSettings;

public interface UserSettingsService {
    UserSettings create(final UserSettingsCommand command, User user);
}
