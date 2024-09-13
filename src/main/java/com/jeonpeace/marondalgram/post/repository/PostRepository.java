package com.jeonpeace.marondalgram.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
