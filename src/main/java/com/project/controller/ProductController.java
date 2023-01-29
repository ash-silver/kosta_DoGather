package com.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.Img;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor   
public class ProductController {
	
	private final ProductService pService;
	

	
	
	/*   -------------------------메인페이지-----------------------------	 */
	
	@GetMapping("/") 
	public String mainPage(Model model) {
		ArrayList<Product> bestlist = pService.Productbest();
		ArrayList<Product> newlist = pService.Productnew();
		
		

		model.addAttribute("bestlist", bestlist);
		model.addAttribute("newlist", newlist);
		
		return "index";
	}

	
	/*   -------------------------페이징-----------------------------	 */
							
	@GetMapping("/newlist")
	public String newlist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.newlist(params, p_category);
		

		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);

		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/pricelist")
	public String pricelist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {

		PagingResponse<Product> plist = pService.pricelist(params, p_category);

		
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/pricelistdesc")
	public String pricelistdesc(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.pricelistdesc(params, p_category);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/bestlist")
	public String bestlist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.bestlist(params, p_category);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/list")
	public String PagingList(@ModelAttribute("params") SearchDto params, Model model) {
		PagingResponse<Product> plist = pService.PagingList(params);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		
		
		return "prodlist";
	}
	
	
	@GetMapping("/category")
	public String category(@ModelAttribute("params") SearchDto params, String p_category, Model model) {
		PagingResponse<Product> plist = pService.Category(params, p_category);
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		return "prodlist";
	}
	
	@GetMapping("/Search")
	public String Search(@ModelAttribute("params") SearchDto params, Model model,
			@RequestParam("keyword") String keyword, @RequestParam("search") String search) {
		PagingResponse<Product> plist = pService.Search(params, keyword, search);
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		} 
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		return "prodlist";
	}

}
