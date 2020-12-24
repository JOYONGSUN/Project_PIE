package kr.or.bit.Socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EchoHandler extends TextWebSocketHandler {
	//	웹소켓 세션을 저장할 리스트 생성
	private Map<String, HashMap<String, WebSocketSession>> sessionList = new HashMap();
	// Map<1번방, HashMap<id, session>>
	// Map<2번방, HashMap<id, session>> 이런식으로 저장 할거임
	List<WebSocketSession> socketList = new ArrayList<WebSocketSession>();
	//로그인 한 사람들에게 뿌리는 용

	//이건 그냥 로그 찍는거임
	private Logger logger = (Logger) LoggerFactory.getLogger(EchoHandler.class);

	//파일 전송시 

	//9. 인터셉터에서 넣어놓은 채팅방 번호 갖고오기  인터셉터에서의 attribute 가 Map 객체였음
	public String getRoomNo(WebSocketSession session) {
		Map<String, Object> map = session.getAttributes();
		String roomNo = (String) map.get("roomNo");
		return roomNo;
	}

	//10. 클라이언트와 연결이 되고 난 후
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		// TODO Auto-generated method stub
		logger.info(session.getId() + "님 접속 ");
		//입장한 채팅방 번호 꺼내와서 저장 

		try {
			String roomNo = getRoomNo(session);
			System.out.println(roomNo + " : 방번호 ");

			//제일 큰 Map에 서 방 번호가 일치하는게 있는지 확인
			if (sessionList.containsKey(roomNo)) {
				//있는 방이라면 제일 큰 Map 에 session id 와 session을 넣어줌
				System.out.println("있는방 번호 : " + roomNo);
				sessionList.get(roomNo).put(session.getId(), session);
			} else {
				//없는 방이라면 새로운 방을 만들고 거기에다가 session id  와 session을 넣어줌
				System.out.println("없는 방번호 : " + roomNo);
				HashMap<String, WebSocketSession> list = new HashMap<String, WebSocketSession>();
				list.put(session.getId(), session);
				sessionList.put(roomNo, list);
			}

			//그리고 현재 방에 접속해 있는 사람들에게만 누가 입장했다고 뿌려줌
			if (!roomNo.equals("0")) {
				for (Map.Entry list : sessionList.get(roomNo).entrySet()) {
					WebSocketSession sess = (WebSocketSession) list.getValue();
					sess.sendMessage(new TextMessage("-----" + session.getPrincipal().getName() + "님이 입장하셨습니다.----"));
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	//13. 클라이언트가 웹소켓 서버로 메시지를 보내면 TextMessage 객체안에 저장됨 그거를 getPlayload() 함수를 통해서 문자열로 받아 볼 수 있음.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		String roomNo = getRoomNo(session);
		System.out.println(message);

		if (!roomNo.equals("0")) {
			String msg = session.getPrincipal().getName() + "|" + message.getPayload();

			// 현재 같은 채팅방에 들어와 있는 사람들 한테만 뿌려줌
			for (Map.Entry list : sessionList.get(roomNo).entrySet()) {
				WebSocketSession sess = (WebSocketSession) list.getValue();
				sess.sendMessage(new TextMessage(msg));
			}
			for (Map.Entry list : sessionList.get("0").entrySet()) {
				WebSocketSession sess = (WebSocketSession) list.getValue();
				sess.sendMessage(
						new TextMessage(session.getPrincipal().getName() + "님 께서 " + roomNo + "번 방에서 채팅 중입니다."));
			}
		}

	}

	//파일 전송
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		// TODO Auto-generated method stub
		try {
			//기본 버퍼의 크기는 [8192] 임 그래서 그거보다 큰거보내면 터짐 
			//그래서 web.xml에가서 바꿈 
			ByteBuffer byteBuffer = message.getPayload();
			String roomNo = getRoomNo(session);
			System.out.println(byteBuffer);
			if (!roomNo.equals("0")) {
				for (Map.Entry list : sessionList.get(roomNo).entrySet()) {
					WebSocketSession sess = (WebSocketSession) list.getValue();
					try {
						sess.sendMessage(new BinaryMessage(byteBuffer));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
			//이걸 넣으니까 되는데.... 왜 되는거지???
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	//15. 클라이언트와 연결이 끊어진 경우 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		logger.info(session.getId() + "님 접속 종료");
		String roomNo = getRoomNo(session);
		//연결이 끊어지면 그 방번호에 세션아이디 값을 제거해주고 세션 닫음
		sessionList.get(roomNo).remove(session.getId());
		session.close();

		//그리고 남아있는 사람들에게 다시 퇴장 했다는 메시지 보냄
		if (!roomNo.equals("0")) {
			for (Map.Entry list : sessionList.get(roomNo).entrySet()) {
				WebSocketSession sess = (WebSocketSession) list.getValue();
				sess.sendMessage(new TextMessage(session.getPrincipal().getName() + "님이 퇴장하셨습니다."));
			}
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		super.handleTransportError(session, exception);
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}

	// json형태의 문자열을 파라미터로 받아서 SimpleJson의 파서를 활용하여 JSONObject로 파싱처리를 해주는 함수
	private static JSONObject JsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
