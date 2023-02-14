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
		List<Product> End_Product_List = sMapper.FindProduct(); // 공고가 종료된 제품들의 리스트
		List<Product> Product_end_price = new ArrayList<>();
		if (End_Product_List.size() != 0) {
			for (Product End_Product : End_Product_List) {
				Product Seller_Return = new Product();
				Product Buyer_Product = new Product();
				Seller_Return.setP_id(End_Product.getP_id());
				Seller_Return.setP_nickname_m_fk(End_Product.getP_nickname_m_fk());
				Buyer_Product.setP_endprice(End_Product.getP_price());
				Buyer_Product.setP_id(End_Product.getP_id());
				Buyer_Product.setP_price(End_Product.getP_price());
				for (Discount EndPro_Dis : End_Product.getDiscount()) {
					if (End_Product.getP_sell() >= EndPro_Dis.getDis_quantity()) { // 판매량이 할인율기준 갯수보다 높거나 같다면 그 할인율을 적용, 할인율최저치보다 낮다면
																	// 원금을
																	// 그대로 반환,
						int p_endprice_txt = End_Product.getP_price() - ((End_Product.getP_price() / 100) * (EndPro_Dis.getDis_count()));
						Seller_Return.setP_endprice(p_endprice_txt);
						Seller_Return.setP_price((p_endprice_txt - (p_endprice_txt / 100 * 5)) * End_Product.getP_sell());
						Seller_Return.setP_sell(End_Product.getP_sell());
						Buyer_Product.setP_endprice(End_Product.getP_price() - p_endprice_txt);
					} else {
						Buyer_Product.setP_sell(0);
					}
				}
				if (Seller_Return.getP_endprice() != 0) {
					Buyer_Product.setP_sell(1);
				}
				sMapper.EndPriceSeller(Seller_Return);
				Product_end_price.add(Buyer_Product);
			}
			for (Product BuyChk_Product : Product_end_price) {
				List<Order> Product_Buyer_List = sMapper.getOrder(BuyChk_Product.getP_id());
				if (Product_Buyer_List.size() != 0) {
					for (Order Buy_Member : Product_Buyer_List) { // 제품번호에 해당하는 제품을 주문한 모든 주문자에게 적립금으로 반환,
						sMapper.EndPriceMember(BuyChk_Product.getP_endprice(), BuyChk_Product.getP_id(), Buy_Member.getO_member_m_fk(),
								BuyChk_Product.getP_sell(), BuyChk_Product.getP_price());
					}
					Product_Buyer_List.clear();
				}
			}
		}

	}

}
