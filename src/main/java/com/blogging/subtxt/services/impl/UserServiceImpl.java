package com.blogging.subtxt.services.impl;
import com.blogging.subtxt.dto.responses.UserSearch;
import com.blogging.subtxt.exception.EmailAlreadyExistsException;
import com.blogging.subtxt.exception.UserNotFoundException;
import com.blogging.subtxt.dto.request.RegisterRequest;
import com.blogging.subtxt.mapper.UserMapper;
import com.blogging.subtxt.models.User;
import com.blogging.subtxt.repository.UserRepository;
import com.blogging.subtxt.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        return userRepository.save(user);

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public List<UserSearch> searchUsers(String partialUsername) {
        return userRepository.findByUsernameContainingIgnoreCase(partialUsername)
                .stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
    }

}
