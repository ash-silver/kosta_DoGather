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
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.mapper.ProductMapper;
import com.project.model.Discount;
import com.project.model.Img;
import com.project.model.Option;
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
		List<Discount> dis_length = pMapper.Update_find(pro.getP_id());
		for (int i = 0; i < pro.getP_discount_count().size(); i++) {
			Discount dis = Discount.builder().dis_count(pro.getP_discount_count().get(i))
					.dis_quantity(pro.getP_discount_quan().get(i)).dis_pid_p_fk(pro.getP_id()).build();
			if (dis_length.size() < pro.getP_discount_count().size()) {
				if (i <= dis_length.size() - 1) {
					dis.setDis_id(dis_length.get(i).getDis_id());
					pMapper.UpdateDiscount(dis);
				} else {
					pMapper.AddDiscount(dis);
				}
			}
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

	public void delimg(Img i) { 
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
		int index = 0;
		for (Discount dis : pro.getDiscount()) {
			if (dis.getDis_quantity() <= pro.getP_sell()) {
				discount_price = pro.getP_price() - ((pro.getP_price() / 100) * (dis.getDis_count()));
				Now_Discount = dis.getDis_count();
				Next_Discount_sell = pro.getDiscount().get(index).getDis_quantity();
				if (index < pro.getDiscount().size() - 1) {
					Next_Discount_sell = pro.getDiscount().get(index + 1).getDis_quantity();
				}

			}
			index++;
		}
		LocalDateTime p_recruitdate = LocalDateTime.parse(pro.getP_recruitdate(), formatter);
		LocalDateTime p_duedate = LocalDateTime.parse(pro.getP_duedate(), formatter);
		map.put("Now_Discount", Now_Discount);
		map.put("Next_Discount_sell", Next_Discount_sell);
		map.put("discount_price", discount_price);
		map.put("opt_option1", opt_option1);
		map.put("p_recruitdate", p_recruitdate);
		map.put("p_duedate", p_duedate);
		map.put("pro", pro);
		return map;
	}

	public List<String> FindOption2(String opt_option1, int p_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("opt_option1", opt_option1);
		map.put("opt_pid_p_fk", p_id);
		return pMapper.FindOption2(map);
	}

	public PagingResponse<Product> WriterProductlist(String p_nickname_m_fk, SearchDto params, String keyword,
			String search) {
		int count = 0;
		Map<String, Object> map = new HashMap<>();
		List<Product> list = new ArrayList<>();
		if (search != null) {
			count = pMapper.SearchSellerCount(p_nickname_m_fk, search, keyword);
			map.put("search", search);
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
		if (search != null) {
			list = pMapper.SearchSeller(map);
		} else {
			list = pMapper.WriterProductlist(map);
		}
		return new PagingResponse<>(list, pagination);
	}

	@Transactional
	public void RemoveEvent(int p_id) {
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
		Map<String, Object> map = new HashMap<>();
		List<String> newList = new ArrayList<>();
		if (opt.size() != 0 && opt.get(0).getOpt_option2() != null) {
			for (Option option1 : opt) {
				newList.add(option1.getOpt_option1());
			}
			List<String> opt1 = newList.stream().distinct().collect(Collectors.toList());
			map.put("opt1", opt1);
		}
		map.put("opt", opt);
		return map;
	}

	public Map<String, Object> All_SellPrice(String p_nickname_m_fk) { // 총 판매액 계싼
		Map<String, Object> sell_count = pMapper.All_SellCount(p_nickname_m_fk);
		List<Integer> sell_Price = pMapper.All_SellPrice(p_nickname_m_fk);
		List<Integer> sell_AllSell = pMapper.All_Sell(p_nickname_m_fk);
		int sell_point = 0;
		for (int i = 0; i < sell_Price.size(); i++) {
			sell_point += sell_Price.get(i) * sell_AllSell.get(i);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("sell_count", sell_count);
		map.put("sell_point", sell_point);
		return map;
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

	public Option FindOption(int opt_pid_p_fk) {
		return pMapper.FindOption(opt_pid_p_fk);
	}
}
