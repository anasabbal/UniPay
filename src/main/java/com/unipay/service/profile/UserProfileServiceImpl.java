package com.unipay.service.profile;

import com.unipay.command.ProfileCommand;
import com.unipay.models.UserProfile;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserProfileRepository;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile create(ProfileCommand profileCommand) {
        profileCommand.validate();
        log.info("Begin creating User profile with payload {}", JSONUtil.toJSON(profileCommand));
        final UserProfile userProfile = UserProfile.create(profileCommand);
        log.info("User settings with id {} created successfully !", userProfile.getId());
        return userProfileRepository.save(userProfile);
    }
}
