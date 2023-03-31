
    //브라우저가 HTML을 전부 읽고 DOM 트리를 완성하는 즉시 발생
    document.addEventListener('DOMContentLoaded', function () {
      let calendarEl = document.getElementById('calendar');
      let Calendar = FullCalendar.Calendar;

      let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        defaultDate: new Date(),
        customButtons: {
          prev: {
            text: "Prev month",
            click: function () {
              calendar.prev()
              getCalendar(calendar.getDate())
            }
          },
          next: {
            text: "Next month",
            click: function () {
              calendar.next()
              getCalendar(calendar.getDate())
            }
          },
          today: {
            text: "today",
            click: function () {
              console.log(calendar.gotoDate(new Date()))
              getCalendar(calendar.getDate())
            }
          },

          // // 이벤트 구현
          // AddEventButton: {
          //   // 오른쪽 텍스트
          //   text: "일정 추가",
          //   click: function () {
          //     $("#calendarModal").modal('show');
          //     $("#addCalendar").on("click", function () {

          //       location.replace(location.href);

          //       let content = $("#calendar_content").val();
          //       let start_date = $("#calendar_start_date").val();
          //       let end_date = $("#calendar_end_date").val();

          //       if (content == null || content == "") {
          //         alert("내용을 입력하세요.");
          //       } else if (start_date == "" || end_date == "") {
          //         alert("날짜를 입력하세요.");
          //       } else if (new Date(end_date) - new Date(start_date) < 0) { // date 타입으로 변경 후 확인
          //         alert("종료일이 시작일보다 먼저입니다.");
          //       } else {
          //         let obj = {
          //           "content": content,
          //           "start": start_date,
          //           "end": end_date
          //         }
          //       //   중요!
          //         setCalendar(content, start_date, end_date)
          //       }
          //     });
          //     $("#calendarModal").modal('hide');
          //   }
          // },
        },
        eventSources: [],
        //   headerToolbar
        headerToolbar: {
          left: 'prev,next today',
          center: 'title'
          // right: 'AddEventButton'
        },
        editable: false,
        droppable: true,
      });

      // DB 데이터 set
      function setCalendar(content, start, end) {
        $.ajax({
            url: "/api/calendar",
            method: "POST",
            dataType: "json",
            async: false,
            data: {
              content: content,
              start: start,
              end: end
            }
          })
        //   성공시
          .done(function (data) {
            getCalendar(calendar.getDate())
            calendar.render();
          })
        //   실패시
          .fail(function (xhr, status, errorThrown) {
            console.log("오류");
          })
        //   성공,실패 상관없이 항상실행
          .always(function (xhr, status) {
            console.log("완료");
          });

      }

      // DB 데이터 get
      function getCalendar(date) {
        calendar.removeAllEvents();
        let result;
        month = date.getMonth() + 1
        if (month < 10) {
          month = "0" + month
        }
        $.ajax({
            // 별도 메소드 타입 없으면 get방식
            url: "/api/calendar",
            dataType: "json",
            async: false
          })
          .done(function (data) {
            console.log(data);
            $.each(data, function (index, element) {
              console.log(element.content, element.start);
              calendar.addEvent({
                // title, start, end => 예약어
                title: element.content,
                start: element.start,
                end: element.end,
              })
            })
            calendar.render();
            result = data
          })
          .fail(function (xhr, status, errorThrown) {
            console.log("오류");
          })
          .always(function (xhr, status) {
            console.log("완료");
          });
        return result
      }
      // 처음 실행 시
      calendar.addEvent({
        title: "금요일",
        start: "2023-03-24"
      })
      calendar.render(); // 그린다(실제 브라우저에 표시)
      getCalendar(calendar.getDate()); // getCalendar함수 호출

    });