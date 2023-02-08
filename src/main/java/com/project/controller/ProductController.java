package com.project.controller;
/*
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.Option;
import com.project.model.Product;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private final ProductService pService;

	@GetMapping("")
	public String ProAddForm() {
		return "productadd";
	}
	

	@PostMapping("")
	public String AddProduct(Product pro, RedirectAttributes re, String[] p_discount_quan, String[] p_discount_count)
			throws Exception {
		re.addFlashAttribute("p_id", pro.getP_id());
		return "redirect:/products/options";
	}

	@GetMapping("/{p_id}")
	public String ProdetailForm(@PathVariable int p_id, Model model) {
		Product pro = pService.FindProduct(p_id);
		List<String> overlap_check = new ArrayList<String>();
		
	}	
		return chk;
	}
	
	

}*/
