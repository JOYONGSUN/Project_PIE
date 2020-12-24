<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var wsocket;
	
	var nickname='<%=session.getAttribute("nickname")%>'
	function connect() {
		wsocket = new WebSocket(
			"ws://localhost:8090/bit/chat?select=" + $("#select").val());
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}
	function disconnect() {
		wsocket.close();
		
	}
	function onOpen(evt) {
	}
	function onMessage(evt) {
		
		var data = evt.data;

		appendMessage(data);

	}
	function onClose(evt) {
		
	}

	function send() {
		console.log("nickname :" +nickname);
		var msg = $("#message").val();
		if(msg.trim()!=""){
			wsocket.send(nickname + "|" + msg);
		}
		$("#message").val("");
	}

	function appendMessage(msg) {
		var strarray = msg.split('|');

		var msginfo = strarray[0];
		console.log(">"+msginfo+"<")
		var message = strarray[1];
		var msgbox;
		if (msginfo == nickname) {
			msgbox = '<div class = "message-wrapper container-fluid" align="right">'
			msgbox += '<div class= "message-textbox mymessage"  align="left">'
					+ message + '</div></div>'
		}else if(msginfo == "알림"){
			console.log("알림에 걸림")
			msgbox = '<div class = "text-inform message-wrapper" align="center"> ---------'+message+' ---------<div>'
		}else {
			msgbox = '<div class = "message-wrapper container-fluid" >'
					+ msginfo

			msgbox += '<br><div class= "message-textbox othermessage">'
					+ message + '</div></div>'
		}
		$("#chatMessageArea").append(msgbox);
		var chatAreaHeight = $("#chatArea").height();
		var maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		$("#chatArea").scrollTop(maxScroll);
	}

	$(document).ready(function() {

		connect();
		$('#message').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
					send();
			}
			event.stopPropagation();
		});
		$('#sendBtn').click(function() {
			send();
		});
		$('#exitBtn').click(function() {
			disconnect();
			window.close();
		});
	});
</script>

<style type="text/css">
.chat {
	padding: 0px;
	width: 100%;
	height: 600px;

}

.chat .chat-display {
	background-color: #e6f2ff;
	overflow-y: auto;
}

.chat-text {
	width: 100%;
	background-color: transparent;
}

.textinput {
	width: 80%;
}

.text-bottom {
	padding: 5px;
	min-height: 550px;
	height: 100%
}

.text-inform{
	color: #808080;
}

.message-wrapper {
	width: 100%;
	padding: 10px
}

.message-textbox {
	border-radius: 5px;
	box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.3);
	max-width: 70%;
	padding: 5px;
	padding-left: 7px;
	padding-right: 7px;
	display: inline-block;
	font-size: 12pt;
	word-spacing: 1pt;
	text-overflow: ellipsis;
}

.othermessage {
	background-color: white;
}

.mymessage {
	background-color: yellow;
}

#header {
	background-color: #3399ff;
	box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.3);
}

#roomname {

	float: right;
}

#message {
	border-radius: 5px;
}

.text-body {
	height: 100%
}

</style>
</head>
<body>
	<div class="text-body">
		<nav id="header" class="navbar navbar-expand-sm navbar-fixed-top">
			<ul class="navbar-nav ">
				<li><a href = "#" id = "exitBtn">나가기</a></li>
			</ul>
			<h4 id="roomname" >${roomname}</h4>
		</nav>
		<div class="container-fluid chat" align="center">
			<div id="chatArea" class="chat chat-display" align="justify">
				<div id="chatMessageArea" class="chat-text">
				</div>
			</div>
			<input type="hidden" value="${select}" id="select">
		</div>
	
		<nav class="navbar navbar-expand-sm navbar-fixed-bottom ">
			<input type="text" id="message" class="textinput">
			<input type="button" id="sendBtn" class="btn btn-default btn-sm"value="전송">
		</nav>
	</div>
</body>
</html>