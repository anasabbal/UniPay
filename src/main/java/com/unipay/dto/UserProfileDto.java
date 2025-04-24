package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Getter
@Setter
public class UserProfileDto {
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String gender;
    private String nationality;
}
