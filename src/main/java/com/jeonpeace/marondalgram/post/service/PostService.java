package com.jeonpeace.marondalgram.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeonpeace.marondalgram.common.FileManager;
import com.jeonpeace.marondalgram.post.domain.Comment;
import com.jeonpeace.marondalgram.post.domain.Like;
import com.jeonpeace.marondalgram.post.domain.Post;
import com.jeonpeace.marondalgram.post.dto.CardView;
import com.jeonpeace.marondalgram.post.repository.CommentRepository;
import com.jeonpeace.marondalgram.post.repository.LikeRepository;
import com.jeonpeace.marondalgram.post.repository.PostRepository;
import com.jeonpeace.marondalgram.user.domain.User;
import com.jeonpeace.marondalgram.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class PostService {

	private PostRepository postRepository;
	private LikeRepository likeRepository;
	private CommentRepository commentRepository;
	private UserService userService;
	
	public PostService(PostRepository postRepository, LikeRepository likeRepository, CommentRepository commentRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
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
	
	@Transactional
	public String deletePost(int postId) {
		
		Post post = postRepository.findById(postId);
		
		if(post != null) {
			likeRepository.deleteByPostId(postId);
			commentRepository.deleteByPostId(postId);
			postRepository.deleteById(postId);
			return "success";
		}else {
			return "fail";
		}
	}
	
	public List<CardView> getPostList(int loginUserId){
		
		List<Post> postList = postRepository.findAllByOrderByIdDesc();
		
		List<CardView> cardViewList = new ArrayList<>();
		
		for(Post post:postList) {
			
			int userId = post.getUserId();
			
			int likeCount = likeRepository.countByPostId(post.getId());
			
			Like like = likeRepository.findByPostIdAndUserId(post.getId(), loginUserId);
			Boolean loginUserLike = false;

			if(like != null) {
				loginUserLike = true;
			}
			
			User user = userService.getUserById(userId);
			
			List<Comment> commentList = commentRepository.findByPostId(post.getId());
			
			CardView cardView = CardView.builder()
										.postId(post.getId())
										.userId(userId)
										.contents(post.getContents())
										.imagePath(post.getImagePath())
										.loginId(user.getLoginId())
										.likeCount(likeCount)
										.loginUserLike(loginUserLike)
										.commentList(commentList)
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
	
	@Transactional
	public String deleteLike(int postId, int userId) {

		Like like = likeRepository.findByPostIdAndUserId(postId, userId);
		
		if(like != null) {
			likeRepository.deleteByPostIdAndUserId(postId, userId);
			return "success";
		}else {
			return "fail";
		}
	}
	
	public Comment addComment(int postId, int userId, String commentText) {
		
		User user = userService.getUserById(userId);
		
		String userLoginId = user.getLoginId();
		
		Comment comment = Comment.builder()
								 .postId(postId)
								 .userId(userId)
								 .userLoginId(userLoginId)
								 .commentText(commentText)
								 .build();
		
		Comment result = commentRepository.save(comment);
		
		return result;
	}	
	
	@Transactional
	public String deleteComment(int commentId) {
		
		Comment comment = commentRepository.findById(commentId);
		
		if(comment != null) {
			commentRepository.deleteById(commentId);
			return "success";
		}else {
			return "fail";
		}
	}
	
}
