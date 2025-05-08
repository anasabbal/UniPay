package com.unipay.command;


import com.unipay.enums.Country;
import com.unipay.utils.AssertValidation;
import lombok.Getter;

@Getter
public class CreateAddressCommand {
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

    public void validate() {
        AssertValidation.assertNonEmptyString(street, 1, "Street");
        AssertValidation.assertNonEmptyString(city, 1, "City");
        AssertValidation.assertNonEmptyString(state, 1, "State");
        AssertValidation.assertNonEmptyString(zipCode, 1, "Zip Code");

        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country must not be null or empty.");
        }

        try {
            Country.valueOf(country.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid country code: " + country);
        }
    }

}
