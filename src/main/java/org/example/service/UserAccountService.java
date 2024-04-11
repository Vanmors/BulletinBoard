package org.example.service;

import lombok.AllArgsConstructor;
import org.example.DTO.UserAccountDTO;
import org.example.Entity.UserAccount;
import org.example.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository repository;

    @Autowired
    UserAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        return new UserDetailsImpl(user);
    }
    public UserAccount create(UserAccountDTO userDTO) {
        UserAccount person = UserAccount.builder().
                username(userDTO.getUsername()).
                password(new BCryptPasswordEncoder().encode(userDTO.getPassword()) ).
                role(userDTO.getRole()).
                build();
        return userRepository.save(person);
    }

    public List<UserAccount> readAll() {
        return repository.findAll();
    }

    public UserAccount update(UserAccount person) {
        return repository.save(person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
