package com.unipay.repository;

import com.unipay.models.Business;
import com.unipay.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
    Optional<Business> findByUser(User user);
}
