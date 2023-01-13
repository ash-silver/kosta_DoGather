package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.service.QnABoardService;

@Controller
@RequestMapping("/qnaboard")
public class QnABoardController {
	
	@Autowired
	private QnABoardService service;
	
	@GetMapping("")
	public String form() {
		return "qnaboard";
	}
	
}
