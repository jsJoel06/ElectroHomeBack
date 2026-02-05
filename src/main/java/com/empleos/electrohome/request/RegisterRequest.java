package com.empleos.electrohome.request;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {

    private String email;
    private String password;
    private Set<String> roles;
}
