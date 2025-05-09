package com.unipay.models;


import com.unipay.enums.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Transaction extends BaseEntity{
    private double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING, SUCCESS, FAILED
    private String paymentMethod; // e.g., "CREDIT_CARD"
    private String customerEmail;
    private String gatewayTransactionId; // ID from Stripe/PayPal
    @ManyToOne
    private User merchant;
}
