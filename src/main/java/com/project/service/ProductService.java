 package com.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.mapper.ProductMapper;
import com.project.model.Pagination;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	
	@Autowired
	private final ProductMapper pMapper;
	
	public ArrayList<Product> Productbest(){
		return pMapper.Productbest();
	}
	
	public ArrayList<Product> Productnew(){
		return pMapper.Productnew();
	}
	
	 public PagingResponse<Product> Category(SearchDto params, String p_category) {

        int count = pMapper.category_count(p_category);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);
        Map<String, Object> map = new HashMap<>();
		map.put("p_category", p_category);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = pMapper.Category(map);

        return new PagingResponse<>(list, pagination);
     }
	 
	 public PagingResponse<Product> PagingList(SearchDto params) {
		 int count = pMapper.count();
		 if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
	     }
		 Pagination pagination = new Pagination(count, params);
		 params.setPagination(pagination);
		 List<Product> list = pMapper.PagingList(params);
		 return new PagingResponse<>(list, pagination);
		 
	 }
	 
//	 public PagingResponse<Product> Line_Up(SearchDto params,String key1,String key2  ){
//		 int count = pMapper.count();
//		 if (count < 1) {
//            return new PagingResponse<>(Collections.emptyList(), null);
//	     }
//		 Pagination pagination = new Pagination(count, params);
//		 params.setPagination(pagination);
//		 List<Product> list = pMapper.Pro_lineUp(params, key1, key2);
//		 return new PagingResponse<>(list, pagination);
//	 }
	
	
	
}
