package com.edu.service;

import com.edu.dto.UserDTO;
import com.edu.entity.User;
import com.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 모든 사용자 데이터를 조회하는 메소드
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName()))
                .collect(Collectors.toList());
    }

    // 사용자 데이터를 삭제하는 메소드
    public boolean deleteUser(String seq) {
        Optional<User> userOptional = userRepository.findById(seq);
        if (userOptional.isPresent()) {
            userRepository.deleteById(seq);
            return true;
        } else {
            return false;
        }
    }
}
