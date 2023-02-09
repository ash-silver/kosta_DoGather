$(function() {

	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let chk = 0;
	let date = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, -5);
	 $(".recruidate").attr("min", date);
	 $(".duedate").attr("min", date);

	$("input[name=p_recruitdate]").change(function() {
		if ($(".recruidate").val() < date) {
			alert('현재 시간보다 이전의 날짜는 설정할 수 없습니다.');
			++chk;
		}else if($(".recruidate").val() >= $(".duedate").val()){
			++chk;
		}else{
			chk=0;
		}
	});
	$("input[name=p_duedate]").change(function() {
		if ($(".duedate").val() < date) {
			alert('현재 시간보다 이전의 날짜는 설정할 수 없습니다.');
			++chk;
		} else if ($(".recruidate").val() >= $(".duedate").val()) {
			alert("마감시간은 시작일자보다 늦을 수 없습니다.");
			++chk;
		}else{
			chk=0;
		}
	});



	$("#addpro_btn").click(() => {
		if (chk == 0) {
			$(".productadd_form").submit();
		}else{
			alert("시간 설정이 유효하지 않습니다.");
		}
	});

	$(document).mouseup(function(e) {
		if ($(".modal_form_pimg").has(e.target).length === 0) {
			$(".modal_form_pimg").hide();
		}
		if ($(".modal_form_contentimg").has(e.target).length === 0) {
			$(".modal_form_contentimg").hide();
		}
	});

	$(document).keydown(function(e) {
		//keyCode 구 브라우저, which 현재 브라우저
		var code = e.keyCode || e.which;

		if (code == 27) { // 27은 ESC 키번호
			$('.modal_form_pimg').hide();
			$('.modal_form_contentimg').hide();
		}

	});
	$("#discount_btn").click(function() {
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
	$(".img_box").click(function() {
		$(".modal_form_pimg").fadeIn();
		$(".modal_form_pimg").css("display", "flex");
	});
	$(".contentimg_box").click(function() {
		$(".modal_form_pimg").fadeIn();
		$(".modal_form_contentimg").css("display", "flex");
	});

	$('input[type=file]').change(function() {
		let img = $(this).attr("id");
		setImageFromFile(this, '.' + img);

	});

	function setImageFromFile(input, expression) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$(expression).attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
});
