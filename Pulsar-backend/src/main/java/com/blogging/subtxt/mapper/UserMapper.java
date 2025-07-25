package com.blogging.subtxt.mapper;

import com.blogging.subtxt.dto.request.UserDto;
import com.blogging.subtxt.dto.responses.UserSearch;
import com.blogging.subtxt.models.User;

public class UserMapper {

    public static UserDto toDTO(User user) {
        if (user == null) return null;

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static User toModel(UserDto dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static UserSearch toUserResponse(User user) {
        if (user == null) return null;

        return new UserSearch(
                user.getId(),
                user.getUsername()
        );
    }
}
