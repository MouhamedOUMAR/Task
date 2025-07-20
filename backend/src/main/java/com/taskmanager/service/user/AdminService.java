package com.taskmanager.service.user;

import com.taskmanager.dto.user.UserDto;
import com.taskmanager.entity.User;
import com.taskmanager.enums.UserStatus;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserDto> getInactiveUsers() {
        return userRepository.findByStatus(UserStatus.INACTIVE)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
