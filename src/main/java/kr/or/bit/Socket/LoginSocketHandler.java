package kr.or.bit.Socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class LoginSocketHandler extends TextWebSocketHandler {

	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println("로그인 했어요");
		System.out.println("session.getId(): " + session.getId());
		SessionMaps.getAlarmusers().put(session.getId(), session); 
		super.afterConnectionEstablished(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		super.handleMessage(session, message);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		SessionMaps.getAlarmusers().remove(session.getId());
		super.afterConnectionClosed(session, status);
	}

}
