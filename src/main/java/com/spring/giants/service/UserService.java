package com.spring.giants.service;


import com.spring.giants.model.entity.Role;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    @Transactional
    public User join(User user) {
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setEnabled(true);
        Role role = new Role();
        role.setRoleId(1);
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Transactional
    public User getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }
}
