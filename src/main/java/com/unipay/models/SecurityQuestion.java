package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "security_questions")
public class SecurityQuestion extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String question;
    private String answerHash;
}
