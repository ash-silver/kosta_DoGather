package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.model.Chart;

@Mapper
public interface ChartMapper {

	List<Chart> OneWeekChart(@Param("p_nickname_m_fk")String p_nickname_m_fk, String Day);

	List<Chart> OneWeekSellPrice(@Param("p_nickname_m_fk")String p_nickname_m_fk, String Day);

	List<Chart> OneMonthCategorySell(@Param("p_nickname_m_fk")String p_nickname_m_fk, String Day);

	List<Chart> OneMonthFailedProduct(@Param("p_nickname_m_fk")String p_nickname_m_fk, String Day);

}
