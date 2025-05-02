package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PayoutSettingsDto {
    private String bankAccount; // encrypted
    private String currency;
    private String schedule;
}
