package com.dk.snapkart_userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDTO {
    //let us return id, name, email, password after signup
    private String name;
    private String email;
}
