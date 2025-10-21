package com.example.demo.Service;

import com.example.demo.Mapper.BlogMapper;
import com.example.demo.Model.BlogModel;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.User.DTO.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private BlogRepository blogRepository;

    private BlogMapper blogMapper;

    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    public BlogDTO createBlog(BlogDTO blogDTO) {
        BlogModel blogModel = blogMapper.map(blogDTO);
        BlogModel saveBlog = blogRepository.save(blogModel);
        return blogMapper.map(saveBlog);

    }

    public List<BlogDTO> getAllBlog() {
        List<BlogModel> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(blogMapper::map)
                .collect(Collectors.toList());
    }

    public BlogDTO getBlogById(Long id) {
        Optional<BlogModel> blog = blogRepository.findById(id);
        return blog.map(blogMapper::map).orElse(null);
    }

    public List<BlogDTO> getBlogsByAuthorId(Long authorId){
        List<BlogModel> blogs = blogRepository.findByAuthorId(authorId);
        return blogs.stream()
                .map(blogMapper::map)
                .collect(Collectors.toList());
    }
}
