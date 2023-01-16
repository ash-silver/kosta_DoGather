package com.project.service;

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
	
}
