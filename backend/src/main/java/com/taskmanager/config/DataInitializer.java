package com.taskmanager.config;

import com.taskmanager.entity.User;
import com.taskmanager.enums.Role;
import com.taskmanager.enums.UserStatus;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password("admin")
                    .role(Role.ROLE_ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(admin);
        }
    }
}