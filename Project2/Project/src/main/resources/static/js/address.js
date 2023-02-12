$(document).ready(function() {
	let myKey = "iH5hwc1qRUyfHb3NTdchiw"; // sweet tracker에서 발급받은 자신의 키 넣는다.
	let t_code = '05';
	let t_invoice = '451993953703';


	$.ajax({
		type: "GET",
		dataType: "json",
		url: "http://info.sweettracker.co.kr/api/v1/trackingInfo?t_key=" + myKey + "&t_code=" + t_code + "&t_invoice=" + t_invoice,
		success: function(data) {
			let trackingDetails = data.trackingDetails;
			let length=trackingDetails.length;

			let myTracking = "";
			$.each(trackingDetails, function(index, item) {
				if(index==length-1)
				myTracking += ('<span>' + item.kind + '</span>');
			});
			$(".PostDState").html(myTracking);

		}
	});
});

