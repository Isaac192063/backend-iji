package com.jijy.music.presentation.controller;

import com.jijy.music.presentation.dto.TokenRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.jijy.music.configuration.security.oauth.CurrentUser;
import com.jijy.music.configuration.security.oauth.UserPrincipal;
import com.jijy.music.presentation.dto.LoginRequest;
import com.jijy.music.presentation.dto.ResponseAuth;
import com.jijy.music.presentation.dto.UserDto;
import com.jijy.music.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${path.auth}")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuth> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/firebase")
    public ResponseEntity<?> firebaseLogin(@RequestBody TokenRequest tokenRequest) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenRequest.getToken());
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            String name = decodedToken.getName(); // Podrías usar esto para el username

            // Aquí generas el JWT y lo devuelves igual que en el login normal
            // El método loginWithFirebase se encargará de registrar si no existe y devolverá el token con el rol
            ResponseAuth jwtResponse = authService.loginWithFirebase(uid, email);
            return ResponseEntity.ok(jwtResponse);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseAuth> registerUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(
                authService.register(userDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public String googleLoginSuccess(@CurrentUser UserPrincipal oAuth2User) {
        System.out.println(oAuth2User);
        return "hola";
    }
}