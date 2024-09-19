package com.jeonpeace.marondalgram.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeonpeace.marondalgram.common.FileManager;
import com.jeonpeace.marondalgram.post.domain.Like;
import com.jeonpeace.marondalgram.post.domain.Post;
import com.jeonpeace.marondalgram.post.dto.CardView;
import com.jeonpeace.marondalgram.post.repository.LikeRepository;
import com.jeonpeace.marondalgram.post.repository.PostRepository;
import com.jeonpeace.marondalgram.user.domain.User;
import com.jeonpeace.marondalgram.user.service.UserService;

@Service
public class PostService {

	private PostRepository postRepository;
	private LikeRepository likeRepository;
	private UserService userService;
	
	public PostService(PostRepository postRepository, LikeRepository likeRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeRepository = likeRepository;
	}
	
	public Post addPost(int userId, String contents, MultipartFile file){
		
		String urlPath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
						.userId(userId)
						.contents(contents)
						.imagePath(urlPath)
						.build();
		
		Post result = postRepository.save(post);
		
		return result;
	}
	
	public List<CardView> getPostList(){
		
		List<Post> postList = postRepository.findAllByOrderByIdDesc();
		
		List<CardView> cardViewList = new ArrayList<>();
		
		for(Post post:postList) {
			
			int userId = post.getUserId();
			
			int likeCount = likeRepository.countByPostId(post.getId());
			
			User user = userService.getUserById(userId);
			
			CardView cardView = CardView.builder()
										.postId(post.getId())
										.userId(userId)
										.contents(post.getContents())
										.imagePath(post.getImagePath())
										.loginId(user.getLoginId())
										.likeCount(likeCount)
										.build();
			
			cardViewList.add(cardView);
		}
		
		return cardViewList;
	}
	
	public Like insertLike(int postId, int userId) {
		
		Like like = Like.builder()
						.postId(postId)
						.userId(userId)
						.build();
		
		Like result = likeRepository.save(like);
		
		return result;
	}
	
}
