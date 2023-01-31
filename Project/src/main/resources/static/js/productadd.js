$(function() {

	let date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, -5);
	$(".recruidate").val(date);
	$(".duedate").val(date);
	$(".duedate").attr("min", date);
	$(".recruidate").attr("min", date);

	$("input[name=p_recruitdate]").change(function() {
		if ($(".recruidate").val() < date) {
			alert('현재 시간보다 이전의 날짜는 설정할 수 없습니다.');
			$(".recruidate").val(date);
		}
	});
	$("input[name=p_duedate]").change(function() {
		if ($(".duedate").val() < date) {
			alert('현재 시간보다 이전의 날짜는 설정할 수 없습니다.');
			$(".duedate").val(date);
		} else if ($(".recruidate").val() >= $(".duedate").val()) {
			alert("마감시간은 시작일자보다 늦을 수 없습니다.");
			$(".duedate").val(date);
		}
	});
	$("#addpro_btn").click(() => {
		let chk = 0;
		let ret = true;

		$("#product_form").find("input[name=dis_quantity]").each(function() {

		});
		$("#product_form").find("input[type=text]").each(function(index, item) {
			// 아무값없이 띄어쓰기만 있을 때도 빈 값으로 체크되도록 trim() 함수 호출
			if ($(this).val().trim() == '') {
				if ($(this).attr("name") == 'p_discount_quan' || $(this).attr("name") == 'p_discount_count') {
					if (!isNaN($(this).val())) {
						alert("할인 수량,할인율 숫자만 입력");
						++chk;
						ret = false;
						return false;
					}
				}
				alert($(this).attr("data-name") + " 항목을 입력하세요.");
				++chk;
				ret = false;
				return false;
			}
		});
		if (!ret) {
			return false;
		}
		$("#product_form").find("input[type=file]").each(function(index, item) {
			if ($(this).val().trim() == '') {
				alert($(this).attr("data-name") + " 항목을 입력하세요.");
				++chk;
				return false;
			}
		});

		if ($(".recruidate").val() >= $(".duedate").val()) {
			alert("마감시간은 시작일자보다 늦을 수 없습니다.");
			++chk;
		}
		if (chk == 0) {
			$("form").submit();
		}
	});
	$("#discount_btn").click(() => {
		let html = `<div class="discount_add">
						<span>설정:</span>
						<input type="text" name="p_discount_quan" placeholder="할인 기준 수량">
						<input type="text" name="p_discount_count" placeholder="할인율">
					</div>`;
		let cl = $('.discount_add').length;
		if (cl >= 3) {
			alert('할인율은 3개 까지만 등록가능')
		} else {
			$('.input_tag_discount').append(html);
			++cl;
			$("#count").attr("value", cl);
		}
	});
});