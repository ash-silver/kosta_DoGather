package com.project.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.mapper.ProductMapper;
import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Autowired
	private final ProductMapper pMapper;
	@Value("${file.Upimg}")
	private String path;

	public void AddProduct(Product pro,String[] p_discount_quan,String[] p_discount_count) {
		String p_discount="";
		String p_disquantity="";
		for(int i=0;i<p_discount_count.length;i++) {
			p_discount+=p_discount_count[i]+"/";
			p_disquantity+=p_discount_quan[i]+"/";
		}
		pro.setP_disquantity(p_disquantity);
		pro.setP_discount(p_discount);
		pMapper.AddProduct(pro);
	}

	public void AddOption( Option opt) {
		pMapper.AddOption(opt);
	}

	public void AddImg(Product pro) throws IllegalStateException, IOException {
			
		for (MultipartFile imgfile : pro.getP_img()) {
			if (!imgfile.isEmpty()) {
				String origName = imgfile.getOriginalFilename(); // 입력한 원본 파일의 이름
				String uuid = UUID.randomUUID().toString(); // 문자+숫자의 랜덤한 파일명으로 변경
				String extension = origName.substring(origName.lastIndexOf(".")); // 원본파일의 파일확장자
				String savedName = uuid + extension; // 랜덤이름 + 확장자
				File converFile = new File(path, savedName); // path = 상품 이미지 파일의 저장 경로가 들어있는 프로퍼티 설정값
				imgfile.transferTo(converFile); // --- 실제로 저장을 시켜주는 부분 , 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생
				Img img = Img.builder().img_keyword("p_img").img_name(savedName).img_origname(origName).img_pid(pro.getP_id())
						.build();
				pMapper.AddImg(img);
			}
		}
		for (MultipartFile conimgfile : pro.getP_contentimg()) {
			if (!conimgfile.isEmpty()) {
				String origName = conimgfile.getOriginalFilename(); // 입력한 원본 파일의 이름
				String uuid = UUID.randomUUID().toString(); // 문자+숫자의 랜덤한 파일명으로 변경
				String extension = origName.substring(origName.lastIndexOf(".")); // 원본파일의 파일확장자
				String savedName = uuid + extension; // 랜덤이름 + 확장자
				File converFile = new File(path, savedName); // path = 상품 이미지 파일의 저장 경로가 들어있는 프로퍼티 설정값
				conimgfile.transferTo(converFile); // --- 실제로 저장을 시켜주는 부분 , 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생
				Img img = Img.builder().img_keyword("p_contentimg").img_name(savedName).img_origname(origName)
						.img_pid(pro.getP_id()).build();
				pMapper.AddImg(img);
			}
		}

	}
	public Product FindProduct(int p_id) {
		return pMapper.FindProduct(p_id);
	}
	
	public List<Option> FindCategory(String opt_option1,int p_id){
		Map<String, Object> map = new HashMap<>();	
		map.put("opt_option1", opt_option1);
		map.put("opt_pid", p_id);
		return pMapper.FindCategory(map);
	}


}
