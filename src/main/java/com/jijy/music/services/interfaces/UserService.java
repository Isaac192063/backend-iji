package com.jijy.music.services.interfaces;

import com.jijy.music.presentation.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto editUser(UserDto userDto, String id);
    UserDto getUserById(String id);
    List<UserDto> getAllUsers();
    UserDto deleteUser(String id);
    UserDto findByEmail(String email);
}
