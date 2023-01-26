package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.model.PurchaseModel;
import com.project.service.ProductService;
import com.project.service.orderSheetService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ordersheet")
public class orderSheetController {

	@Autowired
	private orderSheetService orderSheetService;

	@Autowired
	private ProductService pService;

	@GetMapping("")
	public String orderSheet() {
		return "ordersheet";
	}

	@GetMapping("/{p_id}")
	public String addOrder(@PathVariable("p_id") int p_id) {
		System.out.println("dddd");
		return "ordersheet";
	}

	@PostMapping("/{p_id}")
	public String addOrder(@PathVariable("p_id") int p_id, PurchaseModel order) {
		orderSheetService.AddOrder(order, p_id);
		return "redirect:/ordersheet";
	}
}
