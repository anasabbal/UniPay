package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BusinessDto {
    private String legalName;
    private String taxId;
    private String registrationNumber;
    private boolean verified;
}