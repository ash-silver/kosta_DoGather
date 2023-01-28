//페이지가 시작되면 자동 실행
window.onload = () => {
	findAllPost();
}


function findAllPost() {
	const list = /*[[ ${plist.list} ]]*/"";
	if (!list.length) { // PagingResponse의 멤버인 List<T> 타입의 list를 의미
		document.getElementById('list').innerHTML = '<td colspan="6"><div className="no_data_msg">검색된 결과가 없습니다.</div></td>';
		drawPage();//페이지네이션 HTML을 제거(초기화)한 후 로직을 종료합니다.
	}

	const pagination = /*[[${plist.pagination}]]*/"";//PagingResponse의 멤버인 pagination을 의미
	const params =/*[[${params}]]*/""; // @ModelAttribute로 바로 1대1 맵핑시킨 값을 받아옴
	let num = pagination.totalRecordCount - ((params.page - 1) * params.recordSize); // 전체 데이터의 갯수 - ((현재페이지-1)*한페이지의 행의갯수)      
	// ex) 100 - ((1-1)*10) = 100
	drawList(list, num);
	drawPage(pagination, params);
}

function drawList(list, num) {

	let html = '';

	list.forEach(posting => {//list의 갯수 만큼 반복  변수명을 posting으로 대체

		html += `
			<div class="list_box">
				<a href="/prodetail?p_id=${posting.p_id}" class="list_box_url"> <img src="/stsimg/${posting.img.img_name}">
				</a>
				<label class="list_box_name">
					<a href="/prodetail?p_id=${posting.p_id}" >제품명:${posting.p_name}</a>
				</label>
				<label class="list_box_price">
					<span>가격:${posting.p_price}원</span>
				</label>
			</div>               
        `;
	})
	document.getElementById('list').innerHTML = html; // #list에 해당 html을 대체해서 넣어준다.
}

// 페이지 HTML draw  하단의 버튼에 해당하는 스크립트로 페이징의 핵심
function drawPage(pagination, params) {
	//SearchDto의 기본 Default값을 바탕으로 mybatis의 count를 같이 받아와 계산후 저장시켜둔 pagingnation과 SearchDto를 받아옴
	if (!pagination || !params) {
		// pagination, params가 존재하지 않는다면 해당 스크립트를 출력
		document.querySelector('.paging').innerHTML = '';
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
		html += (i !== params.page)// 현재 페이지 번호( params.page )와 그려야 할 페이지 번호( i )가 같으면,해당 페이지 번호를 활성화(on)
			? `<a href="javascript:void(0);" onclick="movePage(${i});">${i}</a>`
			: `<span class="on">${i}</span>`
	}
	html += '</p>';

	// 다음 페이지, 마지막 페이지
	if (pagination.existNextPage) {//현재 위치한 페이지 뒤에 데이터가 있는 경우, 다음 페이지 버튼과 끝 페이지 버튼을 HTML에 추가
		html += `
            <a href="javascript:void(0);" onclick="movePage(${pagination.endPage + 1});" class="page_bt next">다음 페이지</a>
            <a href="javascript:void(0);" onclick="movePage(${pagination.totalPageCount});" class="page_bt last">마지막 페이지</a>
        `;
	}
	document.querySelector('.paging').innerHTML = html;
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