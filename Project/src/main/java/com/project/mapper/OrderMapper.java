package com.project.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Order;
import com.project.model.Product;

@Mapper
public interface OrderMapper {

	void AddCart(Order oid);
	
	void DelCart(int o_id);

	ArrayList<Order> findCart(int o_member_m_fk);
	int findMember(String m_nickname);
	
	
}
