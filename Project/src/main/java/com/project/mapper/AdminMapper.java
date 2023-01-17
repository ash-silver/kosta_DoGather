package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Member;
import com.project.model.Product;

///////////DAO
@Mapper
public interface AdminMapper{
	
	
	void DelProduct (int p_id);
	List<Product> ListProduct();
	Product FindProduct(int p_id);
	
	void DelMember (int m_id);
	List<Member> ListMember();
	Product FindMember(int m_id);
	
	
	
}
 







