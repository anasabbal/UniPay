package com.unipay.models;

import com.unipay.enums.PaymentMethodType;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private PaymentMethodType methodType;

    @Column(name = "provider", nullable = false)
    private String provider; // e.g., VISA, MasterCard, PayPal

    @Column(name = "account_number", nullable = false)
    private String accountNumber; // Masked or tokenized

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @ManyToOne
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;
}
