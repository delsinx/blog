package com.example.demo.Repository;

import com.example.demo.Model.BlogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogModel, Long> {
    List<UserRepository> findByAuthorId(Long authorId);

}
