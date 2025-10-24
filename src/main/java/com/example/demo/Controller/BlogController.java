package com.example.demo.Controller;

import com.example.demo.Service.BlogService;
import com.example.demo.User.DTO.BlogDTO;

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

    @PostMapping()
    public ResponseEntity createBlog(@RequestBody BlogDTO blogDTO) {
        BlogDTO createBlog = blogService.createBlog(blogDTO);
        return ResponseEntity.status(201).body(createBlog);
    }

    @GetMapping()
    public List<BlogDTO> getAllBlogs(){

        return ResponseEntity.status(200).body(blogService.getAllBlog()).getBody();
    }

    @GetMapping("/{id}")
    public BlogDTO getBlogById(@PathVariable Long id){

        return blogService.getBlogById(id);
    }

    @GetMapping("/{id}")
    public List<BlogDTO> getBlogsByAuthorId(@PathVariable Long id){
        return blogService.getBlogsByAuthorId(id);
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return ResponseEntity.status(204).body(null);
    }

}
