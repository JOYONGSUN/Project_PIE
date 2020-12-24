package kr.or.bit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit.dao.ChatDao;
import kr.or.bit.dto.Room;
import kr.or.bit.dto.user;
import kr.or.bit.service.ChatService;

@Controller
public class ChatController {
	
	
	@Autowired
	private SqlSession sqlsession;

//	@RequestMapping("/index.htm")
//	public String login() {
		/*
		RoomDAO roomdao = sqlsession.getMapper(RoomDAO.class);
		List<RoomDto> list = roomdao.getRoomList();
		model.addAttribute("list", list);
		*/
//		return "login";
//	}
	
	@RequestMapping(value="/join.htm" , method=RequestMethod.GET)
	public String join() {
		/*
		RoomDAO roomdao = sqlsession.getMapper(RoomDAO.class);
		List<RoomDto> list = roomdao.getRoomList();
		model.addAttribute("list", list);
		*/
		return "join";
	}
	@RequestMapping(value="/join.htm" , method=RequestMethod.POST)
	public String join2(user user) {

		ChatDao chatdao = sqlsession.getMapper(ChatDao.class);
		int c = chatdao.joinUser(user);
		String url =null;
		if(c==0) {
			url = "join";
		}else {
			url = "login";
		}
		
		return url;
	}
	
	@RequestMapping("/main.htm")
	public String main(Model model) {

		ChatDao roomdao = sqlsession.getMapper(ChatDao.class);
		List<Room> list = roomdao.getRoomList();
		model.addAttribute("list", list);
		return "main";
	}
	
	@RequestMapping("chat.htm")
	public String chat(String roomname, String select, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.getAttribute("nickname");
		
		
		model.addAttribute("id", session.getAttribute("nickname"));
		model.addAttribute("select", select);
		model.addAttribute("roomname", roomname);
		return "chat";
	}
	
	@RequestMapping("room.htm")
	public String insertroom(Room Room, Model model) {
		/*model.addAttribute("id", id);
		model.addAttribute("select", select);*/
		
		ChatDao roomdao = sqlsession.getMapper(ChatDao.class);
		roomdao.insertRoom(Room);
		
		return "redirect:/main.htm";
	}
	
	@RequestMapping("login.htm")
	public String getLogin(String email,String pwd, Model model,HttpServletRequest request) {
		System.out.println("로그인처리");
		ChatDao roomdao = sqlsession.getMapper(ChatDao.class);
		/*emp e = empservice.getLogin(id,pw);*/
		user user = roomdao.loginUser(email, pwd);
		String result="";
		if(user !=null) {
			HttpSession session = request.getSession();
			session.setAttribute("nickname", user.getEmail());
			System.out.println(user.getEmail());
			System.out.println("get:"+session.getAttribute("nickname"));
			result="redirect:main.htm";
		}else {
			result="login";
		}
		return result;
	}
	
	/*@RequestMapping("room.htm")
	public String getroom(Model model) {
		model.addAttribute("id", id);
		model.addAttribute("select", select);
		
		RoomDAO roomdao = sqlsession.getMapper(RoomDAO.class);
		roomdao.getRoomList();
		
		return "chat";
	}*/
	
}
