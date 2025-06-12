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

        // Obtener el rol del usuario para incluirlo en la respuesta
        String role = user.getAuthorities().stream()
                .filter(a -> a.getAuthority().startsWith("ROLE_"))
                .map(a -> a.getAuthority())
                .findFirst()
                .orElse("ROLE_USER"); // Por defecto si no se encuentra un rol

        return new ResponseAuth(jwtUtils.generateJwtToken(authentication), role);
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
        } else {
            authorities.add(new SimpleGrantedAuthority(userDto.getRole().name()));
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

        return new ResponseAuth(jwtUtils.generateJwtToken(authentication), userSave.getRole().name());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new BadCredentials("Usuario no encontrado");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.get().getRole().name()));

        // Asegúrate de que el primer parámetro sea el ID del usuario como String, no el email, si es lo que esperas
        return new org.springframework.security.core.userdetails.User(userEntity.get().getId(), userEntity.get().getPassword(), authorities);
    }

    @Override
    public ResponseAuth loginWithFirebase(String uid, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isEmpty()) {
            // Si el usuario no existe, regístralo
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(uid); // O podrías generar un username a partir del email o pedirlo en el frontend
            newUser.setPassword(passwordEncoder.encode("firebase-auth-password")); // Contraseña dummy o generada, no se usará para login normal
            newUser.setRole(ROLE.ROLE_USER); // Asigna un rol por defecto
            newUser.setCreatedAt(LocalDateTime.now());
            user = userRepository.save(newUser);
        } else {
            user = userOptional.get();
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getId(), // Usamos el ID del usuario de nuestra BD
                null, // No hay contraseña para esta autenticación
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);
        return new ResponseAuth(token, user.getRole().name());
    }

}