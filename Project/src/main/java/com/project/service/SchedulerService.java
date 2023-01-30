package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.mapper.SchedulerMapper;
import com.project.model.Discount;
import com.project.model.Order;
import com.project.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerService {

	private final SchedulerMapper sMapper;

// */1
	@Scheduled(cron = "*/30 * * * * *") // 5분마다 실행
	public void run() {
		List<Product> product_end = sMapper.FindProduct();
		List<Product> Product_end_price = new ArrayList<>();
		for (Product pro : product_end) {
			Product p = new Product();
			p.setP_endprice(pro.getP_price());
			p.setP_id(pro.getP_id());
			for (Discount dis : pro.getDiscount()) {
				if (pro.getP_sell() >= dis.getDis_quantity()) {
					int p_endprice_txt = pro.getP_price() - ((pro.getP_price() / 100) * (dis.getDis_count()));
					p.setP_endprice(pro.getP_price() - p_endprice_txt);
				}
			}
			Product_end_price.add(p);
		}
		for (Product pro1 : Product_end_price) {
			List<Order> ord = sMapper.getOrder(pro1.getP_id());
			for (Order order : ord) {
				sMapper.EndPriceMember(pro1.getP_endprice(), pro1.getP_id(), order.getO_member_m_fk());
			}
			ord.clear();

		}

	}
}
