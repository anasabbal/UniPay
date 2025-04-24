package com.unipay.models;

import com.unipay.enums.AddressType;
import jakarta.persistence.*;


/**
 * Represents a physical address associated with a user's profile.
 * Includes details such as street, city, state, zip code, country,
 * and the type of address (e.g., billing, shipping).
 */
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
