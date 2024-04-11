package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name = "fk_role_id")
    private Role role;
}

