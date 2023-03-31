window.onload = function(){

  //오늘 날짜출력
    $(document).ready(function () {
    
      function convertTime() {
          let now = new Date();
    
          let year = now.getFullYear();
          let month = now.getMonth() + 1;
          let date = now.getDate();
          let hours = now.getHours();
          let minutes = now.getMinutes() <= 9 ? '0' + now.getMinutes() : now.getMinutes();
          // hour(시)가 12 이상이면 오후(pm), 12 미만이면 오전(am)으로 설정
          let ampm = hours >= 12 ? 'pm' : 'am';
          // heour(시)를 12시간 단위로 변경 => 13시부터 12로 나눈 나머지(1~12)로, 그 미만은 그대로
          let hours2 = hours > 12 ? hours % 12 : hours;

          let weekday = new Array(7);
          weekday[0] = "일";
          weekday[1] = "월";
          weekday[2] = "화";
          weekday[3] = "수";
          weekday[4] = "목";
          weekday[5] = "금";
          weekday[6] = "토";
    
          let n = weekday[now.getDay()];
    
          return year +'.'+ month + '.' + date + '(' + n + ') ' + hours2 + ':' + minutes + ampm;
      }
    
      let currentTime = convertTime();
      $('.nowtime').append(currentTime);
    });
    
    //제이쿼리사용
    $.getJSON('https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&appid=5a87979705c7dd0e87fc7cfda0976f92&lang=kr&units=metric',
    function (WeatherResult) {
    
      // 파라미터
      // q : 도시명
      // appid : apikey (발급필요)
      // lang : 언어 (kr : 한국어)
      // units : 온도표시 방식 (metric : 섭씨)
    
      // 날씨정보 출력
      Math.round
      $('.SeoulWeatherDesc').append(WeatherResult.weather[0].description);
      $('.SeoulNowtemp').append(Math.round(WeatherResult.main.temp,1)+'°C');
      $('.SeoulLowtemp').append(Math.round(WeatherResult.main.temp_min,1)+'°C');
      $('.SeoulHightemp').append(Math.round(WeatherResult.main.temp_max,1)+'°C');
    
      //날씨아이콘출력
      //WeatherResult.weater[0].icon
      let weathericonUrl =
          '<img src= "http://openweathermap.org/img/wn/'
          + WeatherResult.weather[0].icon +
          '.png" alt="' + WeatherResult.weather[0].description + '"/>'
    
      $('.SeoulIcon').html(weathericonUrl);
    });

};
