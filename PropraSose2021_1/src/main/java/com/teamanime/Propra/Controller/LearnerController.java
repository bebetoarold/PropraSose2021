package com.teamanime.Propra.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Repository.SessionRepository;
import com.teamanime.Propra.Services.LearnerService;
import com.teamanime.Propra.Services.PersonDetails;
import com.teamanime.Propra.Services.RoleService;
import com.teamanime.Propra.Services.SubjectService;
import com.teamanime.Propra.Utilitary.FindObject;
import com.teamanime.Propra.Utilitary.SearchForm;

@Controller
public class LearnerController {
		
	
	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private LearnerService learnerService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	
	//////////////////////////////	///////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
    ////////////////////////////////////////////////////////////////////
	///////
	
	@GetMapping	("/learners") 
	public String learners( Model model) {
	
		
		return "Pages/forLearner/learners";
	}
	
	

	@GetMapping	("/learners/add") 
	public String learnerAddForm( Model model ,@RequestParam (name="failure", required=false) boolean failure  ) {
	
		model.addAttribute("learner", new Learner());
		model.addAttribute("failure", failure);
		
		return "Pages/forLearner/learnerForm";
	}
	
	
	@PostMapping ("/learners/add")
    public String learnerAddSubmit(  @ModelAttribute @Valid Learner learner, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forLearner/learnerForm";
		}
	
