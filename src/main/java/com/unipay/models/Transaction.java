package com.unipay.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Transaction extends BaseEntity{
    private double amount;
    private String currency;
    private String status; // e.g., "SUCCESS"
    private String paymentMethod; // e.g., "CREDIT_CARD"
    private String customerEmail;
    @ManyToOne
    private User merchant;
}
