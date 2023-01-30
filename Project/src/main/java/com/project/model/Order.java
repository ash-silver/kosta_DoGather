package com.project.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	private int o_id;
	private int o_quantity;
	private int o_product_p_fk;
	private int o_member_m_fk;
	private String o_recipient;
	private String o_state;
	private String o_option1;
	private String o_option2;
	private Timestamp o_date;
	private Product product;
	private Img img;
}