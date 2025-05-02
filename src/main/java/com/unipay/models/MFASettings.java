package com.unipay.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Setter
@Getter
@Entity
public class MFASettings extends BaseEntity{
    private boolean enabled;
    private String secret; // Base32 encoded TOTP secret

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    private List<String> recoveryCodes;


    private List<String> generateRecoveryCodes() {
        return IntStream.range(0, 10)
                .mapToObj(i -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
    }
    public static MFASettings create(boolean enabled, String secret, User user){
        final MFASettings mfaSettings = new MFASettings();

        mfaSettings.enabled = enabled;
        mfaSettings.secret = secret;
        mfaSettings.user = user;
        mfaSettings.recoveryCodes = mfaSettings.generateRecoveryCodes();

        return mfaSettings;
    }
}
