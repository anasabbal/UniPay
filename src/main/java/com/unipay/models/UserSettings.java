package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_settings")
public class UserSettings extends BaseEntity {


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean emailNotificationsEnabled;
    private String preferredLanguage;
    private String timezone;
}
