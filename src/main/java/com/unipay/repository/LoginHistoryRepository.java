package com.unipay.repository;

import com.unipay.models.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String> {
}
