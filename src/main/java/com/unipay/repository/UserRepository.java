package com.unipay.repository;

import com.unipay.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = {"roles", "roles.role", "roles.role.permissions"})
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findById(String userId);

    boolean existsByEmailOrUsername(String email, String userName);
}
