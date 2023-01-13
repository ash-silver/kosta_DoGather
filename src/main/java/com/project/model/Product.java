package com.project.model;

import lombok.Data;

@Data
public class Product {
		private String p_id;
		private String p_name ;
		private String p_price ; 
		private int p_min_quantity ; 
		private String p_max_quantity ;
		private int p_category ;
		private String p_due_date;
		private int p_recruit_date;
		private String p_create_date;
		private String p_writer;
		private String img_name;
	

}
