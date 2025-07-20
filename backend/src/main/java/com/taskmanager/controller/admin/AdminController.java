package com.taskmanager.controller.admin;

import com.taskmanager.dto.user.UserDto;
import com.taskmanager.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/inactive-users")
    public ResponseEntity<List<UserDto>> getInactiveUsers() {
        return ResponseEntity.ok(adminService.getInactiveUsers());
    }

    @PutMapping("/users/{userId}/activate")
    public ResponseEntity<UserDto> activateUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.activateUser(userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
} 