$(function() {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	let img = $(".mini_img").attr("src");
	let p_id = $("#p_id").val();
	let p_recruit_date = $("#p_recruitdate").val();
	let opt1_default = $(".option1").val();
	$(".big_img").attr("src", img);





	const countDownTimer = function(id, date) {
		let _vDate = new Date(date); // 전달 받은 일자
		let _second = 1000;
		let _minute = _second * 60;
		let _hour = _minute * 60;
		let _day = _hour * 24;
		let timer;
		function showRemaining() {
			let now = new Date();
			let distDt = _vDate - now;

			if (distDt < 0) {
				if (date == $("#p_duedate").val()) {
					clearInterval(timer);
					$("#" + id).text('판매종료 되었습니다!');
					$(".prodetail_btn_box").css("display", "none");
					$(".time_box").css("margin-top", "150px");
					$(".time_box").css('background-color', 'gray');

					return;
				} else {
					let p_duedate = $("#p_duedate").val();
					clearInterval(timer);
					countDownTimer('time', p_duedate);
					$(".prodetail_btn_box").css("display", "flex");
					$(".time_box").css("margin-top", 0);
				}


			}

			let days = Math.floor(distDt / _day);
			let hours = Math.floor((distDt % _day) / _hour);
			let minutes = Math.floor((distDt % _hour) / _minute);
			let seconds = Math.floor((distDt % _minute) / _second);

			let html = days + '일' + hours + '시간' + minutes + '분' + seconds + '초';
			if (date == $("#p_duedate").val()) {
				html += '후 마감';
			} else {
				html += '후 오픈';
			}
			$("#" + id).text(html);
		}

		timer = setInterval(showRemaining, 100);
	}

	let dateObj = new Date();
	dateObj.setDate(dateObj.getDate() + 1);
	countDownTimer('time', p_recruit_date);


	$(".option1").change(function() {
		let opt1 = $(this).val();
		addcategory(opt1);
	});
	let addcategory = function(val) {
		$.ajax({
			type: "GET",
			url: "/products/options/" + p_id,
			traditional: true,
			data: {
				opt_option1: val
			},
			dataType: 'json',
			beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(data) {
				let html = ""
				$.each(data, function(idx, val) {
					html += `
						<option value="${val}">${val}</option>
				`;
				});
				$(".option2").html(html);

			},
			error: function(e) {
				alert('에러');

			}
		});
	}

	addcategory(opt1_default);

	$('.mini_img').click(function() {
		let value = $(this).attr("src");
		$(".big_img").attr("src", value);
	});

	$("#addCart").click(function() {
		// select box Name로 접근하여 선택된 값 읽기
		const p_id = $("#p_id").val();
		const opt_option1 = $("select[name=opt_option1]").val();
		const opt_option2 = $("select[name=opt_option2]").val();
		const o_quantity = $("select[name=order_quantity]").val();

		$.ajax({
			type: "POST",
			url: "/orders/carts",
			data: {
				o_product_p_fk: p_id,
				o_quantity: o_quantity,
				o_option1: opt_option1,
				o_option2: opt_option2
			},
			beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},

			success: function(result) {

			},
			error: function(e) {
				alert('이미 장바구니에 존재하는 상품입니다');

			}
		});
	});
});