package com.example.demo.controller;

import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public User createUser(@RequestBody UserDto userDto) {
        return authService.register(userDto.getUsername(),userDto.getPassword());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.findById(id);
        if (user != null) {
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setRole(userDto.getRole());
            return ResponseEntity.ok(userService.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/login")
    public String login(@RequestBody UserDto user){
        System.out.println(user);
        return authService.login(user.getUsername(), user.getPassword());
    }
//
//    @GetMapping("/register")
//    public String login(@RequestBody UserDto user){
//        return authService.login(user.getUsername(), user.getPassword());
//    }
}

