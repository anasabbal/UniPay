package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.Instant;



@Entity
public class Consent extends BaseEntity{
    private String consentType; // e.g., "TERMS_OF_SERVICE"
    private String version;
    private Instant grantedAt;
    @ManyToOne
    private User user;
}
