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

    @GetMapping("/getblogsauthor/{id}")
    public List<BlogDTO> getBlogsByAuthorId(@PathVariable Long id){
        return blogService.getBlogsByAuthorId(id);
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return ResponseEntity.ok().body("Post deletado com sucesso");
    }

}
