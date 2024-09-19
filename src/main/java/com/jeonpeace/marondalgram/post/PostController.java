package com.jeonpeace.marondalgram.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeonpeace.marondalgram.post.dto.CardView;
import com.jeonpeace.marondalgram.post.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {

	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/timeline-view")
	public String timeline(Model model) {
		
		List<CardView> cardViewList = postService.getPostList();
		
		model.addAttribute("cardViewList", cardViewList);
		
		return "/post/timeline";
	}
	
}
