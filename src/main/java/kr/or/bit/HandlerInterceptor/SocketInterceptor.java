package kr.or.bit.HandlerInterceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class SocketInterceptor extends HttpSessionHandshakeInterceptor {

	//8. 여기서 소켓과 연결되기 전에 잡음
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		//이때 소켓을 연결하기위해 chatting.do에서 parameter 로 넘긴 roomNo 가 필요함 그 값을 echoHandler에 넘겨줘야
		// 이거를 가지고 방 안에 들어온 사람들만 관리할 수있음
		// 그걸 받을 수 있는 것은 HttpServletRequest 객체이다. (우리가 컨트롤러에서 쓰던 Request)
		//그래서 현재 이 메소드에 prarmeter로 있는 ServerHttpReqeust 를 HttpServeltReqeust 로 바꿔 줄거임
		//근데 바로 형변환 안됨 그래서 밑에같이 바꿔주는 작업을 하는거임
		
		ServletServerHttpRequest sshrequest = (ServletServerHttpRequest) request;
		HttpServletRequest hsrequest = sshrequest.getServletRequest();
		//이렇게 하면  hsrequest 에서 파라미터를 받을 수 있음
		
		System.out.println("Before HandShake");
		//그래서 현재 메소드의 attribute 에 넣어놓으면 Echohandler에서 사용가능 
		attributes.put("roomNo",hsrequest.getParameter("roomNo"));
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("HandShake Done");
		super.afterHandshake(request, response, wsHandler, ex);
	}
	
}


