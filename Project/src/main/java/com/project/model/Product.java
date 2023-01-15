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
public class Product {
	private String p_name,p_category,p_writer,p_due_date,p_recruit_date,p_chk,p_discount,p_disquantity;
	private Timestamp p_create_date;
	private int p_id,p_min_quantity,p_max_quantity,p_price,p_sell;
	private MultipartFile[] p_img,p_contentimg;
	private ArrayList<Img> img;
	private ArrayList<Option> option;
	

}
