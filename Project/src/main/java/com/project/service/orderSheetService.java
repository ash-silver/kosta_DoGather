package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mapper.OrderSheetMapper;
import com.project.model.PurchaseModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class orderSheetService {

	@Autowired
	private OrderSheetMapper orderSheetMapper;
	
	@Transactional
	public void AddOrder(PurchaseModel order, int p_id) {
		orderSheetMapper.AddOrder(order, p_id);
	}
	
}
