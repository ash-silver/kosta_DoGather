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
		List<Product> product_end = sMapper.FindProduct(); //공고가 종료된 제품들의 리스트
		List<Product> Product_end_price = new ArrayList<>();
		for (Product pro : product_end) {
			Product seller_return=new Product();
			Product product_one = new Product();
			product_one.setP_endprice(pro.getP_price());
			product_one.setP_id(pro.getP_id());
			for (Discount dis : pro.getDiscount()) {
				if (pro.getP_sell() >= dis.getDis_quantity()) { // 판매량이 할인율기준 갯수보다 높거나 같다면 그 할인율을 적용, 할인율최저치보다 낮다면 원금을 그대로 반환,
					int p_endprice_txt = pro.getP_price() - ((pro.getP_price() / 100) * (dis.getDis_count()));
					seller_return.setP_endprice((p_endprice_txt-(p_endprice_txt/100*5))*pro.getP_sell()); //할인율이 존재한다 = 이 물품은 최소한의 할인율 적용치를 넘어서 공구가 진행된 물품이라는 뜻,판매자에게 돌려줄 돈이 있음,
					seller_return.setP_nickname_m_fk(pro.getP_nickname_m_fk()); //판매자의 이름을 입력            100*5=5%는 수수료의 발생,
					seller_return.setP_price(p_endprice_txt);
					seller_return.setP_id(pro.getP_id());
					product_one.setP_endprice(pro.getP_price() - p_endprice_txt);
					
				}
			}
			if(seller_return.getP_endprice()!=0) {
				sMapper.EndPriceSeller(seller_return);
			}
			Product_end_price.add(product_one);
		}
		for (Product pro1 : Product_end_price) {
			List<Order> buyer = sMapper.getOrder(pro1.getP_id());
			for (Order buy : buyer) { // 제품번호에 해당하는 제품을 주문한 모든 주문자에게 적립금으로 반환,
				sMapper.EndPriceMember(pro1.getP_endprice(), pro1.getP_id(), buy.getO_member_m_fk());
			}
			buyer.clear();
		}
		
		
		

	}
}