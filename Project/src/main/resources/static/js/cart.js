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
});