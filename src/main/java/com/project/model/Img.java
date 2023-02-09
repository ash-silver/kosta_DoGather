package com.project.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Img {
	private String img_origname;//업로도파일의 원본 이름
	private String img_keyword;//대표이미지,상세이미지 구분
	private String img_name;//실제 업로드된 파일의 이름
	private int img_pid_p_fk;//이미지파일의 공구글 번호
	private int img_id;//고유 번호
	private int img_rid_r_fk;//리뷰 이미지 번호
	private String img_chk;//글의 삭제여부 add,remove로 구분 예정

}
