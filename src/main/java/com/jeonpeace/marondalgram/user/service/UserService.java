package com.jeonpeace.marondalgram.user.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.jeonpeace.marondalgram.common.HashingEncoder;
import com.jeonpeace.marondalgram.user.domain.User;
import com.jeonpeace.marondalgram.user.repository.UserRepository;

@Service
public class UserService {

	// IoC (Inversion of Control) : 제어의 역전 
	// DI(Dependency injection) : 의존성 주입 
	private UserRepository userRepository;
	private HashingEncoder encoder;
	
	public UserService(UserRepository userRepository, @Qualifier("sha256Hashing") HashingEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public int addUser(
			String loginId
			, String password
			, String name
			, String email) {
		
		String encryptPassword = encoder.encode(password);
		
		return userRepository.insertUser(loginId, encryptPassword, name, email);
		
	}
	
	public boolean isDuplicateId(String loginId) {
		
		int count = userRepository.selectCountByLoginId(loginId);
		
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public User getUser(String loginId, String password) {
		
		String encryptPassword = encoder.encode(password);
		
		return userRepository.selectUser(loginId, encryptPassword);
		
	}
	
	public User getUserById(int id) {
		
		return userRepository.selectUserById(id);
	}
	
}
