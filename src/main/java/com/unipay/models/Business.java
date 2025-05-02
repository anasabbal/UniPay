package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;



@Entity
public class Business extends BaseEntity{
    private String legalName;
    private String taxId;
    private String registrationNumber;
    private boolean verified;
    @OneToOne
    private User user;
}
