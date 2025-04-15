package com.unipay.models;

import com.universepay.userservice.enums.AddressType;
import jakarta.persistence.*;

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
