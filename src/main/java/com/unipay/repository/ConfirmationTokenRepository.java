package com.unipay.repository;

import com.unipay.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenRepository, String> {
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
}
