package com.unipay.models;

import com.unipay.enums.PaymentMethodType;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents a payment method associated with a user's profile.
 * This entity captures details such as the type of payment method (e.g., credit card, PayPal),
 * the provider (e.g., VISA, MasterCard), account information, and the billing address linked
 * to the payment method.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>userProfile</strong>: The user profile to which this payment method belongs. This is a mandatory relationship with the {@link UserProfile} entity.</li>
 *   <li><strong>methodType</strong>: The type of payment method (e.g., credit card, PayPal, etc.), stored as an enumerated value from {@link PaymentMethodType}.</li>
 *   <li><strong>provider</strong>: The provider of the payment method (e.g., VISA, MasterCard, PayPal).</li>
 *   <li><strong>accountNumber</strong>: The account number associated with the payment method. This is typically masked or tokenized for security.</li>
 *   <li><strong>expiryDate</strong>: The expiry date of the payment method (relevant for credit cards, etc.).</li>
 *   <li><strong>isDefault</strong>: A boolean flag indicating whether this is the user's default payment method.</li>
 *   <li><strong>isVerified</strong>: A boolean flag indicating whether the payment method has been verified by the provider.</li>
 *   <li><strong>billingAddress</strong>: The billing address linked to this payment method, which is a relationship to the {@link Address} entity.</li>
 * </ul>
 *
 * <p>This class is used to manage payment methods for a user, whether it's a credit card, digital wallet, or other forms of payment.
 * It supports:
 * <ul>
 *   <li>Tracking the payment method type, provider, and account details securely.</li>
 *   <li>Linking a billing address to the payment method for transaction purposes.</li>
 *   <li>Supporting functionality for setting a default payment method and verifying payment methods.</li>
 * </ul>
 *
 * @see com.unipay.models.UserProfile
 * @see com.unipay.models.Address
 * @see com.unipay.enums.PaymentMethodType
 */
@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity {

    /**
     * The user profile to which this payment method belongs. This is a mandatory relationship with the {@link UserProfile} entity.
     */
    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    /**
     * The type of payment method (e.g., credit card, PayPal, etc.).
     * This field is represented using an enum {@link PaymentMethodType}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private PaymentMethodType methodType;

    /**
     * The provider of the payment method (e.g., VISA, MasterCard, PayPal).
     */
    @Column(name = "provider", nullable = false)
    private String provider;

    /**
     * The account number associated with the payment method.
     * This is typically masked or tokenized for security reasons.
     */
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    /**
     * The expiry date of the payment method (relevant for credit cards, etc.).
     */
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    /**
     * A boolean flag indicating whether this is the user's default payment method.
     * Only one payment method can be marked as the default.
     */
    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    /**
     * A boolean flag indicating whether the payment method has been verified by the provider.
     * This ensures that the method is valid and ready for transactions.
     */
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    /**
     * The billing address linked to this payment method.
     * This is a relationship with the {@link Address} entity and is important for transaction processing.
     */
    @ManyToOne
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;
}
