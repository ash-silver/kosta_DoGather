$(function() {
	let opt_pid_p_fk = $("input[name=opt_pid_p_fk]").val();

	$.ajax({
		type: "GET",
		url: "/products/options/chk",
		traditional: true,
		data: {
			opt_pid_p_fk: opt_pid_p_fk,

		},
		success: function(result) {
			if (result != null && result != "") {
				$(".type_btn_box").css("display", "none");
				if (result.opt_option2 == null || result.opt_option2 == "") {
					$(".option_form").css("display", "none");
					$(".opt_null_up").css("display", "flex");
				}
			}
		},
		error: function(e) {
			alert('에러');
		}
	});


	$(document).keydown(function(e) {

		if (e.which === 116) {
			if (typeof event == "object") {
				event.keyCode = 0;
				alert("옵션 미등록시 제품 삭제처리 됩니다.");
			}
			return false;
		} else if (e.which === 82 && e.ctrlKey) {
			alert("옵션 미등록시 제품 삭제처리 됩니다.");
			return false;
		}
	});
	let opt_chk = $("input[name=opt_chk").length;
	let opt_chk2 = $("input[name=opt_chk2").val();
	if (opt_chk != 0) {
		$(".type_btn_box").css("display", "none");
	}
	if (opt_chk != 0 && opt_chk2 == "") {
		$(".option_form").css("display", "none");
		$(".opt_null").css("display", "flex");
	};

});