package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Option {
	private int opt_id;
	private int opt_pid_p_fk;
	private String opt_option1;
	private String opt_option2;
	private String opt_quantity;

}
