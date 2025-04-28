package com.unipay.utils;

import com.unipay.criteria.UserCriteria;
import com.unipay.models.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    private final UserCriteria criteria;

    public UserSpecification(UserCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getUsername() != null && !criteria.getUsername().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(User_.username)), "%" + criteria.getUsername().toLowerCase() + "%"));
        }

        if (criteria.getEmail() != null && !criteria.getEmail().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(User_.email)), "%" + criteria.getEmail().toLowerCase() + "%"));
        }

        if (criteria.getStatus() != null) {
            predicates.add(cb.equal(root.get(User_.status), criteria.getStatus()));
        }

        if (criteria.getRoleName() != null) {
            Join<User, UserRole> rolesJoin = root.join("userRoles", JoinType.INNER);
            predicates.add(cb.equal(rolesJoin.get("role").get("name"), criteria.getRoleName()));
        }


        if (criteria.getDateOfBirthFrom() != null) {
            Join<User, UserProfile> profileJoin = root.join(User_.profile, JoinType.INNER);
            predicates.add(cb.greaterThanOrEqualTo(profileJoin.get(UserProfile_.dateOfBirth), criteria.getDateOfBirthFrom()));
        }

        if (criteria.getDateOfBirthTo() != null) {
            Join<User, UserProfile> profileJoin = root.join(User_.profile, JoinType.INNER);
            predicates.add(cb.lessThanOrEqualTo(profileJoin.get(UserProfile_.dateOfBirth), criteria.getDateOfBirthTo()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

