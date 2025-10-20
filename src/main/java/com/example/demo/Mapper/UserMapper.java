package com.example.demo.Mapper;

import com.example.demo.Model.UserModel;
import com.example.demo.User.DTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
public UserModel map(UserDTO userDTO){
    UserModel userModel = new UserModel();
    userModel.setId(userDTO.getId());
    userModel.setName(userDTO.getName());
    userModel.setEmail(userDTO.getEmail());
    userModel.setPassword(userDTO.getPassword());
    return userModel;
}
public UserDTO map(UserModel userModel){
    UserDTO userDTO = new UserDTO();
    userDTO.setId(userModel.getId());
    userDTO.setName(userModel.getName());
    userDTO.setEmail(userModel.getEmail());
    userDTO.setPassword(userModel.getPassword());
    return userDTO;
}
}
