package kr.or.bit.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ChatDao;
import kr.or.bit.dto.Room;

@Service
public class ChatService {
	
	@Autowired
	private SqlSession sqlsession;
	
	public List<Room> chatRoomList() {
		ChatDao dao = sqlsession.getMapper(ChatDao.class);
		List<Room> roomList = dao.chatRoomList();
		return roomList;
	}
   public void openChatRoom(String roomname) {
	   ChatDao dao = sqlsession.getMapper(ChatDao.class);
		dao.openChatRoom(roomname);
   }
}
