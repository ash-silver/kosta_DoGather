package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Review;

@Mapper
public interface ReviewMapper {
	
	void AddReview(Review r);
	
	void RemoveReview(int r_id);
	
	List<Review> ReviewList(Map<String, Object> map);
	
	int Review_count(int r_pid_p_fk);
	
	String findnick(String m_email);
	
	double ReviewStar(int r_pid_p_fk);

}
