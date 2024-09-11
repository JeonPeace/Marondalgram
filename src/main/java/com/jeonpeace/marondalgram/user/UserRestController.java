package com.jeonpeace.marondalgram.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jeonpeace.marondalgram.user.domain.User;
import com.jeonpeace.marondalgram.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
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
									, @RequestParam("name") String name
									, @RequestParam("email") String email
									, @RequestParam(value="imageFile", required=false) MultipartFile file){
		
		Map<String, String> resultMap = new HashMap<>();
		
		User user = userService.addUser(loginId, password, name, email, file);
		
		if(user != null) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}

