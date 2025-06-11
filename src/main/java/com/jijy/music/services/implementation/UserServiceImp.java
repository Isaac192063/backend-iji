package com.jijy.music.services.implementation;

import com.jijy.music.persistence.model.User;
import com.jijy.music.persistence.repository.UserRepository;
import com.jijy.music.presentation.dto.UserDto;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.UserService;
import com.jijy.music.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto editUser(UserDto userDto, String id) {
        UserDto userEdit = getUserById(id);

        User user = UserMapper.INSTANCE.userDtoToUser(userEdit);
        user.setUpdatedAt(LocalDateTime.now());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPhone(userDto.getPhone());

        User savedUser = userRepository.save(user);
        System.out.println(savedUser);
        return UserMapper.INSTANCE.userToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(String id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("No se encontro el usuario");
        }

        return UserMapper.INSTANCE.userToUserDto(user.get());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::userToUserDto).toList();
    }

    @Override
    public UserDto deleteUser(String id) {
        UserDto userEdit = getUserById(id);
        userRepository.deleteById(id);
        return userEdit;
    }

    @Override
    public UserDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException("No se encontro el usuario");
        }

        return UserMapper.INSTANCE.userToUserDto(user.get());
    }
}
