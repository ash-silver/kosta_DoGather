package com.project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor   //requestmapping으로 기본 매핑값 주는게..?
public class ProductController {
	
	private final ProductService pService;
	
	 // 이거는 왜 놔둔거임..? 우리 img db가있는데 imgsplit를 왜 써....
	public PagingResponse<Product> imgsplit(PagingResponse<Product> list){		
		if (!list.getList().isEmpty()) { // 나누기위해 저장시켜놓은 이미지 파일의 명을 잘라내기 위한 메서드. 페이징처리된 List에서 getImg값을 가져옴
			for (int i = 0; i < list.getList().size(); i++) {
				String[] img = list.getList().get(i).getImg().get(i).getImg_name().split("/");
				String imgtxt = img[0];
				list.getList().get(i).getImg().get(i).setImg_name(imgtxt);
			}
		}
		return list;
	}
	
	/*   -------------------------메인페이지-----------------------------	 */
	
	@GetMapping("/") // 이거랑 하단에 GetMapping("/index")는 같은건데 왜 2개를 따로나눳냐 ... 매핑2개같이하는거 방법 찾아서 바꿔라..
	public String mainPage(Model model) {
		ArrayList<Product> bestlist = pService.Productbest();
		ArrayList<Product> newlist = pService.Productnew();
		for(int i=0;i<bestlist.size();i++) { // 아니....xml에 조인도안했는데 왜 이미지를 Get해서 split하고있냐구요...
			String[] bestimg = bestlist.get(i).getImg().get(i).getImg_name().split("/");//인기순
			String[] newimg = newlist.get(i).getImg().get(i).getImg_name().split("/");//신상품순
			bestlist.get(i).getImg().get(i).setImg_name(bestimg[0]);
			newlist.get(i).getImg().get(i).setImg_name(newimg[0]);
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
			String[] bestimg = bestlist.get(i).getImg().get(i).getImg_name().split("/");;//인기순
			String[] newimg = newlist.get(i).getImg().get(i).getImg_name().split("/");;//신상품순
			bestlist.get(i).getImg().get(i).setImg_name(bestimg[0]);
			newlist.get(i).getImg().get(i).setImg_name(newimg[0]);
		}
		model.addAttribute("bestlist", bestlist);		
		model.addAttribute("newlist", newlist);		
		
		return "index";
	}
	
	
	
	/*   -------------------------페이징-----------------------------	 */
							// 저기 페이징에 왜 전부다 Mapping이름만 다르고 같은 Service를 가져오고있음...???
							
	@GetMapping("/newlist")
	public String newlist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.PagingList(params);
		
		model.addAttribute("plist", imgsplit(plist)); // split 쓰지마임마... 왜써 img db를 조인해서 가져올생각을 해야지
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/pricelist")
	public String pricelist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.PagingList(params);  //
		
		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/pricelistdesc")
	public String pricelistdesc(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.PagingList(params);
		
		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/bestlist")
	public String bestlist(@ModelAttribute("params") SearchDto params, Model model, String p_category) {
		PagingResponse<Product> plist = pService.PagingList(params);
		
		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping("/list")
	public String PagingList(@ModelAttribute("params") SearchDto params, Model model) {
		PagingResponse<Product> plist = pService.PagingList(params);
		
		model.addAttribute("plist", imgsplit(plist));
		
		
		return "prodlist";
	}
	
	
	@GetMapping("/category")
	public String category(@ModelAttribute("params") SearchDto params, String p_category, Model model) {
		PagingResponse<Product> plist = pService.Category(params, p_category);
		model.addAttribute("plist", imgsplit(plist));
		model.addAttribute("category", p_category);
		return "prodlist";
	}
	
	@GetMapping("/Search")
	public String Search(@ModelAttribute("params") SearchDto params, Model model,
			@RequestParam("keyword") String keyword, @RequestParam("search") String search) {
		PagingResponse<Product> plist = pService.Search(params, keyword, search);
		model.addAttribute("prolist", imgsplit(plist));
		return "prodlist";
	}

}
