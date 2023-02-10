package com.project.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	private int o_id;
	private int o_product_p_fk;
	private int o_member_m_fk;
	private String o_phoneNumber;
	private String o_address;
	private String o_recipient;
	private Timestamp o_date;
	private int o_quantity;
	private String o_state;
	private String o_dstate;
	private String o_option1;
	private String o_option2;
	private String o_postCode;
	private long o_postCompanyKey;
	private String o_reason;
	private Product product;
	private Img img;
	
	private int p_endprice;
	private String p_name;
	private String m_nickname;
	private String o_date_md;
}