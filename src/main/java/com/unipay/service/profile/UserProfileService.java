package com.unipay.service.profile;

import com.unipay.command.ProfileCommand;
import com.unipay.models.User;
import com.unipay.models.UserProfile;

public interface UserProfileService {
    UserProfile create(final ProfileCommand profileCommand, User user);
}
