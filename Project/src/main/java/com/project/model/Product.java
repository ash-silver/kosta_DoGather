package com.project.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	private String p_name,p_image,p_price,p_conimage,p_category,p_writer,p_due_date,p_recruit_date;
	private Timestamp p_create_date;
	private int p_id,p_min_quantity,p_max_quantity;
	
	

}
