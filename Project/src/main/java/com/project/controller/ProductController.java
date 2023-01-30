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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.Img;
import com.project.model.Option;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;
import com.project.service.ProductService;
import com.project.service.SchedulerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService pService;
	

	/* ==================================================================== */
	@GetMapping("")
	public String ProductAddForm(Principal prin, Model model) {

		model.addAttribute("name", prin.getName());
		return "productadd";
	}

	@PostMapping("")
	public String AddProduct(Product pro, RedirectAttributes re) throws Exception {
		pService.AddProduct(pro);
		re.addFlashAttribute("p_id", pro.getP_id());
		return "redirect:/products/options";
	}

	@DeleteMapping("")
	public String DelProduct(@RequestParam int p_id) {
		pService.removeProduct(p_id);
		return null;
	}

	/* ==================================================================== */
	@GetMapping("/{p_id}/info")
	public String ProductUpdateForm(@PathVariable int p_id, Model model) {
		Map<String, Object> promap = pService.FindProduct(p_id);
		model.addAttribute("promap", promap);
		return "productupdate";
	}

	@PutMapping("/{p_id}/info")
	public String ProductUpdate(@PathVariable int p_id, Model model, Product pro) throws Exception {
		pService.UpdateProduct(pro);
		return "redirect:/products/options/" + p_id + "/info";
	}

	@DeleteMapping("/{p_id}/info")
	public String ProductRemove(@PathVariable int p_id) throws Exception {
		pService.removeProduct(p_id);
		return "redirect:/products/add/lists";

	}

	@GetMapping("/{p_id}/detail")
	public String ProductDetail(@PathVariable int p_id, Model model) {
		Map<String, Object> promap = pService.FindProduct(p_id);

		model.addAttribute("promap", promap);
		return "productdetail";
	}

	@GetMapping("/options")
	public String OptionAddForm() {
		return "option";
	}

	@GetMapping("/options/{p_id}/info")
	public String OptionEditForm(@PathVariable int p_id, Model model) {
		Map<String, Object> optmap = pService.Option_List(p_id);

		model.addAttribute("optmap", optmap);
		return "optionUpdate";
	}

	@DeleteMapping("/options/{opt_name}/info")
	public String OptionRemove(@PathVariable String opt_name, int opt_pid) {
		pService.OptionRemove(opt_name);
		return "redirect:/products/options/" + opt_pid + "/info";
	}

	@ResponseBody
	@GetMapping("/options/{p_id}")
	public List<Option> FindOption2(String opt_option1, @PathVariable int p_id) {
		return pService.FindOption2(opt_option1, p_id);
	}

	@ResponseBody
	@PostMapping("/options")
	public void AddOption2(String opt_option1, @RequestParam("opt_option2") String[] opt_option2,
			@RequestParam("opt_quantity") String[] opt_quantity, int opt_pid) {
		int index = 0;
		for (String opt_2 : opt_option2) {
			if (!opt_2.isEmpty()) {
				Option option = Option.builder().opt_pid_p_fk(opt_pid).opt_option1(opt_option1).opt_option2(opt_2)
						.opt_quantity(opt_quantity[index]).build();
				index++;
				pService.AddOption(option);
			}
		}
	}
	@ResponseBody
	@DeleteMapping("/imgs")
	public String RemoveImg(String img_name) {
		pService.ImgRemove(img_name);
		return img_name+"삭제완료";
	}
	@GetMapping("/{keyword}/lists")
	public String myform(Principal principal, Model model, @ModelAttribute("params") SearchDto params,
			@PathVariable String keyword, String searching) {
		String id = principal.getName();
		PagingResponse<Product> pro = pService.WriterProductlist(id, params, keyword, searching);
		List<Img> img_name = new ArrayList<>();
		for (Product img : pro.getList()) {
			img_name.addAll(img.getImg());
		}
		Map<String, Object> sell_cnt = pService.All_SellPrice(id);
		model.addAttribute("img", img_name);
		model.addAttribute("sell_cnt", sell_cnt);
		model.addAttribute("prolist", pro);
		model.addAttribute("keyword", keyword);
		return "mypage";
	}
}
