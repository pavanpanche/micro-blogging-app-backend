package com.blogging.subtxt.controller;

import com.blogging.subtxt.dto.responses.UserSearch;
import com.blogging.subtxt.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    //Search user by username
    @GetMapping("/search")
    public ResponseEntity<List<UserSearch>> searchUsers(@RequestParam String username) {
        List<UserSearch> results = userService.searchUsers(username);
        return ResponseEntity.ok(results);
    }

}
