package com.example.demo.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.BlogLikeRepository;
import com.example.demo.Repository.BlogRepository;
import com.example.demo.Repository.CommentLikeRepository;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.BlogDislikeDto;
import com.example.demo.dtos.BlogLikeDto;
import com.example.demo.dtos.CommentDislikeDto;
import com.example.demo.dtos.CommentDto;
import com.example.demo.dtos.CommentLikeDto;
import com.example.demo.entity.Blog;
import com.example.demo.entity.BlogLike;
import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentLike;
import com.example.demo.entity.User;
import com.example.demo.service.BlogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

	Logger logger = LoggerFactory.getLogger("BlogServiceImpl.class");

	final int INCREMENT = 1;
	final int DECREMENT = 1;
	final String DELETED_BLOG = "Blog Deleted Successfully with BlogId : ";
	final String COMMENT_ADDED = "Comment Added !!!";
	final String COMMENT_DELETED = "Comment Deleted !!!";
	final String BLOG_LIKED = "Blog Liked !!!";
	final String BLOG_DISLIKED = "Blog Disliked !!!";
	final String COMMENT_LIKED = "Comment Liked !!!";
	final String COMMENT_DISLIKED = "Comment Disliked !!!";

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BlogRepository blogRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private BlogLikeRepository blogLikeRepo;

	@Autowired
	private CommentLikeRepository commentLikeRepo;

	@Override
	public User addUser(User user) {
		logger.info("Adding User ...");
		return userRepo.save(user);
	}

	@Override
	public Blog addBlog(Blog blog) {
		logger.info("Posting Blog ...");
		return blogRepo.save(blog);
	}

	@Override
	public Blog updateBlog(Blog blog) {
		logger.info("Editing Blog ...");
		Blog savedBlog = blogRepo.findById(blog.getId()).get();
		savedBlog.setDescription(blog.getDescription());
		savedBlog.setTitle(blog.getTitle());
		Blog updatedblog = blogRepo.save(savedBlog);
		logger.info("Blog has been Edited successfully !!! ");
		return updatedblog;
	}

	@Override
	public String deleteBlog(Long blogId) {
		logger.info("Deleting Blog ...");
		List<Comment> commentList = commentRepo.findCommentByBlogId(blogId);
		for (Comment comment : commentList)
			commentRepo.deleteById(comment.getId());
		blogRepo.deleteById(blogId);
		return DELETED_BLOG + blogId;
	}

	@Override
	public String addCommentToBlog(CommentDto commentDto) {
		logger.info("Adding Comment to Blog ...");
		Comment comment = getComment(commentDto);
		commentRepo.save(comment);
		Long blogId = commentDto.getBlogId();
		Blog blog = blogRepo.findById(blogId).orElse(null);
		blog.setCommentsCount(blog.getCommentsCount() + INCREMENT);
		blogRepo.save(blog);
		return COMMENT_ADDED;
	}

	@Override
	public String deleteCommentFromBlog(Long commentId) {
		logger.info("Deleting Comment from Blog...");
		Long blogId = commentRepo.findById(commentId).get().getBlogId();
		Blog blog = blogRepo.findById(blogId).orElse(null);
		blog.setCommentsCount(blog.getCommentsCount() - DECREMENT);
		blogRepo.save(blog);
		commentRepo.deleteById(commentId);
		return COMMENT_DELETED;
	}

	@Override
	public String addLikeToBlog(BlogLikeDto blogLikeDto) {
		logger.info("Liking the Blog...");
		System.out.println("BLOG LIKE DTO ------- " + blogLikeDto.getBlogId());
		Blog blog = blogRepo.findById(blogLikeDto.getBlogId()).orElse(null);
		blog.setLikesCount(blog.getLikesCount() + INCREMENT);
		blogRepo.save(blog);
		BlogLike blogLike = getBlogLike(blogLikeDto);
		System.out.println("BLOG LIKE ------- " + blogLike.getBlogId());
		blogLikeRepo.save(blogLike);
		return BLOG_LIKED;
	}

	@Override
	public String disLikeBlog(BlogDislikeDto blogDislikeDto) {
		logger.info("Disliking the Blog...");
		Blog blog = blogRepo.findById(blogDislikeDto.getBlogId()).orElse(null);
		blog.setLikesCount(blog.getLikesCount() - DECREMENT);
		blogRepo.save(blog);
		blogLikeRepo.deleteByUserIdAndBlogId(blogDislikeDto.getUserId(), blogDislikeDto.getBlogId());
		return BLOG_DISLIKED;
	}

	@Override
	public String addLikeToComment(CommentLikeDto commentLikeDto) {
		logger.info("Liking the Comment...");
		Comment comment = commentRepo.findById(commentLikeDto.getCommentId()).orElse(null);
		comment.setLikesCount(comment.getLikesCount() + INCREMENT);

		CommentLike commentLike = getCommentLike(commentLikeDto);

		commentLikeRepo.save(commentLike);
		commentRepo.save(comment);
		return COMMENT_LIKED;
	}

	@Override
	public String disLikeComment(CommentDislikeDto commentDislikeDto) {
		logger.info("Disliking the Comment...");
		Comment comment = commentRepo.findById(commentDislikeDto.getCommentId()).orElse(null);
		comment.setLikesCount(comment.getLikesCount() - DECREMENT);
		commentRepo.save(comment);
		commentLikeRepo.deleteByUserIdAndCommentId(commentDislikeDto.getUserId(), commentDislikeDto.getCommentId());
		return COMMENT_DISLIKED;
	}

	@Override
	public List<Comment> getAllComments(Long blogId) {
		logger.info("Fetching List of All the coomments in this post...");
		List<Comment> commentList = commentRepo.findCommentByBlogId(blogId);
		return commentList;
	}

	private Comment getComment(CommentDto commentDto) {
		Comment comment = new Comment();
		User savedUser = userRepo.findById(commentDto.getUserId()).orElse(null);

		comment.setBlogId(commentDto.getBlogId());
		comment.setUserId(commentDto.getUserId());
		comment.setBody(commentDto.getBody());
		comment.setLikesCount(0);
		comment.setCommentedBy(savedUser.getFirstName() + " " + savedUser.getLastName());

		return comment;

	}

	private BlogLike getBlogLike(BlogLikeDto blogLikeDto) {
		BlogLike blogLike = new BlogLike();
		User user = userRepo.findById(blogLikeDto.getUserId()).orElse(null);

		blogLike.setLikedBy(user.getFirstName() + " " + user.getLastName());
		blogLike.setBlogId(blogLikeDto.getBlogId());
		blogLike.setUserId(blogLikeDto.getUserId());

		return blogLike;
	}

	private CommentLike getCommentLike(CommentLikeDto commentLikeDto) {
		CommentLike commentLike = new CommentLike();

		User user = userRepo.findById(commentLikeDto.getUserId()).orElse(null);

		commentLike.setCommentId(commentLikeDto.getCommentId());
		commentLike.setUserId(commentLikeDto.getUserId());
		commentLike.setLikedBy(user.getFirstName() + " " + user.getLastName());
		return commentLike;
	}
}
