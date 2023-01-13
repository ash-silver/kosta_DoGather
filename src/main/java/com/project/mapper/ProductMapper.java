package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.model.Product;
import com.project.model.SearchDto;

@Mapper
public interface ProductMapper {
	
//	List<Product> Search(Map<String, Object> map);
//
//	List<Product> SearchTotal(Map<String, Object> map);
//   
//	List<Product> Category(Map<String, Object> map);
//
	List<Product> PagingList(SearchDto params);
//	
//	List<Product> List_Sel(Map<String, Object> map);
//	
	int count();
//
//	int category_count(String params);
//	
//	int SearchCount(@Param("keyword") String keyword, @Param("search") String search);
//
//	int SearchTotCount(@Param("total")String total);

}
