package com.unipay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Configuration class for auditing. This class is responsible for setting up the auditor provider
 * that is used to track the user performing operations on entities, like creation or modification.
 *
 * The `auditorProvider()` method returns a custom implementation of the `AuditorAware` interface,
 * which provides the identifier of the user (or system) performing the action. In this case, it is set
 * to always return "UniPay" as the auditor for all operations.
 *
 * Example usage:
 * - This configuration ensures that each time an entity is saved or updated, the auditor (in this case "UniPay")
 *   will be associated with the operation for auditing purposes.
 *
 * @see AuditorAware
 */
@Configuration
public class AuditConfig {

    /**
     * Bean that provides the current auditor (the user/system performing the action).
     * In this implementation, the auditor is always set to "UniPay".
     *
     * @return An instance of AuditorAware that returns "UniPay" as the auditor.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("UniPay");  // "UniPay" is the default auditor
    }
}
