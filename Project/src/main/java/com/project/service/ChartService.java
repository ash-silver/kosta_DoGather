package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mapper.ChartMapper;
import com.project.model.Chart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {

	private final ChartMapper cMapper;

	public List<Chart> OneWeekChart(String p_nickname_m_fk) {
		return cMapper.OneWeekChart(p_nickname_m_fk);
	}

	public List<Chart> OneWeekSellPrice(String p_nickname_m_fk) {
		return cMapper.OneWeekSellPrice(p_nickname_m_fk);
	}
}