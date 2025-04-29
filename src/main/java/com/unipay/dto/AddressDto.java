package com.unipay.dto;


import com.unipay.enums.AddressType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressDto extends BaseEntityDto{

    /**
     * The street address.
     * Represents the street name and house/building number.
     */
    private String street;

    /**
     * The city in which the address is located.
     */
    private String city;

    /**
     * The state or province in which the address is located.
     */
    private String state;

    /**
     * The postal code or zip code for the address.
     */
    private String zipCode;

    /**
     * The country of the address.
     */
    private String country;

    /**
     * The type of address (e.g., billing, shipping).
     * The type is represented by an enum to enforce predefined values.
     */
    private AddressType addressType;
}
