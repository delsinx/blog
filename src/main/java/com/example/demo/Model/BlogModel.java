package com.example.demo.Model;

import com.example.demo.Repository.UserRepository;
import com.example.demo.User.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn(name = "id")
    private Long id;

    @Column(nullable = false)
    private String title;

   @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserModel  author;


   private LocalDateTime  createAt = LocalDateTime.now();
   private LocalDateTime updateAt;


}
