$(function() {
	$(".category_drop_menu").hide();
	$(".mymenu_drop_menu1").hide();
	$(".mymenu_drop_menu2").hide();
	$(".header_category_box").click(function() {
		$(".category_drop_menu").show();
		$(".category_drop_menu").toggleClass("hide");
	});
	$(".header_mymenu_drop").click(function() {
		$(".mymenu_drop_menu1").show();
		$(".mymenu_drop_menu1").toggleClass("hide");
	});
	$(".header_mymenu_drop2").click(function() {
		$(".mymenu_drop_menu2").show();
		$(".mymenu_drop_menu2").toggleClass("hide");
	});
	
	$(function(){
		$("#sch").click(function(){
			let keyword=$("[id=search] :selected").val();
			let search=$("input[name=searchText]").val();
			search_f.submit(keyword,search);								
		});
	});
	$("#search_bt").click(function(){
			let f = $('#search').val();
			if (f == "") {
				alert("검색어를 입력하세요");
				return false;
			} else {
				$(".search_form").submit();
			}
		});
	
	
});

