package com.unipay.models;
import com.unipay.command.ProfileCommand;
import com.unipay.enums.UserGender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;



/**
 * Represents the extended profile information of a user.
 * Includes personal and contact details, and associations with addresses and payment methods.
 *
 * <p>Key Fields:
 * <ul>
 *   <li><strong>user</strong>: The user to whom this profile belongs (one-to-one relationship).</li>
 *   <li><strong>fullName</strong>: The user's full legal name.</li>
 *   <li><strong>dateOfBirth</strong>: The user's date of birth.</li>
 *   <li><strong>phoneNumber</strong>: Contact phone number of the user.</li>
 *   <li><strong>gender</strong>: Gender identity of the user.</li>
 *   <li><strong>nationality</strong>: The user's nationality or country of citizenship.</li>
 *   <li><strong>addresses</strong>: A set of addresses linked to the profile.</li>
 *   <li><strong>paymentMethods</strong>: A set of payment methods associated with the user.</li>
 * </ul>
 */
@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    private String nationality;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private Set<PaymentMethod> paymentMethods;

    public static UserProfile create(final ProfileCommand command){
        final UserProfile userProfile = new UserProfile();

        userProfile.fullName = command.getFullName();
        userProfile.dateOfBirth = command.getDateOfBirth();
        userProfile.phoneNumber = command.getPhoneNumber();
        userProfile.gender = UserGender.valueOf(command.getGender());
        userProfile.nationality = command.getNationality();

        return userProfile;
    }
}
