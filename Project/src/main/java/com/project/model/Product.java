package com.project.model;

import java.sql.Timestamp;
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
	private String p_name,p_price,p_category,p_writer,p_due_date,p_recruit_date;
	private Timestamp p_create_date;
	private int p_id,p_min_quantity,p_max_quantity;
	private MultipartFile[] p_img,p_contentimg;

	

}
