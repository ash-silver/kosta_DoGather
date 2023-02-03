package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.model.Chart;

@Mapper
public interface ChartMapper {
	
	List<Chart> OneWeekChart(String p_nickname_m_fk);
	List<Chart> OneWeekSellPrice(String p_nickname_m_fk);
	
}