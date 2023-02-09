package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.Img;
import com.project.model.Order;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.Review;
import com.project.model.SearchDto;
import com.project.service.IndexService;

import ch.qos.logback.core.subst.Token.Type;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	private final IndexService iService;

/*   -------------------------메인페이지-----------------------------	 */
	
	@GetMapping("/index") 
	public String mainPage(Model model) {
		ArrayList<Product> bestlist = iService.Productbest();
		ArrayList<Product> newlist = iService.Productnew();
			
		
		model.addAttribute("bestlist", bestlist);
		model.addAttribute("newlist", newlist);
		
		return "index";
	}

	
	/*   -------------------------페이징-----------------------------	 */
							
	@GetMapping(value = {"/newlist/{category}","/newlist"})
	public String newlist(@ModelAttribute("params") SearchDto params, Model model, @PathVariable(name="category",required = false) String p_category) {
		PagingResponse<Product> plist = iService.newlist(params, p_category);
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		}
		
		System.out.println(p_category);
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping(value = {"/pricelist/{category}","/pricelist"})
	public String pricelist(@ModelAttribute("params") SearchDto params, Model model, @PathVariable(name="category",required = false) String p_category) {

		PagingResponse<Product> plist = iService.pricelist(params, p_category);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		}

		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping(value = {"/pricelistdesc/{category}", "/pricelistdesc"})
	public String pricelistdesc(@ModelAttribute("params") SearchDto params, Model model, @PathVariable(name="category",required = false) String p_category) {
		PagingResponse<Product> plist = iService.pricelistdesc(params, p_category);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		}
		
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		
		return "prodlist";
	}
	@GetMapping(value = {"/bestlist/{category}", "/bestlist"})
	public String bestlist(@ModelAttribute("params") SearchDto params, Model model, @PathVariable(name="category",required = false) String p_category) {
		PagingResponse<Product> plist = iService.bestlist(params, p_category);
		
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
		PagingResponse<Product> plist = iService.PagingList(params);
		
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
		
		PagingResponse<Product> plist = iService.Category(params, p_category);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		}
		
		
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		model.addAttribute("category", p_category);
		return "prodlist";
	}
	
	@GetMapping("/search")
	public String Search(@ModelAttribute("params") SearchDto params, Model model,
			@RequestParam("keyword") String keyword, @RequestParam("search") String search) {
		PagingResponse<Product> plist = iService.Search(params, keyword, search);
		
		List<Img> img_name = new ArrayList<>();
		for (Product img : plist.getList()) {
			img_name.addAll(img.getImg());
		}
		
		model.addAttribute("img", img_name);
		model.addAttribute("plist", plist);
		return "prodlist";
	}
	
	/* ---------------------------- Review ------------------------*/
	
	@ResponseBody
	@GetMapping("/reviews")
	public Map<String, Object> Reviewlist(@ModelAttribute("params") SearchDto params, int r_pid_p_fk, Principal prin){// 페이징 처리를 위한 SearchDTO를 가져옴,어떤 상품의 리뷰를 가져올지 체크하기 위한 제품번호
		
		PagingResponse<Review> prolist = iService.AllReview(params, r_pid_p_fk);
		
		Map<String, Object> result = new HashMap<String, Object>(); // 리턴시켜야 하는 값의 자료형이 여러개이기 때문에 HashMap안에 담아서 KEY값으로 한번에 모아서 처리,
		
		int reviewct = iService.reviewct(r_pid_p_fk);
		double reviewstar = iService.reviewStar(r_pid_p_fk);
		reviewstar = Math.round(((reviewstar/reviewct)*10)/10.0);
		
		List<Img> img_name = new ArrayList<>();
		
		for (Review img : prolist.getList()) {
			img_name.addAll(img.getImg());
		}
		String m_nickname = iService.findnick(prin.getName());
		
		
		result.put("params", params);
		result.put("prolist", prolist.getList());
		result.put("pagination", prolist.getPagination());
		result.put("reviewct", reviewct);
		result.put("reviewstar", reviewstar);
		result.put("img", img_name);
		result.put("m_nickname", m_nickname);
		
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("/reviews")
	public void addReview(Review r, Principal prin) throws Exception {
		
		
		String nick = iService.findnick(prin.getName());
		if(nick == null || nick == "") {
			nick = prin.getName();
		}
		r.setR_nickname_m_fk(nick);
		
		iService.ReviewAdd(r);
		
		
	}

	@ResponseBody
	@DeleteMapping("/reviews")
	public void DelReview(@RequestParam int r_id) {
		
		iService.ReviewDel(r_id);
		
		
	}
	
	@ResponseBody
	@GetMapping("/comment")
	public boolean checkcomment(Principal prin,@RequestParam int p_id) {
		boolean check = false;
		
		int index = iService.checkComments(prin.getName(), p_id);
		
		if(index>0) {
			check = true;
		}
		
		return check;
	}
	
}
