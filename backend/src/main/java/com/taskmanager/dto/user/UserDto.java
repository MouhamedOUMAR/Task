package com.taskmanager.dto.user;

import com.taskmanager.enums.Role;
import com.taskmanager.enums.UserStatus;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Role role;
    private UserStatus status;
} 