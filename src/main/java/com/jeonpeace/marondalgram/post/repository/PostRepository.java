package com.jeonpeace.marondalgram.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.post.domain.Post;

import jakarta.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public List<Post> findAllByOrderByIdDesc();
	
	public Post findById(int postId);
	
	@Transactional
	public void deleteById(int postId);
	
	@Transactional
	public Optional<Post> deleteByIdAndUserId(int postId, int userId);
	
}
