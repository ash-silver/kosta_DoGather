package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mapper.OrderSheetMapper;
import com.project.model.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class orderSheetService {

	@Autowired
	private OrderSheetMapper orderSheetMapper;
	
	@Transactional
	public void AddOrder(Order order, String p_nickname_m_fk) {
		order.setO_member_m_fk(orderSheetMapper.getMember(p_nickname_m_fk));
		System.out.println(order);
		orderSheetMapper.AddOrder(order);
	}
	
}
