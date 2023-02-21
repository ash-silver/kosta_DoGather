package com.project.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.mapper.ProductMapper;
import com.project.model.Discount;
import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Order;
import com.project.model.Pagination;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductMapper pMapper;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Value("${file.Upimg}")
	private String path;

	@Transactional // 트랜잭션 처리로 하위에 INSERT들이 진행도중 오류가 생긴다면 RollBack이 된다 (에외의 종류에 따라서 안될수도 있음)
	public void AddProduct(Product pro) throws Exception {
		pMapper.AddProduct(pro);
		AddDiscount(pro);
		AddImg(pro.getP_img(), pro.getP_id(), "p_img");
		AddImg(pro.getP_contentimg(), pro.getP_id(), "p_contentimg");
		CreateNewEvent(pro, "ADD");
	}

	@Transactional
	public void UpdateProduct(Product pro) throws Exception {
		pMapper.UpdateProduct(pro);
		EditDiscount(pro);
		CreateNewEvent(pro, "UPDATE");
		EditImg(pro.getP_img(), pro.getP_id(), "p_img");
		EditImg(pro.getP_contentimg(), pro.getP_id(), "p_contentimg");
	}

	@Transactional // 할인율 생성 ,수정
	public void AddDiscount(Product pro) {
		for (int i = 0; i < pro.getP_discount_count().size(); i++) {
			Discount dis = Discount.builder().dis_count(pro.getP_discount_count().get(i))
					.dis_quantity(pro.getP_discount_quan().get(i)).dis_pid_p_fk(pro.getP_id()).build();
			pMapper.AddDiscount(dis);

		}
	}

	@Transactional
	public void EditDiscount(Product pro) {
<<<<<<< HEAD
		List<Discount> dis_length = pMapper.Update_find(pro.getP_id());
		for (int i = 0; i < pro.getP_discount_count().size(); i++) {
			Discount dis = Discount.builder().dis_count(pro.getP_discount_count().get(i))
					.dis_quantity(pro.getP_discount_quan().get(i)).dis_pid_p_fk(pro.getP_id()).build();
			if (dis_length.size() < pro.getP_discount_count().size()) {
				if (i <= dis_length.size() - 1) {
					dis.setDis_id(dis_length.get(i).getDis_id());
=======
		int index = 0;
		List<Discount> dis_length = pMapper.Update_find(pro.getP_id());
		for (int discountlist : pro.getP_discount_count()) {
			Discount dis = Discount.builder().dis_count(discountlist).dis_quantity(pro.getP_discount_quan()[index])
					.dis_pid_p_fk(pro.getP_id()).build();
			if (dis_length.size() < pro.getP_discount_count().length) {
				if (index <= dis_length.size() - 1) {
					dis.setDis_id(dis_length.get(index).getDis_id());
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
					pMapper.UpdateDiscount(dis);
				} else {
					pMapper.AddDiscount(dis);
				}
			}
<<<<<<< HEAD
=======
			index++;
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		}

	}

	@Transactional // 이미지 생성 , 수정
	public void EditImg(List<MultipartFile> file, int p_id, String keyword) throws Exception {
		Img img_txt = Img.builder().img_keyword(keyword).img_pid_p_fk(p_id).build();
		List<Img> img_length = pMapper.img_length(img_txt);
		for (int j = 0; j < file.size(); j++) {
			if (!file.get(j).isEmpty()) {
				String origName = file.get(j).getOriginalFilename(); // 입력한 원본 파일의 이름
				String uuid = String.valueOf(UUID.randomUUID()); // toString 보다는 valueOf를 추천 , NPE에러 예방,
				String extension = origName.substring(origName.lastIndexOf(".")); // 원본파일의 파일확장자
				String savedName = uuid + extension; // 랜덤이름 + 확장자
				File converFile = new File(path, savedName); // path = 상품 이미지 파일의 저장 경로가 들어있는 프로퍼티 설정값
				if (!converFile.exists()) {
					converFile.mkdirs();
				}
				file.get(j).transferTo(converFile); // --- 실제로 저장을 시켜주는 부분 , 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생
				if (img_length.size() > j) {
					delimg(img_length.get(j));
					Img img = Img.builder().img_keyword(keyword).img_name(savedName).img_origname(origName)
							.img_pid_p_fk(p_id).img_id(img_length.get(j).getImg_id()).build();
					pMapper.UpdateImg(img);
				} else {
					Img img = Img.builder().img_keyword(keyword).img_name(savedName).img_origname(origName)
							.img_pid_p_fk(p_id).build();
					pMapper.AddImg(img);
				}
			}
		}
	}

	@Transactional // 이미지 생성 , 수정
	public void AddImg(List<MultipartFile> file, int p_id, String keyword) throws Exception {
		if (!CollectionUtils.isEmpty(file)) {
			for (MultipartFile imgfile : file) {
				String origName = imgfile.getOriginalFilename(); // 입력한 원본 파일의 이름
				String uuid = String.valueOf(UUID.randomUUID());// 문자+숫자의 랜덤한 파일명으로 변경
				String extension = origName.substring(origName.lastIndexOf(".")); // 원본파일의 파일확장자
				String savedName = uuid + extension; // 랜덤이름 + 확장자
				File converFile = new File(path, savedName); // path = 상품 이미지 파일의 저장 경로가 들어있는 프로퍼티 설정값
				if (!converFile.exists()) {
					converFile.mkdirs();
				}
				imgfile.transferTo(converFile); // --- 실제로 저장을 시켜주는 부분 , 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생
				Img img = Img.builder().img_keyword(keyword).img_name(savedName).img_origname(origName)
						.img_pid_p_fk(p_id).build();
				pMapper.AddImg(img);
			}
		}
	}

<<<<<<< HEAD
	public void delimg(Img i) {
=======
	public void delimg(Img i) { // 이미지를 전체삭제시 사용하는 공통 메서드
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		String delpath = path + i.getImg_name();
		File file1 = new File(delpath);
		file1.delete();
	}

	public void CreateNewEvent(Product pro, String type) {
		String value = "";
		if (type.equals("UPDATE")) {
			value = "DROP EVENT " + pro.getP_id() + "_start";
			pMapper.CreateNewEvent(value);
			value = "DROP EVENT " + pro.getP_id() + "_end";
			pMapper.CreateNewEvent(value);
		}
		value = "CREATE EVENT IF NOT EXISTS " + pro.getP_id() + "_start ON SCHEDULE AT '" + pro.getP_recruitdate()
				+ "' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'CHECK' DO UPDATE product set p_chk='start' WHERE p_id="
				+ pro.getP_id();
		pMapper.CreateNewEvent(value);
		value = "CREATE EVENT IF NOT EXISTS " + pro.getP_id() + "_end ON SCHEDULE AT '" + pro.getP_duedate()
				+ "' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'CHECK' DO UPDATE product set p_chk='end' WHERE p_id="
				+ pro.getP_id();
		pMapper.CreateNewEvent(value);
	}

	@Transactional
	public void AddOption(Option opt) {
		pMapper.AddOption(opt);
	}

	public Map<String, Object> FindProduct(int p_id) {
		Map<String, Object> map = new HashMap<>();
		Product pro = pMapper.FindProduct(p_id);
		int Now_Discount = 0;
		int Next_Discount_sell = pro.getDiscount().get(0).getDis_quantity();
		int discount_price = pro.getP_price(); // 할인이 적용된 가격을 넣으려고 만든거,
		List<String> overlap_chk = new ArrayList<>();
		for (Option opt : pro.getOption()) {
			overlap_chk.add(opt.getOpt_option1());
		}
		List<String> opt_option1 = overlap_chk.stream().distinct().collect(Collectors.toList()); // 중복제거
<<<<<<< HEAD
		int index = 0;
=======
		int index = 1;
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		for (Discount dis : pro.getDiscount()) {
			if (dis.getDis_quantity() <= pro.getP_sell()) {
				discount_price = pro.getP_price() - ((pro.getP_price() / 100) * (dis.getDis_count()));
				Now_Discount = dis.getDis_count();
				Next_Discount_sell = pro.getDiscount().get(index).getDis_quantity();
<<<<<<< HEAD
				if (index < pro.getDiscount().size() - 1) {
					Next_Discount_sell = pro.getDiscount().get(index + 1).getDis_quantity();
				}

=======
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
			}
			index++;
		}
		LocalDateTime p_recruitdate = LocalDateTime.parse(pro.getP_recruitdate(), formatter);
		LocalDateTime p_duedate = LocalDateTime.parse(pro.getP_duedate(), formatter);
<<<<<<< HEAD
		List<Map<String, Object>> CategoryBest = pMapper.CategoryBestProduct(pro.getP_nickname_m_fk(),
				pro.getP_category());
		List<Map<String, Object>> SellerBest = pMapper.SellerBestProduct(pro.getP_nickname_m_fk());
		map.put("CategoryBest", CategoryBest);
		map.put("SellerBest", SellerBest);
=======
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		map.put("Now_Discount", Now_Discount);
		map.put("Next_Discount_sell", Next_Discount_sell);
		map.put("discount_price", discount_price);
		map.put("opt_option1", opt_option1);
		map.put("p_recruitdate", p_recruitdate);
		map.put("p_duedate", p_duedate);
		map.put("pro", pro);
		return map;
	}

	public List<Option> FindOption2(String opt_option1, int p_id) {
		List<Option> option_list = pMapper.Option_List(p_id);
		List<Option> FindOption = new ArrayList<>();
		for (Option opt : option_list) {
			if (opt.getOpt_option1().equals(opt_option1)) {
				FindOption.add(opt);
			}
		}
		return FindOption;
	}

	public PagingResponse<Product> WriterProductlist(String p_nickname_m_fk, SearchDto params, String keyword) {
		int count = 0;
		Map<String, Object> map = new HashMap<>();
		List<Product> list = new ArrayList<>();
		if (params.getSearching() != null) {
			count = pMapper.SearchSellerCount(p_nickname_m_fk, params.getSearching(), keyword);
			map.put("search", params.getSearching());
		} else {
			count = pMapper.WriterProductlistCount(p_nickname_m_fk, keyword);
		}
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		map.put("p_nickname_m_fk", p_nickname_m_fk);
		map.put("keyword", keyword);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		if (params.getSearching() != null) {
			list = pMapper.SearchSeller(map);
		} else {
			list = pMapper.WriterProductlist(map);
		}
<<<<<<< HEAD
		return new PagingResponse<>(list, pagination);
	}

	public PagingResponse<Order> BuyProduct(String p_nickname_m_fk, SearchDto params) {
		int count = 0;
		Map<String, Object> map = new HashMap<>();

		count = pMapper.BuyProductCount(p_nickname_m_fk);
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		map.put("p_nickname_m_fk", p_nickname_m_fk);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Order> list = pMapper.BuyProduct(map);
=======
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		return new PagingResponse<>(list, pagination);
	}

	@Transactional
<<<<<<< HEAD
	public void RemoveEvent(int p_id) {
=======
	public void removeProduct(int p_id) {
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		String value = "";
		Product FindCalender = pMapper.FindCalender(p_id);
		String p_recruitdate_str = FindCalender.getP_recruitdate();
		String p_duedate_str = FindCalender.getP_recruitdate();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime p_recruitdate = LocalDateTime.parse(p_recruitdate_str, formatter);
		LocalDateTime p_duedate = LocalDateTime.parse(p_duedate_str, formatter);
		if (now.isBefore(p_recruitdate)) {
			value = "DROP EVENT " + p_id + "_start";
			pMapper.CreateNewEvent(value);
			value = "DROP EVENT " + p_id + "_end";
			pMapper.CreateNewEvent(value);
		} else if (now.isBefore(p_duedate)) {
			value = "DROP EVENT " + p_id + "_end";
		}
		pMapper.removeProduct(p_id);

	}

	public Map<String, Object> Option_List(int p_id) {
		List<Option> opt = pMapper.Option_List(p_id);
		List<String> newList = new ArrayList<>();
<<<<<<< HEAD
		Map<String, Object> map = new HashMap<>();
		if (opt.size() != 0 && opt.get(0).getOpt_option2() != null) {
			for (Option option1 : opt) {
				newList.add(option1.getOpt_option1());
			}
			List<String> opt1 = newList.stream().distinct().collect(Collectors.toList());
			map.put("opt1", opt1);
		}
=======
		for (Option option1 : opt) {
			newList.add(option1.getOpt_option1());
		}
		List<String> opt1 = newList.stream().distinct().collect(Collectors.toList());
		Map<String, Object> map = new HashMap<>();
		map.put("opt1", opt1);
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
		map.put("opt", opt);
		return map;
	}

<<<<<<< HEAD
	public Map<String, Object> All_SellPrice(String p_nickname_m_fk) { // 총 판매액 계산
		List<Map<String, Object>> Sell_Map = pMapper.Sell_chart(p_nickname_m_fk);
		int Sell_Money = 0;
		int Total_Sell = 0;
		for (Map<String, Object> map : Sell_Map) {
			Sell_Money += Integer.parseInt(map.get("p_endprice").toString())
					* Integer.parseInt(map.get("p_sell").toString());
			Total_Sell += Integer.parseInt(map.get("p_sell").toString());
		}
		Map<String, Object> create_map = new HashMap<>();
		create_map.put("Sell_Count", Sell_Map.size());
		create_map.put("Total_Sell", Total_Sell);
		create_map.put("Sell_Money", Sell_Money);
		return create_map;
	}

	@Transactional
	public void OptionRemove(String opt_option1, int opt_pid_p_fk) {
		Option opt = Option.builder().opt_option1(opt_option1).opt_pid_p_fk(opt_pid_p_fk).build();
		pMapper.OptionRemove(opt);
	}

	@Transactional
	public void OneOptionRemove(int opt_id) {
		pMapper.OneOptionRemove(opt_id);
	}

	@Transactional
	public void OptionRemoveProduct(int p_id) {
		pMapper.OptionRemoveProduct(p_id);
	}

	public Option option_chk(int opt_pid_p_fk) {
		List<Option> pid_All_OptionList = pMapper.Option_List(opt_pid_p_fk);
		if (pid_All_OptionList.size() > 0) {
			return pid_All_OptionList.get(0);
		}
		return null;
=======
	public Map<String, Object> All_SellPrice(String p_nickname_m_fk) {
		Map<String, Object> sel_count = pMapper.All_SellCount(p_nickname_m_fk);
		List<Integer> sel_Price = pMapper.All_SellPrice(p_nickname_m_fk);
		List<Integer> sel_AllSell = pMapper.All_Sell(p_nickname_m_fk);
		int sel_point = 0;
		for (int i = 0; i < sel_Price.size(); i++) {
			sel_point += sel_Price.get(i) * sel_AllSell.get(i);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("sel_count", sel_count);
		map.put("sel_point", sel_point);
		return map;
	}

	@Transactional
	public void OptionRemove(String opt_name) {
		pMapper.OptionRemove(opt_name);
>>>>>>> 111d5471ee54827a52188822efb9bbb4652cdb48
	}

}