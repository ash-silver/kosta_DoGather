package com.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.mapper.ChartMapper;
import com.project.model.Chart;
import com.project.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {

	private final ChartMapper cMapper;

	public Map<String, Object> AllChartList(String p_nickname_m_fk, String Day, String Month) {
		Map<String, Object> ChartMap = new HashMap<>();
		List<Chart> Week = cMapper.OneWeekChart(p_nickname_m_fk, Day);
		List<Map<String, Object>> NowAllSell = cMapper.NowAllSell(p_nickname_m_fk);
		List<Chart> WeekSell = cMapper.OneWeekSellPrice(p_nickname_m_fk, Day);
		List<Chart> MonthCategory = cMapper.OneMonthCategorySell(p_nickname_m_fk, Month);
		List<Chart> MonthFailPro = cMapper.OneMonthFailedProduct(p_nickname_m_fk, Month);
		List<Map<String, Object>> NowAllProduct = cMapper.NowAllProduct(p_nickname_m_fk);
		List<Map<String, Object>> NowEndSellMoney = cMapper.NowEndSellMoney(p_nickname_m_fk);
		ChartMap.put("Week", Week);
		ChartMap.put("NowAllSell", NowAllSell);
		ChartMap.put("NowEndSellMoney", NowEndSellMoney);
		ChartMap.put("NowAllProduct", NowAllProduct);
		ChartMap.put("Day", Day);
		ChartMap.put("Month", Month);
		ChartMap.put("WeekSell", WeekSell);
		ChartMap.put("MonthCategory", MonthCategory);
		ChartMap.put("MonthFailPro", MonthFailPro);
		return ChartMap;
	}

	// product service mypage 필요
	public List<Chart> OneWeekChart(String p_nickname_m_fk, String Day) {

		return cMapper.OneWeekChart(p_nickname_m_fk, Day);
	}

}
