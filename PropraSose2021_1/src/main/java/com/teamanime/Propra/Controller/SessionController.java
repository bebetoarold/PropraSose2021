package com.teamanime.Propra.Controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Entities.Person;
import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Repository.SessionRepository;
import com.teamanime.Propra.Services.PersonDetails;
import com.teamanime.Propra.Services.SessionService;
import com.teamanime.Propra.Services.SubjectService;
import com.teamanime.Propra.Services.TutorService;

@Controller
public class SessionController {

	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private PersonRepository personRepository;
	
	
	/////////////////////////////////////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
	
	
    ////////////////////////////////////////////////////////////////////
	///////
	
	@GetMapping	("/sessions") 
	public String sessions( Model model) {
	
		
		return "Pages/forSession/sessions";
	}
	
	
	///////////////////////////////////////////////////////////
	///////////////////// SUBJECTS
	////////////////////////////////////////////////////////////
	@GetMapping	("/sessions/add") 
	public String sessionAddForm( Model model,@AuthenticationPrincipal PersonDetails userDetails ) {
	
		
		Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Tutor tutor=tutorService.findTutor(asker.getId());
		model.addAttribute("session",  new Session());
		model.addAttribute("subjectList",  tutor.getMySubjects());
		
		
		
		return "Pages/forSession/sessionForm";
	}
	
	
	@PostMapping ("/sessions/add") 
    public String sessionAddSubmit(  @ModelAttribute @Valid Session session, BindingResult bindingResult,RedirectAttributes redAttr
    		,@AuthenticationPrincipal PersonDetails userDetails) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSession/sessionForm";
		}
		
		Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Tutor tutor=tutorService.findTutor(asker.getId());
		
		session.setHolder(tutor);
	
		Session sessionSaved = sessionService.addSession(session);
	
	    redAttr.addAttribute("id", sessionSaved.getId());
	    redAttr.addFlashAttribute("message", "Session successfully created");
		return "redirect:/sessions/view/{id}";

	}
	
	// for the view of the element after form submission
	@GetMapping	("/sessions/view/{id}") 
	public String viewSession( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		    	if(id==null) {
					return "redirect:/sessions";
				}
		 		
		
		Session sessionItem = sessionService.findSession(id);
		model.addAttribute("sessionItem", sessionItem);
		model.addAttribute("message", message);
		
		return "Pages/forSession/viewSession";
	}
	
	@GetMapping	("/sessions/single") 
	public String view( Model model,@RequestParam(name="id", required=true) Long id ) {
			
				//findByid doesn't accept null parameter so the verification
				
		    	if(id==null || (!id.toString().matches("\\d+"))) {
		    		throw new PropraException("error on parameters");
				}
		 		
		Session sessionItem = sessionService.findSession(id);
		model.addAttribute("sessionItem", sessionItem);

		
		return "Pages/forSession/viewSession";
	}
	
	@GetMapping	("/sessions/list") 
	public String listSessions( Model model  ) {
	
		List<Session>sessionList=sessionService.listSessions();
		model.addAttribute("sessionList",sessionList);
		
		return "Pages/forSession/listSessions";
	}
	
	@GetMapping	("/sessions/mySessions") 
	public String listMySessions( Model model , @AuthenticationPrincipal PersonDetails userDetails) {
		
		
		Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		List<Session>sessionList=sessionRepository.findByHolderId(asker.getId());
		model.addAttribute("sessionList",sessionList);
		
		return "Pages/forSession/listMySessions";
	}
	
	
	
	@GetMapping	("/sessions/delete") 
	public String sessionDelete( Model model, @RequestParam(name="id" ,required=true) Long id) {
	
	  sessionService.removeSession(id);
		return "Pages/success";
	}
	
	
	@GetMapping	("/sessions/update") 
	public String sessionUpdateForm( Model model ,@RequestParam (name="id", required=true) Long id,@AuthenticationPrincipal PersonDetails userDetails  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Tutor tutor=tutorService.findTutor(asker.getId());
		model.addAttribute("session",   sessionService.findSession(id));
		model.addAttribute("subjectList",  tutor.getMySubjects());
		return "Pages/forSession/updateSession";
	}
	
	
	@PostMapping ("/sessions/update") 
    public String subjectUpdateSubmit(  @ModelAttribute @Valid Session session, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSession/updateSession";
		}
	
		///update the entity in the db
	Session sessionSaved = sessionService.updateSession(session);
	redAttr.addAttribute("id", sessionSaved.getId());
	redAttr.addFlashAttribute("message", "Session successfully updated");
		return "redirect:/sessions/view/{id}";

	}
	
	@GetMapping	("/sessions/confirm") 
	public String sessionConfirmForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		Session session=sessionService.findSession(id);
		model.addAttribute("session",   session);
		Set<Learner> listLearners=new HashSet<>();
		Subject theme=session.getSubject();
		for(Subject subject:session.getHolder().getMySubjects()) {
			
			if(subject.equals(theme)) {
				listLearners=subject.getLearning();
			}
		}
		model.addAttribute("listLearners",  listLearners);
		
		return "Pages/forSession/confirmSession";
	}
	
	
	@PostMapping ("/sessions/confirm") 
    public String sessionConfirmSubmit(  @ModelAttribute @Valid Session session, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSession/confirmSession";
		}
	
		///update the entity in the db
	Session sessionSaved = sessionService.confirmSession(session);
	redAttr.addAttribute("id", sessionSaved.getId());
	redAttr.addFlashAttribute("message", "Session successfully updated");
		return "redirect:/sessions/view/{id}";

	}
	
	
	
	
}		