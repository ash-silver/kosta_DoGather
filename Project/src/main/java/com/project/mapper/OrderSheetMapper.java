package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Order;
import com.project.model.PurchaseModel;

@Mapper
public interface OrderSheetMapper {
	void AddOrder(Order model);
}
