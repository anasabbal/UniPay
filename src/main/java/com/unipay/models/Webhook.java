package com.unipay.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Entity
@Getter
public class Webhook extends BaseEntity{
    private String url;
    private String secret; // For HMAC signatures
    @ElementCollection
    private List<String> events; // e.g., ["payment.success"]
    @ManyToOne
    private User user;
}
