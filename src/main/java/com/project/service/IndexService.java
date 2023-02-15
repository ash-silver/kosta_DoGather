package com.project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.mapper.IndexMapper;
import com.project.mapper.ReviewMapper;
import com.project.model.Img;
import com.project.model.Order;
import com.project.model.Pagination;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.Review;
import com.project.model.SearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IndexService {

	@Autowired
	private final IndexMapper pMapper;

	@Autowired
	private final ReviewMapper rMapper;

	@Value("${file.Upimg}")
	private String path;

	public List<Product> Productbest() {
		return pMapper.Productbest();
	}

	public List<Product> Productnew() {
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
		map.put("limitStart", pagination.getLimitStart());
		map.put("recordSize", params.getRecordSize());
		List<Product> list = pMapper.Category(map);
		System.out.println(list);
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
	 
	 public PagingResponse<Product> newlist(SearchDto params, String p_category) {
		 int count;
		 if(p_category == null) {
			count = pMapper.count();
		 }else {
			count = pMapper.category_count(p_category); 
		 }
		 if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
	     }
		 System.out.println(p_category);
		 Pagination pagination = new Pagination(count, params);
		 params.setPagination(pagination);
		 Map<String, Object> map = new HashMap<>();
		 map.put("p_category", p_category);
		 map.put("limitStart", params.getPagination().getLimitStart());
		 map.put("recordSize", params.getRecordSize());
		 List<Product> list = new ArrayList<>();
		 if(p_category == null) {
			 list = pMapper.newlist(params);
		 }else {
			 list = pMapper.CategoryNew(map);
		 }
		 return new PagingResponse<>(list, pagination);
		 
	 }
	 public PagingResponse<Product> pricelist(SearchDto params, String p_category) {
		 int count;
		 if(p_category == null) {
			count = pMapper.count();
		 }else {
			count = pMapper.category_count(p_category); 
		 }

		 if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
	     }
		 Pagination pagination = new Pagination(count, params);
		 params.setPagination(pagination);
		 Map<String, Object> map = new HashMap<>();
		 map.put("p_category", p_category);
		 map.put("limitStart", params.getPagination().getLimitStart());
		 map.put("recordSize", params.getRecordSize());
		 List<Product> list = new ArrayList<>();
		 if(p_category == null) {
			 list = pMapper.pricelist(params);
		 }else {
			 list = pMapper.CategoryPrice(map);
		 }
		 return new PagingResponse<>(list, pagination);
		 
	 }
	 public PagingResponse<Product> pricelistdesc(SearchDto params, String p_category) {
		 int count;
		 if(p_category == null) {
			count = pMapper.count();
		 }else {
			count = pMapper.category_count(p_category); 
		 }

		 if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
	     }
		 Pagination pagination = new Pagination(count, params);
		 params.setPagination(pagination);
		 Map<String, Object> map = new HashMap<>();
		 map.put("p_category", p_category);
		 map.put("limitStart", params.getPagination().getLimitStart());
		 map.put("recordSize", params.getRecordSize());
		 List<Product> list = new ArrayList<>();
		 if(p_category == null) {
			 list = pMapper.pricelistdesc(params);
		 }else {
			 list = pMapper.CategoryPriceDesc(map);
		 }
		 return new PagingResponse<>(list, pagination);
		 
	 }
	 public PagingResponse<Product> bestlist(SearchDto params, String p_category) {
		 int count;
		 if(p_category == null) {
			count = pMapper.count();
		 }else {
			count = pMapper.category_count(p_category); 
		 }

		 if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
	     }
		 Pagination pagination = new Pagination(count, params);
		 params.setPagination(pagination);
		 Map<String, Object> map = new HashMap<>();
		 map.put("p_category", p_category);
		 map.put("limitStart", params.getPagination().getLimitStart());
		 map.put("recordSize", params.getRecordSize());
		 List<Product> list = new ArrayList<>();
		 if(p_category == null) {
			 list = pMapper.bestlist(params);
		 }else {
			 list = pMapper.CategoryBest(map);
		 }
		 return new PagingResponse<>(list, pagination);
		 
	 }

	public PagingResponse<Product> Search(SearchDto params, String keyword, String search) {
		int count;
		if (keyword.equals("total")) {
			count = pMapper.SearchTotCount(search);
		} else {
			count = pMapper.SearchCount(keyword, search);
		}
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("keyword", keyword);
		map.put("limitStart", params.getPagination().getLimitStart());
		map.put("recordSize", params.getRecordSize());
		List<Product> list = null;
		if (keyword.equals("total")) {
			list = pMapper.SearchTotal(map);
		} else {
			list = pMapper.Search(map);
		}
		return new PagingResponse<>(list, pagination);
	}

}
