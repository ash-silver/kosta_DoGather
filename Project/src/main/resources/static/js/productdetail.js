$(function() {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	let Option_Array = [];
	let img = $(".mini_img").attr("src");
	let p_id = $("#p_id").val();
	let p_recruit_date = $("#p_recruitdate").val();
	let opt1_default = $(".option1").val();
	$(".big_img").attr("src", img);


	new Swiper('.swiper-container', {

		slidesPerView: 3, // 동시에 보여줄 슬라이드 갯수
		spaceBetween: 20, // 슬라이드간 간격
		slidesPerGroup: 3, // 그룹으로 묶을 수, slidesPerView 와 같은 값을 지정하는게 좋음

		// 그룹수가 맞지 않을 경우 빈칸으로 메우기
		// 3개가 나와야 되는데 1개만 있다면 2개는 빈칸으로 채워서 3개를 만듬
		loopFillGroupWithBlank: true,

		loop: true, // 무한 반복

		pagination: { // 페이징
			el: '.swiper-pagination',
			clickable: true, // 페이징을 클릭하면 해당 영역으로 이동, 필요시 지정해 줘야 기능 작동
		},
		navigation: { // 네비게이션
			nextEl: '.swiper-button-next', // 다음 버튼 클래스명
			prevEl: '.swiper-button-prev', // 이번 버튼 클래스명
		},
	});




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
					$("select").prop('disabled',true);
					$("#quantity").css("display","none");
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


	let addcategory = function(val) {
		$.ajax({
			type: "GET",
			url: "/products/options/" + p_id,
			traditional: true,
			data: {
				opt_option1: val
			},
			beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(data) {
				console.log(data);
				Option_Array = data;
				let html = ""
				if (data[0].opt_option2 != null) {
					$.each(data, function(index, item) {
						html += `
						<option value="${item.opt_option2}">${item.opt_option2}</option>
				`;
						if (index == 0) {
							$(".option_quanity").html("선택한 옵션의 남은 수량 :" + item.opt_quantity + "개");
						}
					});
					$(".option2").html(html);
					$("#option2").css('display', 'flex');
				} else {
					$.each(data, function(index, item) {
						if (val == item.opt_option1) {
							$(".option_quanity").html("선택한 옵션의 남은 수량 :" + item.opt_quantity + "개");
						}

					});
				}
			},
			error: function(e) {
				alert('에러');

			}
		});
	}

	$(".option1").change(function() {
		let opt_option1_value = $(this).val();
		addcategory(opt_option1_value);
	});

	$(".option2").change(function() {
		const opt_option2_value = $(this).val();
		$.each(Option_Array, function(idx, val) {
			if (opt_option2_value == val.opt_option2) {
				$(".option_quanity").html("선택한 옵션의 남은 수량 :" + val.opt_quantity + "개");
			}
		});
	});

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