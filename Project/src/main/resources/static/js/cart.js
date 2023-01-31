$(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$('#remove_btn').click(function() {
		let chk_arr = [];
		$("input[type=checkbox]:checked").each(function() {
			let chk = $(this).val();
			chk_arr.push(chk);
		})


		$.ajax({
			type: "DELETE",
			url: "/orders/carts",
			traditional: true,
			data: {
				o_id : chk_arr
			},
			beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(data) {
 				location.reload();
 				alert('삭제완료')
			},
			error: function(e) {
				console.log(e);

			}
		});
	});
	
	
	$("#moalbutton").click(function() {
		$(".modal").css("display", "flex");
	});
	
	$(document).mouseup(function(e) {
		if ($(".modal").has(e.target).length === 0) {
			$(".modal").hide();
		}
		
	});

	$(document).keydown(function(e) {
		//keyCode 구 브라우저, which 현재 브라우저
		var code = e.keyCode || e.which;

		if (code == 27) { // 27은 ESC 키번호
			$('.modal').hide();
		
		}

	});




});