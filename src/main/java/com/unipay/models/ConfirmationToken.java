package com.unipay.models;

import com.unipay.constants.ResourcePathAndConst;
import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TOKEN_ID")
    private Long tokenId;

    @Column(name = "CONFIRMATION_TOKEN")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public static ConfirmationToken create(User user) {
        final ConfirmationToken confirmationToken = new ConfirmationToken();

        final String code = confirmationToken.generateFormattedCode();
        confirmationToken.user = user;
        confirmationToken.createdDate = new Date();
        confirmationToken.confirmationToken = code;

        return confirmationToken;
    }
    private String generateFormattedCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            if (i > 0) code.append("-");
            for (int j = 0; j < 3; j++) {
                code.append(ResourcePathAndConst.CHARACTERS.charAt(random.nextInt(ResourcePathAndConst.CHARACTERS.length())));
            }
        }
        return code.toString();
    }
}
