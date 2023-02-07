package com.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		qna.setQ_id_p_fk(1234);
		qna.setQ_nickname_m_fk("exNickname");
		qnaService.AddQuestion(qna);
		return "redirect:/QnABoardModel";
	}
	
	@GetMapping("/{q_id}")
	public String readQuestion(@PathVariable int q_id, QnABoardModel qna, Model model) throws Exception{
		Map<String, Object> questionMap = qnaService.FindQuestion(q_id);
		model.addAttribute("questionMap", questionMap);
		return "qnaBoard";
	}
	
	@GetMapping("/answer/{q_id}")
	public String goAnswer(@PathVariable int q_id, QnABoardModel qna, Model model) throws Exception{
		Map<String, Object> questionMap = qnaService.FindQuestion(q_id);
		model.addAttribute("questionMap", questionMap);
		System.out.println();
		return "questionMap";
	}

//	@GetMapping("/qnaList/{p_id}")
//	public String readQnaList(@PathVariable int p_id) throws Exception{
//		
//	}	
	
	@PostMapping("/answer/{q_id}")
	public String addAnswer(@PathVariable int q_id, QnABoardModel qna, Model model) throws Exception{
		qnaService.AddAnswer(qna);
		return "redirect:/qnaboard/"+q_id;
	}
	
	@GetMapping("/list/{p_id}")
	public void qnaList(QnABoardModel qna, Model model) {
		model.addAttribute("list", qnaService.getList());
	}
}
