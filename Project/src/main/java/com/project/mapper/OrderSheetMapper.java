package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Order;
import com.project.model.PurchaseModel;

@Mapper
public interface OrderSheetMapper {
	void AddOrder(Order model);
	int getMember(String p_nickname_m_fk);
	void Refund(int o_id);
}
