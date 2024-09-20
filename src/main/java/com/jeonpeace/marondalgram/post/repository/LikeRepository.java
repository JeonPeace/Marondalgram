package com.jeonpeace.marondalgram.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.post.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Integer>{

	public int countByPostId(int postId);
	
	public Like findByPostIdAndUserId(int postId, int userId);
	public void deleteByPostIdAndUserId(int postId, int userId);
	
}
