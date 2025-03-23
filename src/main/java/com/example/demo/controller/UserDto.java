package com.example.demo.controller;

import com.example.demo.models.Role;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
