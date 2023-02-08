package com.project.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
	private String m_name,m_password,m_nickname,m_type,m_Tel,m_address,m_email;
	
	
	
	private Timestamp m_create_date;
	private int m_id,m_temp,m_cash;
	

}
