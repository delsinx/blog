package com.example.demo.Controller;

import com.example.demo.Service.BlogService;
import com.example.demo.User.DTO.BlogDTO;
import com.example.demo.User.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    private final BlogService blogService;


    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public ResponseEntity createBlog(@RequestBody BlogDTO blogDTO) {
        BlogDTO createBlog = blogService.createBlog(blogDTO);
        return ResponseEntity.ok().body("Blog created successfully");
    }

    @GetMapping("/getblogs")
    public List<BlogDTO> getAllBlogs(){
        return blogService.getAllBlog();
    }
    @GetMapping("/getblogs/{id}")
    public BlogDTO getBlogById(@PathVariable Long id){
        return blogService.getBlogById(id);
    }


}
