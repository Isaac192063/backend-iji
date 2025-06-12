package com.jijy.music.presentation.controller;

import com.jijy.music.presentation.dto.AuthorPlaylistCount;
import com.jijy.music.presentation.dto.UserDto;
import com.jijy.music.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${path.user}")
@RequiredArgsConstructor
public class UserController  {

    private final UserService userService;

    @GetMapping("find")
    public ResponseEntity<UserDto> getUserWithEmail(@RequestParam String email) {
        System.out.println(email);
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping()
    public List<UserDto> getAllUser() {
        return userService.getAllUsers();
    }


    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public UserDto getUserById(@PathVariable String id, @RequestBody UserDto musicDto) {
        return userService.editUser(musicDto, id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(id));
    }


}
