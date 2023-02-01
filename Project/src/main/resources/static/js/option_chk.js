$(function() {
	
	$.ajax({
			type: "POST",
			url: "/products/options",
			traditional: true,
			data: {
				opt_pid_p_fk: opt_pid_p_fk,
				opt_option1: opt_option1,
				opt_opt2_list: opt_option2,
				opt_quantity_list: opt_quantity
			},
			beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {
				alert("추가완료");
				$("input[name=opt_option1]").val("");
				$("input[name=opt_option2]").val("");
				$("input[name=opt_quantity]").val("");
				$(".type_btn_box").css("display", "none");
				if (result == "OneOptionAdd") {
					$(".option_form").css("display", "none");
					$(".opt_null").css("display", "flex");
				};
			},
			error: function(e) {
				alert('에러');
			}
		});
	let opt_chk = $("input[name=opt_chk").length;
	let opt_chk2 = $("input[name=opt_chk2").val();
	if (opt_chk != 0) {
		$(".type_btn_box").css("display", "none");
	}
	if (opt_chk !=0 && opt_chk2 =="") {
		$(".option_form").css("display","none");
		$(".opt_null").css("display","flex");
	};

});