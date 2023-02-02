
function ChartList(chart) {
	let DaysList = []; // x축 데이터 배열 생성
	let DayCountList = [];
	chart.forEach(row => {
		DaysList.push(row.date);
		DayCountList.push(row.count);
	});

	let context = document
		.getElementById('myChart')
		.getContext('2d');
	let myChart = new Chart(context, {
		type: 'bar', // 차트의 형태
		data: { // 차트에 들어갈 데이터
			labels: DaysList,//x축
			datasets: [
				{ //데이터
					label: '최근 7일간 판매 현황', //차트 제목
					fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
					data: DayCountList,
					backgroundColor: [
						//색상
						'rgba(255, 99, 132, 0.2)',
						'rgba(54, 162, 235, 0.2)',
						'rgba(255, 206, 86, 0.2)',
						'rgba(75, 192, 192, 0.2)',
						'rgba(153, 102, 255, 0.2)',
						'rgba(255, 159, 64, 0.2)'
					],
					borderColor: [
						//경계선 색상
						'rgba(255, 99, 132, 1)',
						'rgba(54, 162, 235, 1)',
						'rgba(255, 206, 86, 1)',
						'rgba(75, 192, 192, 1)',
						'rgba(153, 102, 255, 1)',
						'rgba(255, 159, 64, 1)'
					],
					borderWidth: 1 //경계선 굵기
				}
			]
		},
		options: {
			scales: {

				yAxes: [
					{
						ticks: {
							beginAtZero: true
						}
					}
				]
			}
		}
	});
}
/*<![CDATA[*/
function drawList(list, img, keyword) {
	let index = 0;
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
					<span>총 판매액:${row.p_endprice * row.p_sell}원</span>
				</div>
			`;
		if (keyword == 'add') {
			html += `<div class="mypage_info">
					<a href="/products/${row.p_id}/info">
						제품수정
					</a>
					</div>
					`;
		}

		html += "	</div> </div>";
		index++;
	});

	$(".mypage_list_cont").html(html); // #list에 해당 html을 대체해서 넣어준다.
}


// 페이지 HTML draw  하단의 버튼에 해당하는 스크립트로 페이징의 핵심
function drawPage(pagination, params) {
	//SearchDto의 기본 Default값을 바탕으로 mybatis의 count를 같이 받아와 계산후 저장시켜둔 pagingnation과 SearchDto를 받아옴
	if (!pagination || !params) {
		// pagination, params가 존재하지 않는다면 해당 스크립트를 출력
		$(".paging_btn").html("버튼이상");
		return false;
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