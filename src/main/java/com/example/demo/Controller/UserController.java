package com.example.demo.Controller;

import com.example.demo.Service.UserService;
import com.example.demo.User.DTO.UserDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
     public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity signUp(@RequestBody UserDTO userDTO){
        UserDTO createUser = userService.signUP(userDTO);
        return ResponseEntity.status(201).body(createUser);
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id){
         return userService.getUserById(id);
    }
    @GetMapping()
    public List<UserDTO> getAllUsers(){
         return userService.getAllUsers();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id){
         userService.deleteUserByID(id);
         return ResponseEntity.status(204).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserById(@PathVariable Long id, @RequestBody UserDTO userDTO){
         userService.updateUserById(id, userDTO);
         return ResponseEntity.status(204).body(null);
    }

}
