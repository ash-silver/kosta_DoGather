$(function() {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");

	$('#remove_btn').click(function() {
		let length = $("input[type=checkbox]:checked").length - 1;
		let suc = 0;
		let failed = 0;
		$("input[type=checkbox]:checked").each(function(index) {
			let chk = $(this).val();
			if (index != 0) {

				$.ajax({
					type: "delete",
					url: "/orders/carts",
					data: {
						chk: chk
					},
					beforeSend: function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						xhr.setRequestHeader(header, token);
					},
					success: function() {
						++suc;
						if (index == length) {
							if (failed <= 0) {
								alert(suc + "개 삭제 완료");
								location.reload();
							} else {
								alert("삭제 이상 발생");
							}
						}
					},
					error: function() {
						++failed;
					}
				});
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

	$(document).ready(function() {
		$("#cbx_chkAll").click(function() {
			if ($("#cbx_chkAll").is(":checked")) $("input[name=o_id]").prop("checked", true);
			else $("input[name=o_id]").prop("checked", false);
		});

		$("input[name=o_id]").click(function() {
			var total = $("input[name=o_id]").length;
			var checked = $("input[name=o_id]:checked").length;

			if (total != checked) $("#cbx_chkAll").prop("checked", false);
			else $("#cbx_chkAll").prop("checked", true);
		});
	});





});
