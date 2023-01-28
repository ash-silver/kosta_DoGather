package com.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.mapper.IndexMapper;
import com.project.model.Pagination;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexService {
	private final IndexMapper iMapper;

	public ArrayList<Product> Productbest() {
		return iMapper.Productbest();
	}

	public ArrayList<Product> Productnew() {
		return iMapper.Productnew();
	}

	public PagingResponse<Product> Category(SearchDto params, String p_category) {

		int count = iMapper.category_count(p_category);
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}

		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("p_category", p_category);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = iMapper.Category(map);

		return new PagingResponse<>(list, pagination);
	}

	public PagingResponse<Product> PagingList(SearchDto params) {
		int count = iMapper.count();
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		List<Product> list = iMapper.PagingList(params);
		return new PagingResponse<>(list, pagination);

	}

	public PagingResponse<Product> newlist(SearchDto params, String p_category) {
		int count;
		if (p_category == null) {
			count = iMapper.count();
		} else {
			count = iMapper.category_count(p_category);
		}
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("p_category", p_category);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = null;
		if (p_category == null) {
			list = iMapper.newlist(params);
		} else {
			list = iMapper.CategoryNew(map);
		}
		return new PagingResponse<>(list, pagination);

	}

	public PagingResponse<Product> pricelist(SearchDto params, String p_category) {
		int count;
		if (p_category != null) {
			count = iMapper.count();
		} else {
			count = iMapper.category_count(p_category);
		}

		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("p_category", p_category);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = null;
		if (p_category == null) {
			list = iMapper.pricelist(params);
		} else {
			list = iMapper.CategoryPrice(map);
		}
		return new PagingResponse<>(list, pagination);

	}

	public PagingResponse<Product> pricelistdesc(SearchDto params, String p_category) {
		int count;
		if (p_category != null) {
			count = iMapper.count();
		} else {
			count = iMapper.category_count(p_category);
		}

		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("p_category", p_category);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = null;
		if (p_category == null) {
			list = iMapper.pricelistdesc(params);
		} else {
			list = iMapper.CategoryPriceDesc(map);
		}
		return new PagingResponse<>(list, pagination);

	}

	public PagingResponse<Product> bestlist(SearchDto params, String p_category) {
		int count;
		if (p_category != null) {
			count = iMapper.count();
		} else {
			count = iMapper.category_count(p_category);
		}

		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("p_category", p_category);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = null;
		if (p_category == null) {
			list = iMapper.bestlist(params);
		} else {
			list = iMapper.CategoryBest(map);
		}
		return new PagingResponse<>(list, pagination);

	}

	public PagingResponse<Product> Search(SearchDto params, String keyword, String search) {
		int count;
		if (keyword.equals("total")) {
			count = iMapper.SearchTotCount(search);
		} else {
			count = iMapper.SearchCount(keyword, search);
		}
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("keyword", keyword);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		List<Product> list = null;
		if (keyword.equals("total")) {
			list = iMapper.SearchTotal(map);
		} else {
			list = iMapper.Search(map);
		}
		return new PagingResponse<>(list, pagination);
	}
}
