package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Product;

@Mapper
public interface ProductMapper {

	void AddProduct(Product pro);

	void AddOption(Option opt);
	
	void AddImg(Img img);
	
	Product FindProduct(int p_id);
	
	List<Option> FindCategory(Map<String,Object> map);
	
	void CreateNewEvent(String value);

}