package com.jeonpeace.marondalgram.post.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeonpeace.marondalgram.common.FileManager;
import com.jeonpeace.marondalgram.post.domain.Post;
import com.jeonpeace.marondalgram.post.repository.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
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
	
}
