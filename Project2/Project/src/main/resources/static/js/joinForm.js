$(function() {

	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let chk = "alsrbgurwn";
	//let time_chk = 0;
	//let faild_chk = 0;
	let pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

	$('#code_check').hide();
	$("#code_send").prop("disabled", true)

	$("#m_email").blur(function() {
		let email = $("#m_email").val();

		if (email == null || email == "") {
			$(".email_error").css("display", "flex");
			$(".email_error").text("이메일을 입력해주세요");
			$("#code_send").prop("disabled", true);
		}
		else if (email.match(pattern) == null) {
			$(".email_error").css("display", "flex");
			$(".email_error").text("잘못된 형식의 이메일 주소입니다");
			$("#code_send").prop("disabled", true);
		}
		else {
			emailCheck();
		}
	});

	function emailCheck() {

		let email = $("#m_email").val();

		$.ajax({
			url: '/member/emailCheck',
			type: 'post',
			data: {
				m_email: email
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: function(cnt) {  //0 아이디 없음, 1 아이디 있음
				if (cnt == 0 && email != "") {
					$(".email_error").css("display", "flex");
					$(".email_error").text("사용가능한 이메일입니다. 이메일 인증을 해주세요");
					$("#code_send").prop("disabled", false);

				} else {
					$(".email_error").css("display", "flex");
					$(".email_error").text("이미 존재하는 이메일입니다");
					$("#code_send").prop("disabled", true);
				}
			},
			error: function() {
				console.log("이메일 중복체크 ajax 실패")
				$(".email_error").css("display", "flex");
				$(".email_error").text("다시 시도해주세요");
			}
		});
	}

	//$("#code_send").on("click", function(){
	$("#code_send").click(function() {
		let email = $("#m_email").val();

		if ($(".email_error").text("사용가능한 이메일입니다. 이메일 인증을 해주세요")) {
			$.ajax({
				url: "/member/sendEmail",
				type: "POST",
				data: {
					m_email: email
				},
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success: function(data) {
					$("#code_send").prop("disabled", true);
					$(".code_error").css("display", "none");
					$(".email_error").css("display", "flex");
					$(".email_error").text("인증번호가 전송되었습니다");
					$('#code_check').show();
					chk = data;

					timer_start();
				},
				error: function() {
					$(".code_error").css("display", "flex");
					$(".email_error").text("일시적인 전송에러가 발생하였습니다. 다시 시도해 주세요");
				}
			})
		}
	});

	$("#code_check").on("click", function() {

		$(".code_error").css("display", "flex");
		let num = $("#m_code").val();


		if (num == chk) {
			$(".email_error").css("display", "none");
			$(".code_error").text("인증되었습니다");
			$("#code_check").hide();
			$("#timer").hide();
		} else if (num == "" || num == null) {
			$(".code_error").text("인증번호를 입력해주세요");
		}
		else {
			$(".code_error").text("번호가 일치하지 않습니다");
		}
	});


	$("#m_pwd2").blur(function() {
		let pwd = $("#m_pwd").val();
		let pwd2 = $("#m_pwd2").val();

		if (pwd === pwd2) {
			$(".pwd2_error").css("display", "flex");
			$(".pwd2_error").text("비밀번호가 일치합니다");
		} else {
			$(".pwd2_error").css("display", "flex");
			$(".pwd2_error").text("비밀번호가 일치하지않습니다.");
		}
	})


	function timer_start() {

		let current_time = 0;

		// 인증코드 유효성 true
		code_valid = true;
		// 현재 발송 시간 초기화
		current_time = 0
		// 20초
		let count = 30;

		//   timer.innerHTML = "00:30"
		// 1초마다 실행
		timer_thread = setInterval(function() {

			minutes = parseInt(count / 60, 10);
			seconds = parseInt(count % 60, 10);

			minutes = minutes < 10 ? "0" + minutes : minutes;
			seconds = seconds < 10 ? "0" + seconds : seconds;


			timer.innerHTML = minutes + ":" + seconds;


			// 타이머 끝
			if (--count < 0) {
				//++time_chk;
				timer_stop();
				//	timer.textContent = "00:00"

				$("#code_check").hide()
				$("#code_send").text("재전송")
				$("#code_send").prop("disabled", false);

				$(".code_error").css("display", "flex");
				$(".code_error").text("인증코드가 만료되었습니다. 재전송해주세요")
				$(".email_error").css("display", "none");
			}
			current_time++

		}, 1000);

	}


	// 타이머 종료
	function timer_stop() {
		// 타이머 종료

		clearInterval(timer_thread)
		// 유효시간 만료
		code_valid = false
	}

	// 인증코드가 유효하면 true, 만료되었다면 false 반환
	function iscodeValid() {

		return code_valid;

	}
}) //1번 function