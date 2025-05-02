package com.unipay.models;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

import java.time.Instant;

@Entity
public class RiskProfile extends BaseEntity{

    private int riskScore;
    private Instant lastRiskCheck;
    private boolean flagged;
    @OneToOne
    private User user;
}
