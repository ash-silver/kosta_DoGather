package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.model.QnABoardModel;
import com.project.service.QnABoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qnaboard")
public class QnABoardController {
	
	@Autowired
	private QnABoardService qnaService;
	
	@GetMapping("")
	public String form() {
		return "qnaboard";
	}
	
	
	@PostMapping("") 
	public String addQuestion(QnABoardModel qna) throws Exception{
		qna.setQ_name_p_fk("aaa");
		qna.setQ_nickname_m_fk("bbb");
		qnaService.AddQuestion(qna);
		return "redirect:/QnABoardModel";
	}
}
