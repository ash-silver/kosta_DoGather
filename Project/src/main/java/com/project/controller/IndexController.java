package com.project.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.IndexService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final IndexService iService;

	public PagingResponse<Product> imgsplit(PagingResponse<Product> list) {
		if (!list.getList().isEmpty()) { // 나누기위해 저장시켜놓은 이미지 파일의 명을 잘라내기 위한 메서드. 페이징처리된 List에서 getImg값을 가져옴
			for (int i = 0; i < list.getList().size(); i++) {
				String[] img = list.getList().get(i).getImg().get(i).getImg_name().split("/");
				String imgtxt = img[0];
				list.getList().get(i).getImg().get(i).setImg_name(imgtxt);
			}
		}
		return list;
	}

	/* -------------------------메인페이지----------------------------- */

	@GetMapping({"/","/index"})   // GetMapping   / 랑 /index는 같은 역할인데 왜 2개로 나눠놨냐..... 하나로 합치는방법 찾아봐야지,,,,,
	public String mainPage(Model model) {
		ArrayList<Product> bestlist = iService.Productbest();
		ArrayList<Product> newlist = iService.Productnew(); 
		for (int i = 0; i < bestlist.size(); i++) { // ?????,,,, 여기서 사진이 어떻게 나오는거임..??? xml에서 조인을 안했는데??.....
			String[] bestimg = bestlist.get(i).getImg().get(i).getImg_name().split("/");// 인기순
			String[] newimg = newlist.get(i).getImg().get(i).getImg_name().split("/");// 신상품순
			bestlist.get(i).getImg().get(i).setImg_name(bestimg[0]);
			newlist.get(i).getImg().get(i).setImg_name(newimg[0]);
		}
		model.addAttribute("bestlist", bestlist);
		model.addAttribute("newlist", newlist);

		return "index";
	}


	/* -------------------------페이징----------------------------- */

	@GetMapping("/newlist")
	public String newlist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = iService.PagingList(params);

		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);

		return "prodlist";
	}

	@GetMapping("/pricelist")
	public String pricelist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = iService.PagingList(params);

		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);

		return "prodlist";
	}

	@GetMapping("/pricelistdesc")
	public String pricelistdesc(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = iService.PagingList(params);

		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);

		return "prodlist";
	}

	@GetMapping("/bestlist")
	public String bestlist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = iService.PagingList(params);

		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);

		return "prodlist";
	}

	@GetMapping("/list")
	public String PagingList(@ModelAttribute("params") SearchDto params, Model model) {
		PagingResponse<Product> plist = iService.PagingList(params);

		model.addAttribute("plist", imgsplit(plist));

		return "prodlist";
	}

	@GetMapping("/category")
	public String category(@ModelAttribute("params") SearchDto params, String p_category, Model model) {
		PagingResponse<Product> plist = iService.Category(params, p_category);
		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);
		return "prodlist";
	}

	@GetMapping("/Search")
	public String Search(@ModelAttribute("params") SearchDto params, Model model,
			@RequestParam("keyword") String keyword, @RequestParam("search") String search) {
		PagingResponse<Product> plist = iService.Search(params, keyword, search);
		model.addAttribute("prolist", imgsplit(plist));
		return "prodlist";
	}
}
