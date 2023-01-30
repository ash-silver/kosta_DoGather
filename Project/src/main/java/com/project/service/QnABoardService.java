package com.project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mapper.QnABoardMapper;
import com.project.model.QnABoardModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnABoardService {
	@Autowired
	private QnABoardMapper qnaMapper;
	
	@Transactional
	public void AddQuestion(QnABoardModel qna) {
		qnaMapper.AddQuestion(qna);
	}
	
	public Map<String, Object> FindQuestion(int q_id) {
		Map<String, Object> map = new HashMap<>();
		QnABoardModel qnaModel = qnaMapper.FindQuestion(q_id);
		
		System.out.println(qnaModel);
		
		map.put("q_id", q_id);
		map.put("q_name_p_fk", qnaModel.getQ_name_p_fk());
		map.put("q_title", qnaModel.getQ_title());
		map.put("q_content", qnaModel.getQ_content());
		map.put("q_nickname_m_fk", qnaModel.getQ_nickname_m_fk());
		map.put("q_date", qnaModel.getQ_date());
		
		if (qnaModel.getQ_answer() != null) {
			map.put("q_answer", qnaModel.getQ_answer());
		}
		
		return map;
		
	}
	
	
}
