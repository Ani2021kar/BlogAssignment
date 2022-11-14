package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.BlogLike;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM blog_like WHERE user_id =?1 and blog_id =?2", nativeQuery = true)
	void deleteByUserIdAndBlogId(Long userId, Long blogId); 

}
