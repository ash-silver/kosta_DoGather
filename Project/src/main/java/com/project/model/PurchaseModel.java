package com.project.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseModel {
	private int o_id;
	private String o_product_p_fk;
	private String o_member_m_fk;
	private String o_date;
	private String o_quantity;
	private String o_state;
}
