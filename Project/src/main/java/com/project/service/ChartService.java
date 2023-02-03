package com.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.mapper.ChartMapper;
import com.project.model.Chart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {

	private final ChartMapper cMapper;

	public Map<String, Object> AllChartList(String p_nickname_m_fk, String Day) {
		Map<String, Object> ChartMap = new HashMap<>();
		List<Chart> Week = cMapper.OneWeekChart(p_nickname_m_fk, Day);
		List<Chart> WeekSell = cMapper.OneWeekSellPrice(p_nickname_m_fk, Day);
		List<Chart> MonthCategory = cMapper.OneMonthCategorySell(p_nickname_m_fk, Day);
		List<Chart> MonthFailPro = cMapper.OneMonthFailedProduct(p_nickname_m_fk, Day);
		ChartMap.put("Week", Week);
		ChartMap.put("WeekSell", WeekSell);
		ChartMap.put("MonthCategory", MonthCategory);
		ChartMap.put("MonthFailPro", MonthFailPro);
		return ChartMap;
	}

	// product service mypage 필요
	public List<Chart> OneWeekChart(String p_nickname_m_fk, String Day) {
		System.out.println("sddasda" + p_nickname_m_fk);
		System.out.println("sddasda" + Day);
		return cMapper.OneWeekChart(p_nickname_m_fk,Day);
	}

}
