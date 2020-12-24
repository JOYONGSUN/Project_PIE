
	 //3. 버튼으로 눌러진 방 정보를 파라미터랑 userid(security) 가지고 chatroom.jsp로 가기 위해 controller 태움 
	function enterChat(me) {
		window.open(
			"http://localhost:8090/bit/Chat.do?roomNo="+$(me).val(),
			"chatRoom",
			"width=400, height=700"
		);
		
	};
	function writeMsg(evt){
		let html = evt.data;
		let username = evt.data.split("님");
		console.log(username);
		console.log($('#userid').val());
		if(username[0] != $('#userid').val()){
		let html = "<div id='slide'>"+evt.data+"</div>";
		console.log(html);
		$('#alarm').append(html);
		setTimeout(()=>{
		$('#alarm').empty();
			},3000)
		
		}
	};
	var websocket;
	function connect(){
		//0번방은 알람용
		websocket = new WebSocket('ws://localhost:8090/bit/chatting.do?roomNo=0');
		websocket.onopen =(evt) =>{
			};
	
	 	websocket.onclose =(evt) =>{
	 	}; 
	 	
		websocket.onmessage=(evt)=>{
			writeMsg(evt);
		}
	}; 
	
	function disconnect(){
		websocket.close();
	}
	
	
	
	$(function() {
	
		var maxroomno;
		connect();
	
		$('#open_chat_room').click(() => {
			console.log(maxroomno);
			$.ajax({
				url: "OpenChatRoom.do",
				data: { roomName: $('#roomName').val() },
				success: (data) => {
					let html = '<div class="row my-2" style="position:relative;">' +
					'<h3 class="my-auto mx-4"><i class="far fa-comments"></i></h3> <p class="my-auto mx-4">' + $('#roomName').val() +
					'</p><button class="mx-4 chat my-auto fas fa-door-open chatbtn" onclick="enterChat(this)" style="position:absolute; right:0px;"value=' + maxroomno +
					'></button></div>'
					$('#roomList').append(html);
					
					$('#roomName').val('');
					
				}
			});
		});
	
		$('#createRoom').click(() => {
			$('#inputdisplay').toggle();
		});
		
	
		//1. 모달창을 열면 DB에서 현재 개설돼 있는 채팅방 목록을 가져 옴 채팅방 들어가는 버튼에는 방 번호의 정보가 담겨 있음 (나중에 이름으로 해도 가능) 
		//2. 버튼을 누르면 enterChat(this) 함수가 실행되며 채팅방 팝업을 띄움 
		$('#chat').on("click", () => {
			$('#roomList').empty();
			$.ajax({
				url: "ChatRoomList.do",
				type: "POST",
				success: (data) => {
					$.each(data, (index, list) => {
						let html = '<div class="row my-2" style="position:relative;">' +
							'<h3 class="my-auto mx-4"><i class="far fa-comments"></i></h3> <p class="my-auto mx-4">' + list.roomName +
							'</p><button class="mx-4 chat my-auto fas fa-door-open chatbtn" onclick="enterChat(this)" style="position:absolute; right:0px;"value=' + list.roomNo +
							'></button></div>'
						$('#roomList').append(html);
						maxroomno = list.roomNo;
						console.log(maxroomno);
					});
				}
			});
		});
	
		
		$('#chatModal').on("hidden.bs.modal", () => {
			$('#inputdisplay').css('display', 'none');
		});
	
	
	
	
		$('#btn-msg-write').on("click", () => {
			//sendMessage();
	
		});
	
	})

