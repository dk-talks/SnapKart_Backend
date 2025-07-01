package com.dk.snapkart_userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends Base {
    @ManyToOne
    private User user;
    private String tokenValue;
    private Date expiryAt;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
}
