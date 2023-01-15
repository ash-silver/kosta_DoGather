package com.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public String AddProduct(Product pro, RedirectAttributes re, String[] p_discount_quan, String[] p_discount_count)
			throws Exception {
		pService.AddProduct(pro, p_discount_count, p_discount_quan);
		pService.AddImg(pro);
		re.addFlashAttribute("p_id", pro.getP_id());
		return "redirect:/products/options";
	}

	@GetMapping("/{p_id}")
	public String ProdetailForm(@PathVariable int p_id, Model model) {
		Product pro = pService.FindProduct(p_id);
		List<String> chk1 = new ArrayList<String>();
		for (Option opt1 : pro.getOption()) {
			chk1.add(opt1.getOpt_option1());
		}
		List<String> opt_option1=chk1.stream().distinct().collect(Collectors.toList()); // 옵션간의 중복제거				
		String[] p_discount =pro.getP_discount().split("/");	
		String[] p_disquantity =pro.getP_disquantity().split("/");
		int discountprice=0;
		for(int i=0;i<p_disquantity.length;i++) {
			if(Integer.parseInt(p_disquantity[i])<=pro.getP_sell()){
				discountprice=  pro.getP_price()-((pro.getP_price()/100)*Integer.parseInt(p_discount[i])); 
			}
		}
		
		model.addAttribute("discountprice",discountprice);
		model.addAttribute("p_discount",p_discount);
		model.addAttribute("p_disquantity",p_disquantity);
		model.addAttribute("opt_option1",opt_option1);
		model.addAttribute("img", pro.getImg());
		model.addAttribute("pro", pro);
		return "productdetail";
	}

	@GetMapping("/options")
	public String OptionForm() {
		return "option";
	}
	
	@ResponseBody
	@GetMapping("/categorys")
	public List<Option> Category(String opt_option1,int p_id) {		
		
		return pService.FindCategory(opt_option1, p_id);
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
