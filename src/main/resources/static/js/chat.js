$(function(){
	$("#question").keyup(questionKeyuped);
});

function openChat(){
	setConnectStated(true);// 연결 버튼 클릭시 보이기
	connect();  // 연결
}

function showMessage(message) {
    // 메시지를 #caht-content 에 추가
    $("#chat-content").append(message);
    // 대화창 스크롤을 항상 최하위에 배치
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}

// 접속 버튼 클릭시 토글
function setConnectStated(isTrue){
	if(isTrue){//true
		$("#btn-chat-open").hide();
		$("#chat-disp").show();
	}else{
		$("#btn-chat-open").show();
		$("#chat-disp").hide();
	}
	// 화면 초기화
	$("#chat-content").html("");
}

// 접속 해제
function disconnect() {
//    if (stompClient !== null) {
//        stompClient.disconnect();
//    }
    setConnectStated(false);
    console.log("Disconnected");
}

 // 연결버튼클릭시 접속
 // 처음 접속시
function connect() {
	sendMessage("안녕");
}

function sendMessage(message){
	$.ajax({
		url:"/botController",
		type:"post",
		data:{message: message},
		success:function(responsedHtml){
			showMessage(responsedHtml);
		}
	});
}

function inputTagString(text){
	var now=new Date();
	var ampm=(now.getHours()>11)?"오후":"오전";
	var time= ampm + now.getHours()%12+":"+now.getMinutes();
	var message=`
		<div class="msg user flex end" id="userMsgBox" style="text-align: right">
			<div class="message">
				<div class="part" style="text-align: right">
					<p style="magin: 0">${text}</p>
				</div>
				<div class="time">${time}</div>
			</div>
		</div>
	`;
	return message;
}
//메뉴클릭시 메뉴 텍스트 화면에 표현 
function menuclicked(el){
	var text=$(el).text().trim();
	var fToken=$(el).siblings(".f-token").val();
	console.log("-----> fToken:"+fToken+"----");
	var message=inputTagString(text);
	showMessage(message);
}

//엔터가 입력이되면 질문을 텍스트 화면에 표현 
function questionKeyuped(event){
	if(event.keyCode!=13)return;
	btnMsgSendClicked()
}

//전송버튼 클릭이되면 질문을 텍스트 화면에 표현
function btnMsgSendClicked(){
	var question=$("#question").val().trim();
	if(question=="" || question.length<2)return;
	//메세지 서버로 전달
	sendMessage(question);
	 
	var message=inputTagString(question);
	showMessage(message);//사용자가 입력한 메세지 채팅창에 출력
	$("#question").val("");//질문 input 리셋
}
