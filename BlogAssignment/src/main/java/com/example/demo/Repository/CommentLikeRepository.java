package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM comment_like WHERE user_id =?1 and comment_id =?2", nativeQuery = true)
	void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
