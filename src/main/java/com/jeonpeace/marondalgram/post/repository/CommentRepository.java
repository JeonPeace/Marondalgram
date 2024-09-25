package com.jeonpeace.marondalgram.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.post.domain.Comment;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	public List<Comment> findByPostId(int postId);
	
	public Comment findById(int id);
	@Transactional
	public void deleteById(int id);
	
	@Transactional
	public void deleteByPostId(int id);
	
}
