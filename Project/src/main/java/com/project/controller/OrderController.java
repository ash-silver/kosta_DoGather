package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.Img;
import com.project.model.Order;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService oService;

	@ResponseBody
	@PostMapping("/carts")
	public void AddCart(Principal principal, Order order) {
		oService.AddCart(principal, order);

	}

	@GetMapping("/carts")
	public String findCart(Model model, Principal principal) {
		String m_nickname = principal.getName();
		ArrayList<Order> order = oService.findCart(m_nickname);
		for (Order ord : order) {
		}
		model.addAttribute("order", order);
		return "cart";
	}

	@PutMapping("/carts")
	public String cartBuyItem(int[] o_id, int[] o_quantity, int[] o_product_p_fk) {
		for (int i = 0; i < o_id.length; i++) {
			oService.cartBuyItem(o_id[i], o_quantity[i], o_product_p_fk[i]);
		}
		return "redirect:/orders/carts";
	}

	@DeleteMapping("/carts")
	@ResponseBody
	public void delCartItem(int[] o_id) {
		for (int a : o_id) {
			oService.delCartItem(a);
		}
	}

	// 구매내역
	@GetMapping("/buylists")
	public String buyList(Model model, Principal principal, SearchDto params) {
		String m_nickname = principal.getName();
		List<Product> product = new ArrayList<>();
		List<Img> img = new ArrayList<>();
		PagingResponse<Order> ordlist = oService.buyList(m_nickname, params);
		for (Order ord : ordlist.getList()) {
			product.add(ord.getProduct());
			img.add(ord.getImg());
		}
		model.addAttribute("img", img);
		model.addAttribute("product", product);
		model.addAttribute("params", params);
		model.addAttribute("ordlist", ordlist);

		return "buylist";
	}


	@GetMapping("/delivery")
		public String delivery() {
			
		return"delivery";
		}
	}
	
	
	

