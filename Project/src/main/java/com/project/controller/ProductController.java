package com.project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Product;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private final ProductService pService;

	@GetMapping("")
	public String ProAddForm() {
		return "productadd";
	}

	@PostMapping("")
	public String AddProduct(Product pro, RedirectAttributes re) throws Exception {
		pService.AddProduct(pro);
		pService.AddImg(pro);
		re.addFlashAttribute("p_id", pro.getP_id());
		return "redirect:/product/options";
	}
	
	@GetMapping("/{p_id}")
	public String ProdetailForm(@PathVariable int p_id,Model model) {
		ArrayList<Img> img=pService.FindProduct(p_id);
		Product pro=img.get(0).getProduct();
		
		model.addAttribute("pro",pro);
		model.addAttribute("img",img);
		return "productdetail";
	}

	
	@GetMapping("/options")
	public String OptionForm() {
		return "option";
	}
	@ResponseBody
	@PostMapping("/options")
	public String addOption(String opt_option1, @RequestParam("opt_option2") String[] opt_option2,
			@RequestParam("opt_quantity") String[] opt_quantity, int opt_pid) {
		String chk = "추가실패";
		for (int i = 0; i < opt_option2.length; i++) {
			if (!opt_option2[i].isEmpty()) {
				Option option = Option.builder().opt_pid(opt_pid).opt_option1(opt_option1).opt_option2(opt_option2[i])
						.opt_quantity(opt_quantity[i]).build();
				pService.AddOption(option);
				chk = "추가 성공 :" + opt_option1;
			}
		}
		return chk;
	}

}
