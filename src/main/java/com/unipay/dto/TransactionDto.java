package com.unipay.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TransactionDto {
    private String transactionId;
    private double amount;
    private String currency;
    private String status;
    private String paymentMethod;
}
