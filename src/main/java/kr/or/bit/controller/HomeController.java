package kr.or.bit.controller;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//index
	@RequestMapping(value = "index.htm", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
			return "user/logIn";
	}
	
	//로그인 성공 후 projectList 페이지로 이동 
	@RequestMapping(value = "projectList.do", method = RequestMethod.GET)
	public String projectList() {
		return "projectList";
	}
	
	//회원가입 페이지로 이동 
	@RequestMapping(value = "join.do", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	//비밀번호 찾기 페이지로 이동
	@RequestMapping(value = "findPassword.do", method = RequestMethod.GET)
	public String findPassword() {
		return "user/findPassword";
	}
	
	@RequestMapping(value = "chatWebSocket.htm", method = RequestMethod.GET)
	public String chatLogin() {
		return "login";
	}
}
