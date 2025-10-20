package com.example.demo.User.DTO;

import com.example.demo.Model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private UserModel author;
    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime updateAt;


}
