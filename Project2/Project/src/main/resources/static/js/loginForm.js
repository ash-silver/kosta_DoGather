
$(function() {

	//let token = $("meta[name='_csrf']").attr("content");
//	let header = $("meta[name='_csrf_header']").attr("content");
	let pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

	$("#m_email").blur(function() {
		let email = $("#m_email").val();//이메일 유효성 api

		if (email == null || email == "") {
			$(".email_error").css("display", "flex");
			$(".email_error").text("이메일을 입력해주세요");
		}
		else if (email.match(pattern) == null) {
			$(".email_error").css("display", "flex");
			$(".email_error").text("잘못된 형식의 이메일 주소입니다");
		}
		else {
			$(".email_error").css("display", "none");
		}
	});


$(".show").click(function() {
alert("test")
});


$("#login").click(function() {
	let email = $("#m_email").val();
	let pwd= $("#m_pwd").val();
	
	
	});
	

	
	

});
