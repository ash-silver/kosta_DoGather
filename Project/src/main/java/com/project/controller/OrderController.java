package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.Order;
import com.project.service.OrderService;
import com.project.service.ProductService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService oService;
	private final ProductService pService;
	static public ArrayList<Order> items;
	
	@ResponseBody
	@PostMapping("/AddCart")
	public void AddCart(Principal principal, Order order) {
		oService.AddCart(principal, order);
		
	}


	@GetMapping("/findCart")
	public String findCart(Model model,Principal principal){
		String m_nickname = principal.getName();
		ArrayList<Order> order = oService.findCart(m_nickname);
		for(Order ord:order) {
			System.out.println(ord);
		}
		model.addAttribute("order",order);
		return "cart";
	};
	
	
	
	
	
}
