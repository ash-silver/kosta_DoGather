$(function(){
	

    /*<![CDATA[*/

    window.onload = () => {
      findAllPost();
    }
    // 게시글 리스트 조회
    function findAllPost() {

      const list =/*[[${prolist.list}]]*/"";// PagingResponse의 멤버인 List<T> 타입의 list를 의미
      if ( !list.length ) { //리스트가 비어있는 경우, "검색 결과가 없다"는 메시지를 행에 출력
    	  $(".mypage_list_cont").html('<td colspan="6"><div className="no_data_msg">검색된 결과가 없습니다.</div></td>');
        drawPage(); //페이지네이션 HTML을 제거(초기화)한 후 로직을 종료합니다.
      }

      const pagination = /*[[${prolist.pagination}]]*/"";//PagingResponse의 멤버인 pagination을 의미
     const img=/*[[${img}]]*/"";
      const params =/*[[${params}]]*/""; // @ModelAttribute로 바로 1ㄷ1 맵핑시킨 값을 받아옴
     																				// ex) 100 - ((1-1)*10) = 100
      drawList(list, img);
      drawPage(pagination, params);
    }


    // 리스트 HTML draw
    // 제품 리스트를 출력
    function drawList(list, img) {
	let index=0;
      let html = '';
      list.forEach(row => { // list 의 갯수만큼 반복진행 row 라는 변수명으로 list명을 대체함 화살표함수
        html += `
        
        	<div class="mypage_list_tag">
				<div class="mypage_list_tag_img">
					<a href="/products/${row.p_id}/detail">
						<img src="/projectimg/${img[index].img_name}">
					</a>
				</div>
				<div class="myapge_list_tag_text">
					<div class="myapge_text">
						<span>제품명:${row.p_name}</span>
						<span>현재 판매된 수량:${row.p_sell}개</span>
						<span>총 판매액:${row.p_price*row.p_sell}원</span>
					</div>
				
					<div class="mypage_info">
					<a href="/products/${row.p_id}/info">
						세부정보
					</a>
					</div>
				
				</div>
			</div>
	       
        `;        
        index++;
      })	
      html+='<div class="paging_btn"></div>'
      $(".mypage_list_cont").html(html); // #list에 해당 html을 대체해서 넣어준다.
    }


    // 페이지 HTML draw  하단의 버튼에 해당하는 스크립트로 페이징의 핵심
    function drawPage(pagination, params) {
		//SearchDto의 기본 Default값을 바탕으로 mybatis의 count를 같이 받아와 계산후 저장시켜둔 pagingnation과 SearchDto를 받아옴
      if ( !pagination || !params ) {
    	  // pagination, params가 존재하지 않는다면 해당 스크립트를 출력
    	  $(".paging_btn").html("");        
        throw new Error('Missing required parameters...');
      }

      let html = '';
      
      // 첫 페이지, 이전 페이지
      
      if (pagination.existPrevPage) { // 현재페이지에서 보이는 스타트페이지가 1이 아닐경우 해당 스크립트의 HTML이 만들어진다. ( <  이전 페이지 ) 
        html += `
            <a href="javascript:void(0);" onclick="movePage(1)" class="page_bt first">첫 페이지</a>
            <a href="javascript:void(0);" onclick="movePage(${pagination.startPage - 1})" class="page_bt prev">이전 페이지</a>
        `;
      }

      // 페이지 번호
      html += '<p>';
      		// 1,2,3,4,5~~~ 페이지의 번호를 만들어내는 구문
      for (let i = pagination.startPage; i <= pagination.endPage; i++) {
        html += (i !== params.page)
                ? `<a href="javascript:void(0);" onclick="movePage(${i});">${i}</a>`
                : `<span class="on">${i}</span>`
      }
      html += '</p>';

      // 다음 페이지, 마지막 페이지
      if (pagination.existNextPage) {
        html += `
            <a href="javascript:void(0);" onclick="movePage(${pagination.endPage + 1});" class="page_bt next">다음 페이지</a>
            <a href="javascript:void(0);" onclick="movePage(${pagination.totalPageCount});" class="page_bt last">마지막 페이지</a>
        `;
      }
      $(".paging_btn").html(html);  
    }
    // 페이지 이동
    function movePage(page) {
      const queryParams = {
        page: (page) ? page : 1,
        recordSize: 10,
        pageSize: 10
      }
      location.href = location.pathname + '?' + new URLSearchParams(queryParams).toString();
    }
    	
	
    /*]]>*/
    });