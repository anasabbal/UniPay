package com.unipay.repository;

import com.unipay.models.User;
import com.unipay.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    void deleteByUser(User user);
}
