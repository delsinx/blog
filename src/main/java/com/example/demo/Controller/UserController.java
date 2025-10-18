package com.example.demo.Controller;

import com.example.demo.User.DTO.UserDTO;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
     public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserDTO userDTO){
        UserDTO createUser = userService.signUP(userDTO);
        return ResponseEntity.ok("User created successfully: " + createUser);
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id){
         return userService.getUserById(id);
    }


}
