package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.BlogDislikeDto;
import com.example.demo.dtos.BlogLikeDto;
import com.example.demo.dtos.CommentDislikeDto;
import com.example.demo.dtos.CommentDto;
import com.example.demo.dtos.CommentLikeDto;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.service.BlogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BlogController {
	Logger logger = LoggerFactory.getLogger("BlogController.class");
	
	@Autowired
	private BlogService blogService; 
	
	@PostMapping("/userCreate")
	public User addUser(@RequestBody User user) {
		logger.info("-- API to Add User Details -- ");
		return blogService.addUser(user);
	}
	
	@PostMapping("/blog/create")
	public Blog addBlog(@RequestBody Blog blog) {
		logger.info("-- API to Add Blog -- ");
		return blogService.addBlog(blog);
	}
	
	@PutMapping("/blog/update")
	public Blog updateBlog(@RequestBody Blog blog) {
		logger.info("-- API to Update Blog -- ");
		return blogService.updateBlog(blog);
	}
	
	@DeleteMapping("blog/delete/{blogId}")
	public String deleteBlog(@PathVariable Long blogId){
		logger.info("-- API to Delete Blog -- ");
		return blogService.deleteBlog(blogId);
	}
	
	@PostMapping("blog/comment/add")
	public String addComment(@RequestBody CommentDto commentDto) {
		logger.info("-- API to Add Comment to Blog -- ");
		return blogService.addCommentToBlog(commentDto);
	}
	
	@DeleteMapping("blog/comment/delete/{commentId}")
	public String deleteComment(@PathVariable Long commentId) {
		logger.info("-- API to Delete Comment from Blog -- ");
		return blogService.deleteCommentFromBlog(commentId);
	}
	
	@PutMapping("blog/likes")
	public String likeBlog(@RequestBody BlogLikeDto blogLikeBody) {
		logger.info("-- API to Like the Blog -- ");
		return blogService.addLikeToBlog(blogLikeBody);
	}
	
	@PutMapping("blog/dislikes")
	public String dislikeBlog(@RequestBody BlogDislikeDto blogDislikeDto) {
		logger.info("-- API to dislike the Blog -- ");
		return blogService.disLikeBlog(blogDislikeDto);
	}
	
	@PutMapping("blog/comment/likes")
	public String likeComment(@RequestBody CommentLikeDto commentLikeDto) {
		logger.info("-- API to like the comment in the Blog -- ");
		return blogService.addLikeToComment(commentLikeDto);
	}
	
	@PutMapping("blog/comment/dislikes")
	public String dislikeComment(@RequestBody CommentDislikeDto commentDislikeDto) {
		logger.info("-- API to dislike the comment in the Blog -- ");
		return blogService.disLikeComment(commentDislikeDto);
	}
	
	@GetMapping("blog/allComments/{blogId}")
	public List<Comment> getAllComments(@PathVariable Long blogId){
		logger.info("-- API to Get All the comment in a specific Blog -- ");
		return blogService.getAllComments(blogId);
	}
}
