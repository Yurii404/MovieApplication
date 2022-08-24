package com.example.movie.security;

import com.example.movie.entities.User;
import com.example.movie.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.security.core.userdetails.User.builder;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());

        return builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(authority))
                .build();
    }
}
