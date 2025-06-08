package com.jijy.music.configuration.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jijy.music.persistence.model.ROLE;
import com.jijy.music.persistence.model.User;
import com.jijy.music.persistence.repository.UserRepository;
import com.jijy.music.presentation.dto.ResponseAuth;
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

            response.sendRedirect("http://localhost:5173/oauth2/redirect?token="+token);
            return;
        }


        String name = authToken.getPrincipal().getAttributes().get("name").toString();
        String givenName = authToken.getPrincipal().getAttributes().get("given_name").toString().trim();
        String username = givenName + Math.round(Math.random() * 100 + 1);


        User user = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .role(ROLE.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();

        User userSave = userRepository.save(user);


        ResponseAuth responseAuth = new ResponseAuth(
                login(userSave.getId(), userSave.getRole())
        );

        response.sendRedirect("http://localhost:5173/oauth2/redirect?token="+responseAuth.token());
    }

    private String login(String id, ROLE role) {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        Authentication userDetails = new UsernamePasswordAuthenticationToken(id, null, authorities);

        return jwtUtils.generateJwtToken(userDetails);
    }

}
