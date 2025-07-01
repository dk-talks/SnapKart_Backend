package com.dk.snapkart_userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRquestDTO {
    private String name;
    private String email;
    private String password;
}
