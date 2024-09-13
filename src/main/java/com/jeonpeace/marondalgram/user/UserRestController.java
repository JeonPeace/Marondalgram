package com.jeonpeace.marondalgram.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeonpeace.marondalgram.user.domain.User;
import com.jeonpeace.marondalgram.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {

	private UserService userService;
	
//	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/join")
	public Map<String, String> join(@RequestParam("loginId") String loginId
									, @RequestParam("password") String password									
									, @RequestParam("email") String email
									, @RequestParam("name") String name){
		
		Map<String, String> resultMap = new HashMap<>();
		
		User user = userService.addUser(loginId, password, email, name);
		
		if(user != null) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

	@PostMapping("/login")
	public Map<String, String> login(@RequestParam("loginId") String loginId
									, @RequestParam("password") String password
									, HttpSession session){
		
		Map<String, String> resultMap = new HashMap<>();
		
		User user = userService.getUser(loginId, password);
		
		if(user != null) {
			resultMap.put("result", "success");
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@GetMapping("/duplicate-id")
	public Map<String, Boolean> checkDuplicate(@RequestParam(value="loginId", required=false) String loginId){
		
		boolean isDuplicate = userService.checkDuplicate(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		if(isDuplicate) {
			resultMap.put("isDuplicate", true);
		}else {
			resultMap.put("isDuplicate", false);
		}
		
		return resultMap;
	}
	
}

