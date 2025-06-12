package com.jijy.music.configuration.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jijy.music.persistence.model.ROLE;
import com.jijy.music.persistence.model.User;
import com.jijy.music.persistence.repository.UserRepository;
import com.jijy.music.presentation.dto.ResponseAuth; // Asegúrate de que este import es correcto
import com.jijy.music.presentation.dto.UserDto;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.UserService;
import com.jijy.music.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;

    private final UserRepository userService;
    private final UserRepository userRepository;

    // Aunque ObjectMapper está aquí, el error no tiene que ver con su uso directo en las líneas afectadas
    // Pero si no lo usas, considera quitarlo para limpiar el código (advertencia de "unused field").
    private final ObjectMapper objectMapper;

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String email = authToken.getPrincipal().getAttributes().get("email").toString();

        Optional<User> userIfExist = userService.findByEmail(email);

        if (userIfExist.isPresent()) {
            System.out.println(userIfExist.get().getId());
            String token = this.login(userIfExist.get().getId(), userIfExist.get().getRole());

            // Aquí ya tienes el rol del usuario existente.
            String role = userIfExist.get().getRole().name(); // Obtén el nombre del enum ROLE

            // Redirige pasando el token y el rol
            response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + token + "&role=" + role);
            return;
        }

        String name = authToken.getPrincipal().getAttributes().get("name").toString();
        String givenName = authToken.getPrincipal().getAttributes().get("given_name").toString().trim();
        String username = givenName + Math.round(Math.random() * 100 + 1);

        User user = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .role(ROLE.ROLE_USER) // Para un nuevo registro con OAuth, el rol inicial suele ser USER
                .createdAt(LocalDateTime.now())
                .build();

        User userSave = userRepository.save(user);

        // Generamos el token después de guardar al nuevo usuario
        String tokenForNewUser = login(userSave.getId(), userSave.getRole());
        String roleForNewUser = userSave.getRole().name(); // Obtén el nombre del enum ROLE

        // Construimos el ResponseAuth (aunque no lo vamos a usar directamente para la redirección,
        // lo dejo para mostrar cómo lo crearías si lo serializaras en el futuro, por ejemplo)
        // ResponseAuth responseAuth = new ResponseAuth(tokenForNewUser, roleForNewUser);

        // Redirige pasando el token y el rol
        response.sendRedirect("http://localhost:5173/oauth2/redirect?token=" + tokenForNewUser + "&role=" + roleForNewUser);
    }

    private String login(String id, ROLE role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        Authentication userDetails = new UsernamePasswordAuthenticationToken(id, null, authorities);

        return jwtUtils.generateJwtToken(userDetails);
    }
}