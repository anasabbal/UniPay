package com.unipay.repository;

import com.unipay.criteria.UserCriteria;
import com.unipay.models.User;
import com.unipay.utils.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"roles", "roles.role", "roles.role.permissions"})
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    boolean existsByEmailOrUsername(String email, String userName);

    default Page<User> getUsersByCriteria(Pageable pageable, UserCriteria criteria) {
        return findAll(new UserSpecification(criteria), pageable);
    }
}
