package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.Chart;
import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Order;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.ChartService;
import com.project.service.IndexService;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService pService;
	private final ChartService cService;
	private final IndexService iService;
	@Value("${chart.OneWeek}")
	private String OneWeek;

	/* ==================================================================== */
	@GetMapping("")
	public String ProductAddForm(Principal prin, Model model) {
		model.addAttribute("name", prin.getName());
		return "productadd";
	}

	@PostMapping("")
	public String AddProduct(Product pro, RedirectAttributes re) throws Exception {
		pService.AddProduct(pro);
		re.addAttribute("p_id", pro.getP_id());
		return "redirect:/products/options";
	}

	@ResponseBody
	@DeleteMapping("")
	public String OptionRemoveProduct(int opt_pid_p_fk) {
		Option pro_opt_chk = pService.option_chk(opt_pid_p_fk);
		if (pro_opt_chk == null) {
			pService.OptionRemoveProduct(opt_pid_p_fk);
			return "옵션이 존재하지 않아 해당 제품은 삭제 처리되었습니다.";
		} else {
			return "옵션 저장 및 제품 추가 완료";
		}
	}

	/* ==================================================================== */
	@GetMapping("/{p_id}/info")
	public String ProductUpdateForm(@PathVariable int p_id, Model model) {
		Map<String, Object> promap = pService.FindProduct(p_id);
		model.addAttribute("promap", promap);
		return "productupdate";
	}
	@PutMapping("/{p_id}/info")
	public String ProductUpdate(@PathVariable int p_id, Model model, Product pro, int dis_length) throws Exception {

		pService.UpdateProduct(pro, dis_length);
		return "redirect:/products/options/" + p_id + "/info";
	}

	@DeleteMapping("/{p_id}/info")
	public String ProductRemove(@PathVariable int p_id) throws Exception {
		pService.RemoveEvent(p_id);
		return "redirect:/products/add/lists";

	}
	
	@GetMapping("/{p_id}")
	public String ProductDetail(@PathVariable int p_id, Model model,String r_pnickname_m_fk) {
		Map<String, Object> promap = pService.FindProduct(p_id);
		int reviewct = iService.reviewct(r_pnickname_m_fk);
		double reviewstar = iService.reviewStar(r_pnickname_m_fk);
		reviewstar = Math.round(((reviewstar/reviewct)*10)/10.0);
		model.addAttribute("promap", promap);
		model.addAttribute("p_id", p_id);
		model.addAttribute("reviewct", reviewct);
		model.addAttribute("reviewstar", reviewstar);
		return "productdetail";
	}

	@GetMapping("/options")
	public String OptionAddForm(Model model, int p_id) {
		Option opt = pService.option_chk(p_id);
		model.addAttribute("opt", opt);
		model.addAttribute("p_id", p_id);
		return "option";
	}

	@GetMapping("/options/{p_id}/info")
	public String OptionEditForm(@PathVariable int p_id, Model model) {
		Map<String, Object> optmap = pService.Option_List(p_id);
		model.addAttribute("optmap", optmap);
		return "optionUpdate";
	}

	@DeleteMapping("/options/{opt_option1}/info/{opt_pid_p_fk}")
	public String OptionRemove(@PathVariable String opt_option1, @PathVariable int opt_pid_p_fk) {
		pService.OptionRemove(opt_option1, opt_pid_p_fk);
		return "redirect:/products/options/" + opt_pid_p_fk + "/info";
	}

	@DeleteMapping("/options/{opt_id}/info")
	public String OneOptionRemove(@PathVariable int opt_id, int opt_pid_p_fk) {
		pService.OneOptionRemove(opt_id);
		return "redirect:/products/options/" + opt_pid_p_fk + "/info";
	}

	@ResponseBody
	@GetMapping("/options/{p_id}")
	public List<Option> FindOption2(String opt_option1, @PathVariable int p_id) {
		return pService.FindOption2(opt_option1, p_id);
	}

	@ResponseBody
	@PostMapping("/options")
	public String AddOption2(Option opt, int opt_pid_p_fk) {
		if (opt.getOpt_opt1_list() == null) {
			for (int i = 0; i < opt.getOpt_opt2_list().size(); i++) {
				if (!opt.getOpt_opt2_list().get(i).isEmpty()) {
					Option option = Option.builder().opt_pid_p_fk(opt_pid_p_fk).opt_option1(opt.getOpt_option1())
							.opt_option2(opt.getOpt_opt2_list().get(i)).opt_quantity(opt.getOpt_quantity_list().get(i))
							.build();
					pService.AddOption(option);
				}
			}
			return "AllOptionAdd";
		} else {
			for (int i = 0; i < opt.getOpt_opt1_list().size(); i++) {
				Option option = Option.builder().opt_pid_p_fk(opt_pid_p_fk).opt_option1(opt.getOpt_opt1_list().get(i))
						.opt_quantity(opt.getOpt_quantity_list().get(i)).build();
				pService.AddOption(option);
			}
		}
		return "OneOptionAdd";
	}

	@GetMapping("/{keyword}/lists")
	@ExceptionHandler(NullPointerException.class)
	public String myform(Principal principal, Model model, @ModelAttribute("params") SearchDto params,
			@PathVariable String keyword) {
		String id = principal.getName();
		PagingResponse<Product> pro = pService.WriterProductlist(id, params, keyword);
		List<Img> img_name = new ArrayList<>();
		for (Product img : pro.getList()) {
			img_name.addAll(img.getImg());
		}
		Map<String, Object> sell_cnt = pService.All_SellPrice(id);
		List<Chart> chart = cService.OneWeekChart(id, OneWeek);
		model.addAttribute("img", img_name);
		model.addAttribute("chart", chart);
		model.addAttribute("sell_cnt", sell_cnt);
		model.addAttribute("prolist", pro);
		model.addAttribute("keyword", keyword);
		return "mypage";
	}

	@GetMapping("/{keyword}/lists/buy")
	public String BuyerList(Principal principal, Model model,@ModelAttribute("params") SearchDto params,
			@PathVariable String keyword) {
		String id = principal.getName();
		PagingResponse<Order> order = pService.BuyProduct(id, params,keyword);
		Map<String, Object> sell_cnt = pService.All_SellPrice(id);
		model.addAttribute("sell_cnt", sell_cnt);
		model.addAttribute("order", order);
		model.addAttribute("keyword", keyword);
		model.addAttribute("tag",params.getTag());
		return "buymember";
	}

	@GetMapping("/charts/{day}/{month}")
	public String chart(@PathVariable(required = false) String day, @PathVariable(required = false) String month,
			Principal principal, Model model) {
		String id = principal.getName();
		Map<String, Object> ChartMap = new HashMap<>();
		ChartMap = cService.AllChartList(id, day, month);
		model.addAttribute("ChartMap", ChartMap);
		return "chart";
	}

	@ResponseBody
	@GetMapping("/options/chk")
	public Option FindOptionAddChk(int opt_pid_p_fk) {
		Option opt = pService.option_chk(opt_pid_p_fk);
		return opt;
	}

}
