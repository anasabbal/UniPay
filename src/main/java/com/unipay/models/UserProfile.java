package com.unipay.models;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String gender;
    private String nationality;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private Set<PaymentMethod> paymentMethods;
}
