package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Product;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	
	private final ProductService pService;
	
	
	/*===============================Form =====================================*/
	@GetMapping("")
	public String ProductAddForm() {
		return "productadd";
	}
	@GetMapping("/options")
	public String OptionAddForm() {
		return "option";
	}
	/*===============================Form =====================================*/
	
	
	@PostMapping("")
	public String AddProduct(Product pro, RedirectAttributes re)
			throws Exception {
		pService.AddProduct(pro);
		re.addFlashAttribute("p_id", pro.getP_id());
		return "redirect:/products/options";
	}

	
	@DeleteMapping("")
	public String DelProduct(@RequestParam int p_id) {
		pService.removeProduct(p_id);
		return null;
	}
	
	
	@GetMapping("/{p_id}")
	public String ProductDetail(@PathVariable int p_id,Model model) {
		Map<String, Object> promap= pService.FindProduct(p_id);
		
		model.addAttribute("promap",promap);
		return "productdetail";
	}



	@ResponseBody
	@GetMapping("/options/{p_id}")
	public List<Option> FindOption2(String opt_option1, @PathVariable int p_id) {
		return pService.FindOption2(opt_option1, p_id);
	}
	
	@ResponseBody
	@PostMapping("/options")
	public void AddOption(String opt_option1, @RequestParam("opt_option2") String[] opt_option2,
			@RequestParam("opt_quantity") String[] opt_quantity, int opt_pid) {
		int index=0;
		for (String opt_2:opt_option2) {
			if (!opt_2.isEmpty()) {
				Option option = Option.builder()
						.opt_pid(opt_pid)
						.opt_option1(opt_option1)
						.opt_option2(opt_2)
						.opt_quantity(opt_quantity[index]).build();
				index++;
				pService.AddOption(option);
			}
		}
	}
	
	@GetMapping("/lists")
	public String myform(Principal principal, Model model) {
		String id = principal.getName();
		List<Product> pro=pService.WriterProductlist(id);
		List<Img> img_name=new ArrayList<>();
		for(Product img:pro) {
			img_name.addAll(img.getImg());
		}
		
		model.addAttribute("img_name",img_name);
		model.addAttribute("pro",pro);
		return "mypage";
	}

}
