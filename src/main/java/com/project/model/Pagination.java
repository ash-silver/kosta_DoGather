package com.project.model;

import com.project.model.SearchDto;

import lombok.Getter;

@Getter
public class Pagination {

    private int totalRecordCount;   // 전체 데이터 수
    private int totalPageCount;     // 전체 페이지 수
    private int startPage;          // 첫 페이지 번호
    private int endPage;            // 끝 페이지 번호
    private int limitStart;         // LIMIT 시작 위치
    private boolean existPrevPage;  // 이전 페이지 존재 여부
    private boolean existNextPage;  // 다음 페이지 존재 여부
	private int displayPageNum=10; //1 2 3 4 5 6 7 8 9 10

    public Pagination(int totalRecordCount, SearchDto params) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            this.calculation(params);
        }
    }

    private void calculation(SearchDto params) {

        // 전체 페이지 수 계산
        totalPageCount =(int)(Math.ceil(totalRecordCount/(double)params.getRecordSize()));

        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
        if (params.getPage() > totalPageCount) {
            params.setPage(totalPageCount);  // 현재페이지 위치가 최대 페이지를 넘기지 않게하기위한 설정
        }

        //화면에 보여질 마지막 페이지 번호
  		endPage=(int)(Math.ceil(params.getPage()/(double)displayPageNum)*displayPageNum);
  		
  		
  		//화면에 보여질 시작 페이지 번호0
  		startPage=(endPage-displayPageNum)+1;
  		if(startPage<=0) startPage=1;
        if (endPage > totalPageCount) {
            endPage = totalPageCount;  //하단의 출력되는 페이지의 수가 실제로 존재하는 페이지의 수보다 많지 않게하기 위함
        }

        // LIMIT 시작 위치 계산
        limitStart = (params.getPage() - 1) * params.getRecordSize();
        
        // 이전 페이지 존재 여부 확인
        existPrevPage=(startPage==1) ? false :  true;

        // 다음 페이지 존재 여부 확인
        existNextPage=(endPage<totalPageCount) ? true : false;       
    }

}