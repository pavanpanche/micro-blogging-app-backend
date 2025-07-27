package com.blogging.subtxt.services;

import com.blogging.subtxt.dto.request.RegisterRequest;
import com.blogging.subtxt.dto.responses.UserSearch;
import com.blogging.subtxt.models.User;

import java.util.List;

public interface UserService {

    User registerUser(RegisterRequest request);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<UserSearch> searchUsers(String partialUsername);

}