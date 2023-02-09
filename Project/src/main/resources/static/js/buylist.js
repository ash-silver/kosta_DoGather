// 리스트 HTML draw
// 제품 리스트를 출력
function drawList(list, product, img) {
	let index = 0;
	let html = '';
	list.forEach(row => { // list 의 갯수만큼 반복진행 row 라는 변수명으로 list명을 대체함 화살표함수
		html += `
    
    	<div class="mypage_list_tag">
			<div class="mypage_list_tag_img">
			<a href="/products/${product[index].p_id}/detail">
			<img src="/projectimg/${img[index].img_name}">
				</a>
			</div>
			<div class="myapge_list_tag_text">
				<div class="myapge_text">
					<span>제품명:${product[index].p_name}</span>
					<span>수량:${row.o_quantity}개</span>
					<span>가갹:${product[index].p_price}원</span>
					<span>옵션1:${row.o_option1}원</span>
					`;
		if (row.o_postCode != null) {
			html += `<input type="hidden" name="o_postCode" class="o_postCode" id="${index}" value="${row.o_postCode}">
					<input type="hidden" name="o_postCompanyKey" class="o_postCompanyKey" value="${row.o_postCompanyKey}">
					<span class="${index}"></span>`;
		} else {
			html += `<span>상품 준비중</span>`;
		}

		html += `
				</div>
			
			</div>
			
			<div class="request_Box">
				<button id="request_btn" value="${row.o_id}">교환문의</button>
		
				<button class="request_btn" value="${row.o_id}">환불문의</button>
			</div>	
		</div>
       
    `;
		; index++;
	})
	$(".mypage_list_cont").html(html); // #list에 해당 html을 대체해서 넣어준다.
}



$(document).on("click","#request_btn",function() {
	let o_id = $(this).val();
	alert(o_id);
	$("#buyList_Modal").attr("action", "/refund/"+o_id);
	
});
// 페이지 HTML draw  하단의 버튼에 해당하는 스크립트로 페이징의 핵심
function drawPage(pagination, params) {
	//SearchDto의 기본 Default값을 바탕으로 mybatis의 count를 같이 받아와 계산후 저장시켜둔 pagingnation과 SearchDto를 받아옴
	if (!pagination || !params) {
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
	// 1,2,3,4,5~~~ 페이지의 번호를 만들어내는 구문
	for (let i = pagination.startPage; i <= pagination.endPage; i++) {
		html += (i !== params.page)
			? `<a href="javascript:void(0);" onclick="movePage(${i});">${i}</a>`
			: `<span class="on">${i}</span>`
	}

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


function address() {
	let myKey = "iH5hwc1qRUyfHb3NTdchiw"; // sweet tracker에서 발급받은 자신의 키 넣는다.
	let t_code = [];
	let t_invoice = [];
	let t_id = [];
	$('.o_postCompanyKey').each(function(index, item) {
		if ($(item).val() != 0) {
			t_invoice.push($(item).val());
		}
	});
	$('.o_postCode').each(function(index, item) {
		if ($(this).val() != 'null') {
			t_code.push($(this).val());
		}
	});
	$('.o_postCode').each(function(index, item) {
		if ($(this).val() != 'null') {
			t_id.push($(this).attr("id"));
		}
	});


	$(t_code).each(function(index, item) {
		$.ajax({
			type: "GET",
			dataType: "json",
			url: "http://info.sweettracker.co.kr/api/v1/trackingInfo?t_key=" + myKey + "&t_code=" + item + "&t_invoice=" + t_invoice[index],
			success: function(data) {
				let trackingDetails = data.trackingDetails;
				let length = trackingDetails.length;
				let myTracking = "";
				$.each(trackingDetails, function(index, item) {
					if (index == length - 1)
						myTracking = item.kind;
				});
				if (myTracking == null || myTracking == "") {
					$("." + t_id[index]).text("배송 준비중");
				} else {
					$("." + t_id[index]).text(myTracking);
				}
			}
		});
	});
};
/*]]>*/