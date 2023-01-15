$(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let img = $(".mini_img").attr("src");
	let opt_option1 = $("select[name=opt_option1]").val();
	let p_id = $("#p_id").val();
	



	$(".big_img").attr("src", img);
	window.onload = () => {
		addcategory(opt_option1);
	}
	$(".option1").change(function() {
		addcategory($(this).val());

	});
	let addcategory = function(val) {
		$.ajax({
			type: "GET",
			url: "/products/categorys",
			traditional: true,
			data: {
				opt_option1: val,
				p_id: p_id
			},
			dataType: 'json',
			beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(data) {
				let html = ""
				$.each(data, function(idx, val) {
					html += `
						<option value="${val.opt_option2}">${val.opt_option2}</option>
				`;

				});
				$(".option2").html(html);

			},
			error: function(e) {
				alert('에러');

			}
		});
	}

	$('.mini_img').click(function() {
		let value = $(this).attr("src");
		$(".big_img").attr("src", value);
	});


});