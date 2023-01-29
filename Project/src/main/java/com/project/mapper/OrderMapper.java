package com.project.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.model.Order;

@Mapper
public interface OrderMapper {

	//장바구니에 추가
	void AddCart(Order oid);
	
	ArrayList<Order> findCart(int o_member_m_fk);
	int findMember(String m_nickname);
	
	//장바구니에서 구매
	void cartBuyItem(@Param("o_id")int o_id, @Param("o_quantity")int o_quantity ,@Param("o_product_p_fk")int o_product_p_fk);
	
	//장바구니에서 삭재ㅔ
	void delCartItem(int o_id);
	
	
}
