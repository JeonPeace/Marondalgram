package com.jeonpeace.marondalgram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeonpeace.marondalgram.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByLoginIdAndPassword(String loginId, String password);
	
	public User findByLoginId(String loginId);
	
}
