package com.jeonpeace.marondalgram.post.dto;

import java.util.List;

import com.jeonpeace.marondalgram.post.domain.Comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CardView {

	private int postId;
	private int userId;
	
	private String contents;
	private String imagePath;
	
	private String loginId;
	
	private int likeCount;
	private Boolean loginUserLike;
	
	private List<Comment> commentList;
	
}
