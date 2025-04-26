package com.unipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a security question and its hashed answer associated with a user.
 * Used for verifying user identity during account recovery or additional authentication steps.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>user</strong>: The user to whom this security question belongs. This is a mandatory relationship with the {@link User} entity.</li>
 *   <li><strong>question</strong>: The text of the security question.</li>
 *   <li><strong>answerHash</strong>: The hashed answer to the security question.</li>
 * </ul>
 *
 * <p>Security questions add an extra layer of security by requiring users to answer a question that only they should know,
 * making account recovery or additional authentication steps more secure.
 */
@Entity
@Table(name = "security_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityQuestion extends BaseEntity {
    /**
     * The user to whom this security question belongs.
     * This relationship ensures that each security question is tied to a specific user.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The text of the security question (e.g., "What is your mother's maiden name?").
     */
    private String question;

    /**
     * The hashed answer to the security question.
     * Storing the answer in a hashed format ensures it is securely stored.
     */
    private String answerHash;
}
