package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.DTO.UserAccountDTO;
import org.example.Entity.UserAccount;
import org.example.service.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserAccountService userService;

    @PostMapping
    public ResponseEntity<UserAccount> create(@RequestBody UserAccountDTO userAccountDTO) {
        return new ResponseEntity<>(userService.create(userAccountDTO), HttpStatus.OK);
    }
}