		//save the tutor in the db first
	Learner learnerSaved = learnerService.addLearner(learner);
	redAttr.addAttribute("id", learnerSaved.getId());
	redAttr.addFlashAttribute("message", "Learner successfully addded");
		return "redirect:/learners/view/{id}";

	}
	
	// for the view of the element after form submission
	@GetMapping	("/learners/view/{id}") 
	public String viewLearner( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		Learner learner= learnerService.findLearner(id);
		model.addAttribute("learner", learner);
		model.addAttribute("message", message);
		
		return "Pages/forlearner/viewLearner";
	}
	
	@GetMapping	("/learners/single") 
	public String view( Model model,@RequestParam(name="id", required=true) Long id ) {
			
		 		
		Learner  learner= learnerService.findLearner(id);
		model.addAttribute("learner", learner);

		
		return "Pages/forLearner/viewLearner";
	}
	
	@GetMapping	("/learners/list") 
	public String listLearners( Model model,  @ModelAttribute("message") String message  ) {
	
		List<Learner>learnerList=learnerService.listLearners();
		model.addAttribute("learnerList",learnerList);
		model.addAttribute("message",message);
		
		return "Pages/forLearner/listLearners";
	}
	
	
	@GetMapping	("/learners/find") 
	public String learnerFindForm( Model model ) {
	
		return "Pages/forLearner/findLearner";
	}
	
	
	@PostMapping ("/learners/find")
    public String tutorFind(  HttpServletRequest request,Model model) {
		
		SearchForm sf=new SearchForm();
		FindObject toFind=sf.initializeSearch(request);
	List<Learner> found= learnerService.findLearners(toFind);
	model.addAttribute("listFound", found);
	return "Pages/forLearner/findLearner";
	

	}
	
	@GetMapping	("/learners/delete") 
	public String learnersDelete( Model model, @RequestParam(name="id" ,required=true) Long id,RedirectAttributes redAttr) {
	
		if(id==null ||(!id.toString().matches("\\d+")))
		throw new PropraException("error on parameters");
		
	  learnerService.removeLearner(id);
	  redAttr.addFlashAttribute("message", "Learner successfully removed!!");
		return "redirect:/learners/list";
	}
	
	
	@GetMapping	("/learners/update") 
	public String learnerUpdateForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("learner",   learnerService.findLearner(id));
		return "Pages/forLearner/updateLearner";
	}
	
	
	@PostMapping ("/learners/update") 
    public String learnerUpdateSubmit(  @ModelAttribute @Valid Learner learner, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forLearner/updateLearner";
		}
	
		///update the entity in the db
     Learner learnerSaved = learnerService.updateLearner(learner);
	redAttr.addAttribute("id", learnerSaved.getId());
	redAttr.addFlashAttribute("message", "Learner successfully updated");
		return "redirect:/learners/view/{id}";

	}
	
	

	@GetMapping	("/learners/link") 
	public String linkForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("learner",   learnerService.findLearner(id));
		model.addAttribute("listSubjects",   subjectService.listSubjects());
		return "Pages/forLearner/linkingForm";
	}
	
	
	@PostMapping ("/learners/link") 
    public String linkSubmit(  @ModelAttribute  Learner learner, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forLearner/linkingForm";
		}
		///update the entity in the db
	Learner learnerSaved = learnerService.linkWithSubject(learner);
	redAttr.addAttribute("id", learnerSaved.getId());
	redAttr.addFlashAttribute("message", "Learner successfully updated");
		return "redirect:/learners/view/{id}";

	}
	
	@GetMapping	("/learners/myRoles") 
	public String linkWithRoleForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("learner",   learnerService.findLearner(id));
		model.addAttribute("listRoles",   roleService.listRoles());
		return "Pages/forLearner/linkWithRoleForm";
	}
	
	
	@PostMapping ("/learners/myRoles") 
    public String linkWithRoleSubmit(  @ModelAttribute  Learner learner, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forLearner/linkWithRoleForm";
		}
		///update the entity in the db
		Learner learnerSaved = learnerService.linkWithRole(learner);
	redAttr.addAttribute("id", learnerSaved.getId());
	redAttr.addFlashAttribute("message", "Learner successfully updated");
		return "redirect:/learners/view/{id}";

	}
	
	/*
	 * 
	 * private area of the students
	 */
	
	@GetMapping	("/learners/myArea") 
	public String myArea( Model model) {
	
		
		return "Pages/forLearner/myArea/myMenu";
	}
	
	@GetMapping	("/learners/mySessions") 
	public String mySessions( Model model,@AuthenticationPrincipal PersonDetails userDetails ) {
		
        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Learner learner=learnerService.findLearner(asker.getId());
		model.addAttribute("sessionList",sessionRepository.findByParticipantsId(learner.getId()));
		
		return "Pages/forSession/studentSessions";
	}
	
	@GetMapping	("/learners/myArea/myProfile") 
	public String myProfile( Model model,@AuthenticationPrincipal PersonDetails userDetails ) {
		
        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Learner learner=learnerService.findLearner(asker.getId());
		model.addAttribute("learner",learner);
		
		return "Pages/forLearner/myArea/myProfile";
	}
	
	@GetMapping	("/learners/myArea/updateProfile") 
	public String myUpdateForm( Model model ,@AuthenticationPrincipal PersonDetails userDetails ) {
		
        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Learner learner=learnerService.findLearner(asker.getId());
		model.addAttribute("learner",learner);
		return "Pages/forLearner/myArea/updateProfile";
	}
	
	
	@PostMapping ("/learners/myArea/updateProfile") 
    public String myUpdateSubmit(  @ModelAttribute @Valid Learner learner, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forLearner/myArea/updateProfile";
		}
	
		///update the entity in the db
		learnerService.updateLearner(learner);
	redAttr.addFlashAttribute("message", "changes successfully applied");
		return "redirect:/learners/myArea/myProfile";

	}
	
	@GetMapping	("/learners/myArea/link") 
	public String myLinkForm( Model model,@AuthenticationPrincipal PersonDetails userDetails ) {
		
        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Learner learner=learnerService.findLearner(asker.getId());
		model.addAttribute("learner",learner);
		model.addAttribute("listSubjects",   subjectService.listSubjects());
		return "Pages/forLearner/myArea/mySubjects";
	}
	
	
	@PostMapping ("/learners/myArea/link") 
    public String myLinkSubmit(  @ModelAttribute  Learner learner, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forLearner/myArea/mySubjects";
		}
		///update the entity in the db
		learnerService.linkWithSubject(learner);
	redAttr.addFlashAttribute("message", "Learner successfully linked with subjects");
		return "redirect:/learners/myArea/myProfile";

	}
	
	
	
	
}		