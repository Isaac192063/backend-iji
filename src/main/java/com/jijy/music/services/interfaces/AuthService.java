package com.jijy.music.services.interfaces;

import com.jijy.music.presentation.dto.LoginRequest;
import com.jijy.music.presentation.dto.ResponseAuth;
import com.jijy.music.presentation.dto.UserDto;

public interface AuthService {
    ResponseAuth login(LoginRequest loginRequest);
    ResponseAuth register(UserDto userDto);
    ResponseAuth loginWithFirebase(String uid, String email);
}
