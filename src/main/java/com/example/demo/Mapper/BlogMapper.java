package com.example.demo.Mapper;

import com.example.demo.Model.BlogModel;
import com.example.demo.User.DTO.BlogDTO;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {
    public BlogModel map(BlogDTO blogDTO){
        BlogModel blogModel = new BlogModel();

       blogModel.setId(blogDTO.getId());
       blogModel.setTitle(blogDTO.getTitle());
       blogModel.setContent(blogDTO.getContent());
       blogModel.setAuthor(blogDTO.getAuthor());
       blogModel.setUpdateAt(blogDTO.getUpdateAt());
       blogModel.setCreateAt(blogDTO.getCreateAt());
        return blogModel;
    }

    public BlogDTO map(BlogModel blogModel){
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(blogModel.getId());
        blogDTO.setTitle(blogModel.getTitle());
        blogDTO.setContent(blogModel.getContent());
        blogDTO.setAuthor(blogModel.getAuthor());
        blogDTO.setUpdateAt(blogModel.getUpdateAt());
        blogDTO.setCreateAt(blogModel.getCreateAt());
        return blogDTO;
    }

}
