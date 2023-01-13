package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	
	@Autowired
	private final ProductService productService;
	
	
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
	
	
	
	/*   -------------------------페이징-----------------------------	 */
	
	@GetMapping("/list")
	public String PagingList(@ModelAttribute("params") SearchDto params, Model model) {
		PagingResponse<Product> plist = productService.PagingList(params);
		
		model.addAttribute("plist", imgsplit(plist));
		
		return "prodlist";
	}
	

}
