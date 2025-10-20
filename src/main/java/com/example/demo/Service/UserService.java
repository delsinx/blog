package com.example.demo.Service;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepository;
import com.example.demo.User.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO signUP(UserDTO userDTO){
        UserModel userModel = userMapper.map(userDTO);
        UserModel savedUser = userRepository.save(userModel);
        return userMapper.map(savedUser);
    }

    public UserDTO getUserById(Long id){
       Optional<UserModel> user = userRepository.findById(id);
       return user.map(userMapper::map).orElse(null);
    }

    public List<UserDTO> getAllUsers(){
        List <UserModel> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> deleteUserByID(Long id){
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return null;
    }
}
