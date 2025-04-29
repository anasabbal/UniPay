package com.unipay.repository;

import com.unipay.models.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {
    Optional<AuditLog> findByIdOrAction(String id, String action);
    Page<AuditLog> getAuditLogsByUser_Id(Pageable pageable, String userId);
}
