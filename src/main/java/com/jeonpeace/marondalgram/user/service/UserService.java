package com.jeonpeace.marondalgram.user.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeonpeace.marondalgram.common.FileManager;
import com.jeonpeace.marondalgram.common.HashingEncoder;
import com.jeonpeace.marondalgram.user.domain.User;
import com.jeonpeace.marondalgram.user.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private HashingEncoder encoder;
	
	private UserService(UserRepository userRepository, @Qualifier("md5Hashing") HashingEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public User addUser(String loginId, String password, String email, String name) {
		
		String encryptPassword = encoder.encode(password);

		
		User user = User.builder()
						.loginId(loginId)
						.password(encryptPassword)
						.email(email)
						.name(name)
						.build();
		
		User result = userRepository.save(user);
		
		return result;
	}
	
	public User getUser(String loginId, String password) {
		
		String encryptPassword = encoder.encode(password);
		
		User user = userRepository.findByLoginIdAndPassword(loginId, encryptPassword);
		
		return user;
	}
	
	public boolean checkDuplicate(String loginId) {
		
		User user = userRepository.findByLoginId(loginId);
		
		if(user == null) {
			// 중복 안됨
			return false;
		}else {
			// 중복됨
			return true;
		}
	}
	
}
