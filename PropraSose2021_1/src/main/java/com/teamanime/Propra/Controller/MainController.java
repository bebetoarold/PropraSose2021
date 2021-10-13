package com.teamanime.Propra.Controller;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teamanime.Propra.Repository.AdminRepository;
import com.teamanime.Propra.Services.SubjectService;
import com.teamanime.Propra.Services.TutorService;

@Controller
public class MainController {

	private static String SESSION_USER="session_user";
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private AdminRepository personRepository;
	
	
	//////////////////////////////	///////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
	@GetMapping	("/") 
	public String home(HttpServletRequest request, Principal principal) {
		
		
		HttpSession session=request.getSession();
		session.setAttribute(SESSION_USER, session);
		return "Pages/home";
	}
	
	@GetMapping	("/login") 
	public String login(@RequestParam(name = "loginError",required=false) boolean loginError, Model model) {
		System.err.println("LE VRAI DEBUT");
		model.addAttribute("loginError", loginError);
		return "Pages/login";
	}
	
	@GetMapping	("/logout") 
	public String logout(HttpServletRequest request, Model model) {
		
		request.getSession().invalidate();
        request.setAttribute(SESSION_USER,null);
		return "redirect:/login";
	}
	
	@GetMapping	("/403") 
	public String denied() {
	
		return "Pages/403";
	}
	
	
}
	
    