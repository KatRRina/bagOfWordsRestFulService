package ru.butenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.butenko.dto.UserDto;
import ru.butenko.model.User;
import ru.butenko.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class UseService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException(name));
        return UserDto.builder()
                .username(user.getUsername())
                .user_password(user.getUser_password())
                .build();
    }
}
