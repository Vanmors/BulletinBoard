package org.example.DTO;

import lombok.Data;
import org.example.Entity.Role;

@Data
public class UserAccountDTO {

    private String username;
    private String password;
    private Role role;
}