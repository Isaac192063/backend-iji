package com.jijy.music.services.implementation;

import com.jijy.music.persistence.model.ROLE;
import com.jijy.music.persistence.model.User;
import com.jijy.music.persistence.repository.UserRepository;
import com.jijy.music.presentation.dto.LoginRequest;
import com.jijy.music.presentation.dto.ResponseAuth;
import com.jijy.music.presentation.dto.UserDto;
import com.jijy.music.services.exceptions.BadCredentials;
import com.jijy.music.services.exceptions.BadRequestFailed;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.AuthService;
import com.jijy.music.utils.JwtUtils;
import com.jijy.music.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseAuth login(LoginRequest loginRequest) {
        UserDetails user = this.loadUserByUsername(loginRequest.email());

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentials("Password incorrect");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                loginRequest.password(),
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseAuth(jwtUtils.generateJwtToken(authentication));
    }

    @Override
    public ResponseAuth register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()) ||
                userRepository.existsByEmail(userDto.getEmail())) {
            throw new BadRequestFailed("El usuario ya existe");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (userDto.getRole() == null) {
            userDto.setRole(ROLE.ROLE_USER);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());


        User userSave = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userSave.getId(),
                userSave.getPassword(),
                authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseAuth(
                jwtUtils.generateJwtToken(
                        authentication
                )
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new BadCredentials("Usuario no encontrado");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.get().getRole().name()));

        return new org.springframework.security.core.userdetails.User(userEntity.get().getId(), userEntity.get().getPassword(), authorities);
    }
}
