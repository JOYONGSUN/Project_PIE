package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.dto.Room;
import kr.or.bit.dto.user;

public interface ChatDao {
	
	public List<Room> chatRoomList();
	public void openChatRoom(String roomName);
	
	 //메서드 정의
	 //CRUD 기반
	 int insertRoom(Room room);

	 /*
	 RoomDto getRoom(int roomno);*/
	 List<Room> getRoomList();
	 
	 user loginUser(String email,String pwd);
	 int joinUser(user user);
}
