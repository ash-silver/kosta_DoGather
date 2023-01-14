$(function(){
	$('.mini_img').click(function() {
			let value = $(this).attr("src");
			$(".big_img").attr("src", value);
		});
	
	
});