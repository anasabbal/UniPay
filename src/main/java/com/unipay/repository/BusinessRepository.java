package com.unipay.repository;

import com.unipay.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
}
