package com.jeonpeace.marondalgram.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public List<Post> findAllByOrderByIdDesc();
	
}
