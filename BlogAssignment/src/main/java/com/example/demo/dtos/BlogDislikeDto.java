package com.example.demo.dtos;

public class BlogDislikeDto {

	private Long userId;
	
	private Long blogId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}
}
