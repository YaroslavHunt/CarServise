package org.example.carservise.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.auth.SignUpRequestDTO;
import org.example.carservise.dto.auth.SignUpResponseDTO;
import org.example.carservise.entities.User;
import org.example.carservise.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '%s' not found".formatted(username)));
    }

    public SignUpResponseDTO createUser(@Valid SignUpRequestDTO signUpRequestDto) {
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());

        User user = new User();
        user.setUsername(signUpRequestDto.getUsername());
        user.setPassword(password);
        user.setRole(signUpRequestDto.getRole());
        user.setRegisteredAt(LocalDateTime.now());
        userRepository.save(user);

        return SignUpResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .registeredAt(user.getRegisteredAt())
                .build();
    }
}