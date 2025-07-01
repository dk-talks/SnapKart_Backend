package com.dk.snapkart_userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "users")
public class User extends Base {
    private String name;
    private String email;
    private String password;
}
