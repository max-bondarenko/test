package com.max.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.max.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
