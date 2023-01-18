package com.project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	
	@Autowired
	private final ProductService pService;
	
	
	public PagingResponse<Product> imgsplit(PagingResponse<Product> list){		
		if (!list.getList().isEmpty()) { // 나누기위해 저장시켜놓은 이미지 파일의 명을 잘라내기 위한 메서드. 페이징처리된 List에서 getImg값을 가져옴
			for (int i = 0; i < list.getList().size(); i++) {
				String[] img = list.getList().get(i).getImg_name().split("/");
				String imgtxt = img[0];
				list.getList().get(i).setImg_name(imgtxt);
			}
		}
		return list;
	}
	
	/*   -------------------------메인페이지-----------------------------	 */
	
	@GetMapping("/")
	public String mainPage(Model model) {
		ArrayList<Product> bestlist = pService.Productbest();
		ArrayList<Product> newlist = pService.Productnew();
		for(int i=0;i<bestlist.size();i++) {
			String[] bestimg = bestlist.get(i).getImg_name().split("/");//인기순
			String[] newimg = newlist.get(i).getImg_name().split("/");//신상품순
			bestlist.get(i).setImg_name(bestimg[0]);
			newlist.get(i).setImg_name(newimg[0]);
		}
		model.addAttribute("bestlist", bestlist);		
		model.addAttribute("newlist", newlist);
		
		return "index";
	}
	
	@GetMapping("/index")
	public String mainPage2(Model model) {
		ArrayList<Product> bestlist = pService.Productbest();
		ArrayList<Product> newlist = pService.Productnew();
		for(int i=0;i<bestlist.size();i++) {
			String[] bestimg = bestlist.get(i).getImg_name().split("/");//인기순
			String[] newimg = newlist.get(i).getImg_name().split("/");//신상품순
			bestlist.get(i).setImg_name(bestimg[0]);
			newlist.get(i).setImg_name(newimg[0]);
		}
		model.addAttribute("bestlist", bestlist);		
		model.addAttribute("newlist", newlist);		
		
		return "index";
	}
	
	
	
	/*   -------------------------페이징-----------------------------	 */
	
	@GetMapping("/list")
	public String PagingList(@ModelAttribute("params") SearchDto params, Model model) {
		PagingResponse<Product> plist = pService.PagingList(params);
		
		model.addAttribute("plist", imgsplit(plist));
		
		
		return "prodlist";
	}
	
//	@GetMapping("/#{keyword}")
//	public String LineUpPage(@ModelAttribute("params") SearchDto params, Model model,@PathVariable String keyword) {
//		String[] keyword1 = keyword.split("-");
//		
//		System.out.println(keyword1[0]);
//		System.out.println(keyword1[1]);
//		PagingResponse<Product> plist = pService.Line_Up(params,keyword1[0],keyword1[1]);
//		
//		model.addAttribute("plist", imgsplit(plist));
//		
//		return "prodlist";
//	}
	
	@GetMapping("/category")
	public String category(@ModelAttribute("params") SearchDto params, String p_category, Model model) {
		PagingResponse<Product> plist = pService.Category(params, p_category);
		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);
		return "prodlist";
	}

}
