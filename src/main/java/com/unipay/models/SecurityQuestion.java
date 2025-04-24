package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * Represents a security question and its hashed answer associated with a user.
 * Used for verifying user identity during account recovery or additional authentication steps.
 *
 * <p>Key Fields:
 * <ul>
 *   <li><strong>user</strong>: The user to whom this security question belongs.</li>
 *   <li><strong>question</strong>: The text of the security question.</li>
 *   <li><strong>answerHash</strong>: The hashed answer to the security question.</li>
 * </ul>
 */
@Entity
@Table(name = "security_questions")
public class SecurityQuestion extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String question;
    private String answerHash;
}
