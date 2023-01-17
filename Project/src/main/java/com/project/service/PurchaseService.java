package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mapper.PurchaseMapper;
import com.project.model.PurchaseModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	@Autowired
	private PurchaseMapper purchaseMapper;
	
	@Transactional
	public void AddOrder(PurchaseModel order) {
		purchaseMapper.AddOrder(order);
	}
	
}
