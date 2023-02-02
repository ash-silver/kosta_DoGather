package com.project.model;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Product {
	private String p_name,p_category,p_nickname_m_fk,p_duedate,p_recruitdate,p_chk;
	private Timestamp p_createdate;
	private int p_id,p_price,p_sell;
	
	private ArrayList<Img> img;
	

}
