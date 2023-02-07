package com.project.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	private String r_nickname_m_fk, r_content,r_datetxt;
	private Timestamp r_date;
	private double r_rating;
	private int r_id,r_pid_p_fk;

}
