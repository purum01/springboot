package com.edu.service;

import com.edu.dto.UserDTO;
import com.edu.entity.User;
import com.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserDTO> getUserById(String id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getName()));
    }

    public UserDTO createUser(String name) {
        String id = String.valueOf(userRepository.count() + 1);
        User user = new User();
        user.setId(id);
        user.setName(name);
        userRepository.save(user);
        return new UserDTO(id, name);
    }

    public UserDTO updateUser(String id, String name) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName());
    }
}
