package com.jeonpeace.marondalgram.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.post.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	public List<Comment> findByPostId(int postId);
	
}
