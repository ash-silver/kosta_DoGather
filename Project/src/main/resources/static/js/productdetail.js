$(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let img = $(".mini_img").attr("src");
	let opt_option1 = $("select[name=opt_option1]").val();
	let p_id = $("#p_id").val();
	let p_recruit_date = $("#p_recruit_date").val();
	$(".big_img").attr("src", img);


	const countDownTimer = function(id, date) {
		let _vDate = new Date(date); // 전달 받은 일자
		let _second = 1000;
		let _minute = _second * 60;
		let _hour = _minute * 60;
		let _day = _hour * 24;
		let timer;
		
		
		function showRemaining() {
			var now = new Date();
			var distDt = _vDate - now;

			if (distDt < 0) {
				let p_due_date = $("#p_due_date").val();
				clearInterval(timer);
				countDownTimer('time', p_due_date);
				$(".prodetail_btn_box").css("display","flex");
				$(".time_box").css("margin-top",0);
			}

			let days = Math.floor(distDt / _day);
			let hours = Math.floor((distDt % _day) / _hour);
			let minutes = Math.floor((distDt % _hour) / _minute);
			let seconds = Math.floor((distDt % _minute) / _second);

			//document.getElementById(id).textContent = date.toLocaleString() + "까지 : ";
			document.getElementById(id).textContent = days + '일 ';
			document.getElementById(id).textContent += hours + '시간 ';
			document.getElementById(id).textContent += minutes + '분 ';
			document.getElementById(id).textContent += seconds + '초';
			if(distDt<0){
				document.getElementById(id).textContent +='후 마감';
			}else {
				document.getElementById(id).textContent +='후 오픈';
			}
		}

		timer = setInterval(showRemaining, 1000);
	}

	let dateObj = new Date();
	dateObj.setDate(dateObj.getDate() + 1);
	countDownTimer('time', p_recruit_date); // 2024년 4월 1일까지, 시간을 표시하려면 01:00 AM과 같은 형식을 사용한다.



	$(".option1").change(function() {
		let opt1 = $(this).val();
		addcategory(opt1);

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