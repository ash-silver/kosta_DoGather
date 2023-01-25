package com.project.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mapper.OrderMapper;
import com.project.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper oMapper;


	// insert
	public void AddCart(Principal principal, Order order) {
		String m_nickname = principal.getName();
		int o_member_m_fk= oMapper.findMember(m_nickname);
		order.setO_member_m_fk(o_member_m_fk);
		oMapper.AddCart(order);
	}
	
	
	public ArrayList<Order> findCart(String m_nickname){
		int o_member_m_fk= oMapper.findMember(m_nickname);
		return oMapper.findCart(o_member_m_fk);
	}


	

//	 oMapper.findProduct(order.getO_product_p_fk());

}
