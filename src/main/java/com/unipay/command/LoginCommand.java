package com.unipay.command;


import com.unipay.utils.AssertValidation;
import lombok.Getter;

@Getter
public class LoginCommand {
    private String email;
    private String password;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public void validate(){
        AssertValidation.assertValidPassword(password);
        AssertValidation.assertValidEmail(email);
    }
}
