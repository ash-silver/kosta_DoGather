package com.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

//	@GetMapping("")
//	public String orderSheet() {
//		return "ordersheet";
//	}


	@GetMapping("/{p_id}")
	public String addOrder(PurchaseModel order, @PathVariable int p_id, Model model) {
		System.out.println("ddd"+order);
		
		Map<String, Object> promap = pService.FindProduct(p_id);
		System.out.println("bbbbb"+promap);
		model.addAttribute("promap", promap);
		return "ordersheet";
	}
}
