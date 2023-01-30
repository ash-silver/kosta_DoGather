$(function(){
	let opt1=$(".option1").val();
	let opt2=$(".option2").val();
	$("input[name=o_option1]").val(opt1);
	$("input[name=o_option2]").val(opt2);
	$(".option1").change(function(){
		let opt1_chk=$(this).val();
		$("input[name=o_option1]").val(opt1_chk);
	});
	$(".option2").change(function(){
		let opt2_chk=$(this).val();
		$("input[name=o_option2]").val(opt2_chk);
	});
});