package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.BlogDislikeDto;
import com.example.demo.dtos.BlogLikeDto;
import com.example.demo.dtos.CommentDislikeDto;
import com.example.demo.dtos.CommentDto;
import com.example.demo.dtos.CommentLikeDto;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;

public interface BlogService {
	public User addUser(User user);
	
	public Blog addBlog(Blog blog);
	
	public Blog updateBlog(Blog blog);
	
	public String deleteBlog(Long blogId);
	
	public String addCommentToBlog(CommentDto commentDto);
	
	public List<Comment> getAllComments(Long blogId);
	
	public String deleteCommentFromBlog(Long commentId);
	
	public String addLikeToBlog(BlogLikeDto blogLikeDto);
	
	public String disLikeBlog(BlogDislikeDto blogDislikeDto);
	
	public String addLikeToComment(CommentLikeDto commentLikeDto);
	
	public String disLikeComment(CommentDislikeDto commentDislikeDto);
	
}
