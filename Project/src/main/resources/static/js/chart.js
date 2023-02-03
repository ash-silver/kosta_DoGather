
function WeekSellList(WeekSell) {
	let DaysList = []; // x축 데이터 배열 생성
	let DaySellPriceList = [];
	let context = document
		.getElementById('type2')
		.getContext('2d');
	WeekSell.forEach(row => {
		DaysList.push(row.date);
		DaySellPriceList.push(row.endprice);
	});
	let myChart = new Chart(context, {
		type: 'line',
		data: {
			labels: DaysList,
			datasets: [{
				label: '최근 7일간 판매 매출',
				data: DaySellPriceList,
				backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
					'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)'
				],
				borderColor: [
					'rgba(255, 99, 132, 1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)',
					'rgba(255, 159, 64, 1)'
				],
				borderWidth: 2
			}]
		},
		options: {
			responsive: false,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			},
		}
	});
}

function WeekList(Week) {
	let DaysList = []; // x축 데이터 배열 생성
	let DayCountList = [];
	Week.forEach(row => {
		DaysList.push(row.date);
		DayCountList.push(row.count);
	});

	let context = document
		.getElementById('type1')
		.getContext('2d');
	let myChart = new Chart(context, {
		type: 'bar', // 차트의 형태
		data: { // 차트에 들어갈 데이터
			labels: DaysList,//x축
			datasets: [
				{ //데이터
					label: '최근 7일간 제품 판매 수량', //차트 제목
					fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
					data: DayCountList,
					  backgroundColor: [
                      'rgba(255, 99, 132, 0.2)',
                      'rgba(54, 162, 235, 0.2)',
                      'rgba(255, 206, 86, 0.2)',
                      'rgba(75, 192, 192, 0.2)',
                      'rgba(153, 102, 255, 0.2)',
                      'rgba(255, 159, 64, 0.2)'
                  ],
                  borderColor: [
                      'rgba(255, 99, 132, 1)',
                      'rgba(54, 162, 235, 1)',
                      'rgba(255, 206, 86, 1)',
                      'rgba(75, 192, 192, 1)',
                      'rgba(153, 102, 255, 1)',
                      'rgba(255, 159, 64, 1)'
                  ],
                  borderWidth: 2
              }]
          },
          options: {
              responsive: false,
              scales: {
                  yAxes: [{
                      ticks: {
                          beginAtZero: true
                      }
                  }]
              },
          }
      });
}