package com.project.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mapper.OrderMapper;
import com.project.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper oMapper;

	//장바구니에 아이템 추가
	public void AddCart(Principal principal, Order order) {
		String m_nickname = principal.getName();
		int o_member_m_fk = oMapper.findMember(m_nickname);
		order.setO_member_m_fk(o_member_m_fk);
		oMapper.AddCart(order);
	}
	
	// 장바구니에 담은 내역 뿌려주는 것 
	public ArrayList<Order> findCart(String m_nickname) {
		int o_member_m_fk = oMapper.findMember(m_nickname);
		return oMapper.findCart(o_member_m_fk);
	}
	
	//장바구니에서 구매  -> update
	@Transactional
	public void cartBuyItem(int o_id, int o_quantity ,int o_product_p_fk) {
		oMapper.cartBuyItem(o_id,o_quantity,o_product_p_fk );
		
	}

	//장바구니 목록에서 삭제 -> delete
	@Transactional
	public void delCartItem(int o_id) {
		oMapper.delCartItem(o_id);
		
	}
	
	
	
}
