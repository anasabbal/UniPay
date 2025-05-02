package com.unipay.models;

import com.unipay.helper.EncryptedStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;



@Entity
public class PayoutSettings extends BaseEntity{
    @Convert(converter = EncryptedStringConverter.class) // Use JPA encryption
    private String bankAccount;
    private String currency;
    private String schedule; // e.g., "DAILY"
    @OneToOne
    private User user;
}
