package com.unipay.command;


import lombok.Getter;

@Getter
public class CreateBusinessCommand {
    private String legalName;
    private String taxId;
    private String registrationNumber;
}
