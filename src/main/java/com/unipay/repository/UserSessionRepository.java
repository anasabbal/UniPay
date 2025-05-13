package com.unipay.repository;

import com.unipay.models.User;
import com.unipay.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    void deleteByUser(User user);
    List<UserSession> findByUser(User user);
}
