package com.unipay.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Setter
@Entity
@Getter
public class ApiKey extends BaseEntity{
    private String name;
    @ElementCollection
    private List<String> permissions; // e.g., ["payment:create", "refund:read"]
    private Instant expiresAt;
    @ManyToOne
    private User user;
}
