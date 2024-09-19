package com.jeonpeace.marondalgram.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jeonpeace.marondalgram.post.domain.Like;
import com.jeonpeace.marondalgram.post.domain.Post;
import com.jeonpeace.marondalgram.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController {

	private PostService postService;
	
	public PostRestController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping("/create")
	public Map<String, String> createPost(@RequestParam("contents") String contents
										, @RequestParam("imageFile") MultipartFile file
										, HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Post post = postService.addPost(userId, contents, file);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(post != null) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@PostMapping("/like")
	public Map<String, String> addLike(@RequestParam("postId") int postId
									, HttpSession session
									, Model model){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Like like = postService.insertLike(postId, userId);
		
		model.addAttribute("like", like);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(like != null) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;		
	}
	
	
}
