package com.unipay.models;


import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;


/**
 * BaseEntity serves as a foundational class for all JPA entities, encapsulating common auditing fields.
 * It leverages Spring Data JPA's auditing capabilities to automatically populate metadata such as creation
 * and modification timestamps, versioning for optimistic locking, and a soft delete flag.
 *
 * <p>Key Features:
 * <ul>
 *   <li><strong>id</strong>: Primary key identifier, auto-generated.</li>
 *   <li><strong>createdAt</strong>: Timestamp marking when the entity was created.</li>
 *   <li><strong>updatedAt</strong>: Timestamp marking the last update to the entity.</li>
 *   <li><strong>version</strong>: Version number for optimistic locking to prevent concurrent update issues.</li>
 *   <li><strong>isDeleted</strong>: Boolean flag indicating soft deletion status.</li>
 * </ul>
 *
 * <p>Annotations Used:
 * <ul>
 *   <li><strong>@MappedSuperclass</strong>: Indicates that this class provides mapping information for its subclasses.</li>
 *   <li><strong>@EntityListeners(AuditingEntityListener.class)</strong>: Enables auditing by listening to entity lifecycle events.</li>
 *   <li><strong>@CreatedDate</strong> and <strong>@LastModifiedDate</strong>: Automatically manage timestamp fields.</li>
 *   <li><strong>@Version</strong>: Facilitates optimistic locking by tracking entity versions.</li>
 * </ul>
 *
 * <p>Note: To activate auditing features, ensure that your Spring Boot application is configured with
 * <strong>@EnableJpaAuditing</strong> and an appropriate implementation of <strong>AuditorAware</strong> if you plan to use
 * <strong>@CreatedBy</strong> and <strong>@LastModifiedBy</strong> annotations for tracking user information.
 *
 * @see org.springframework.data.annotation.CreatedDate
 * @see org.springframework.data.annotation.LastModifiedDate
 * @see org.springframework.data.annotation.Version
 * @see org.springframework.data.jpa.domain.support.AuditingEntityListener
 */
@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
