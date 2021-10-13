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

import com.teamanime.Propra.Entities.Person;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Services.PersonDetails;
import com.teamanime.Propra.Services.RoleService;
import com.teamanime.Propra.Services.SubjectService;
import com.teamanime.Propra.Services.TutorService;
import com.teamanime.Propra.Utilitary.FindObject;
import com.teamanime.Propra.Utilitary.SearchForm;

@Controller
public class TutorController {
		
	
	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PersonRepository personRepository;
	
	//////////////////////////////	///////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
    ////////////////////////////////////////////////////////////////////
	///////
	@GetMapping	("/tutors") 
	public String tutors( Model model) {
	
		
		return "Pages/forTutor/tutors";
	}
	

	
	@GetMapping	("/tutors/myArea") 
	public String myArea( Model model) {
	
		
		return "Pages/forTutor/myArea/myMenu";
	}
	
	
	
	@GetMapping	("/tutors/myArea/myProfile") 
	public String myProfile( Model model,@AuthenticationPrincipal PersonDetails userDetails ) {
		
        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Tutor tutor=tutorService.findTutor(asker.getId());
		model.addAttribute("tutor",tutor);
		
		return "Pages/forTutor/myArea/myProfile";
	}
	
	@GetMapping	("/tutors/myArea/updateProfile") 
	public String myUpdateForm( Model model ,@AuthenticationPrincipal PersonDetails userDetails ) {
		

        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Tutor tutor=tutorService.findTutor(asker.getId());
		model.addAttribute("tutor",tutor);
		return "Pages/forTutor/myArea/updateProfile";
	}
	
	
	@PostMapping ("/tutors/myArea/updateProfile") 
    public String myUpdateSubmit(  @ModelAttribute @Valid Tutor tutor, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forTutor/myArea/updateProfile";
		}
	
		///update the entity in the db
	 tutorService.updateTutor(tutor);
	redAttr.addFlashAttribute("message", "Changes successfully made");
		return "redirect:/tutors/myArea/myProfile";

	}
	
	@GetMapping	("/tutors/myArea/link") 
	public String myLinkForm( Model model ,@AuthenticationPrincipal PersonDetails userDetails ) {
		
        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Tutor tutor=tutorService.findTutor(asker.getId());
		model.addAttribute("tutor",tutor);
		model.addAttribute("listSubjects",   subjectService.listSubjects());
		return "Pages/forTutor/myArea/mySubjects";
	}
	
	
	@PostMapping ("/tutors/myArea/link") 
    public String myLinkSubmit(  @ModelAttribute  Tutor tutor, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forTutor/myArea/mySubjects";
		}
		///update the entity in the db
	 tutorService.linkWithSubject(tutor);
	redAttr.addFlashAttribute("message", "Tutor successfully linked with the subjects");
		return "redirect:/tutors/myArea/myProfile";

	}
	
	
	
	@GetMapping	("/tutors/add") 
	public String tutorAddForm( Model model ,@RequestParam (name="failure", required=false) boolean failure  ) {
	
		model.addAttribute("tutor", new Tutor());
		model.addAttribute("failure", failure);
		
		return "Pages/forTutor/tutorForm";
	}
	
	
	@PostMapping ("/tutors/add")
    public String tutorAddSubmit(  @ModelAttribute @Valid Tutor tutor, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forTutor/tutorForm";
		}
	
		//save the tutor in the db first
	Tutor tutorSaved = tutorService.addTutor(tutor);
	redAttr.addAttribute("id", tutorSaved.getId());
	redAttr.addFlashAttribute("message", "Tutor successfully addded");
		return "redirect:/tutors/view/{id}";

	}
	
	// for the view of the element after form submission
	@GetMapping	("/tutors/view/{id}") 
	public String viewTutor( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		Tutor tutor = tutorService.findTutor(id);
		model.addAttribute("tutor", tutor);
		model.addAttribute("message", message);
		
		return "Pages/forTutor/viewTutor";
	}
	
	@GetMapping	("/tutors/single") 
	public String view( Model model,@RequestParam(name="id", required=true) Long id ) {
			
		 		
		Tutor tutor = tutorService.findTutor(id);
		model.addAttribute("tutor", tutor);

		
		return "Pages/forTutor/viewTutor";
	}
	
	@GetMapping	("/tutors/list") 
	public String listTutor( Model model,  @ModelAttribute("message") String message  ) {
	
		List<Tutor>tutorList=tutorService.listTutors();
		model.addAttribute("tutorList",tutorList);
		model.addAttribute("message",message);
		
		return "Pages/forTutor/listTutors";
	}
	
	
	@GetMapping	("/tutors/find") 
	public String tutorfindForm( Model model ) {
	
		return "Pages/forTutor/findTutor";
	}
	
	
	@PostMapping ("/tutors/find")
    public String tutorFind(  HttpServletRequest request,Model model) {
		
		SearchForm sf=new SearchForm();
		FindObject toFind=sf.initializeSearch(request);
	List<Tutor> found= tutorService.findTutors(toFind);
	model.addAttribute("listFound", found);
	return "Pages/forTutor/findTutor";
	

	}
	
	@GetMapping	("/tutors/delete") 
	public String tutorDelete( Model model, @RequestParam(name="id" ,required=true) Long id,RedirectAttributes redAttr) {
	
		if(id==null ||(!id.toString().matches("\\d+")))
		throw new PropraException("error on parameters");
		
	  tutorService.removeTutor(id);
	  redAttr.addFlashAttribute("message", "Tutor successfully removed!!");
		return "redirect:/tutors/list";
	}
	
	
	@GetMapping	("/tutors/update") 
	public String tutorUpdateForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("tutor",   tutorService.findTutor(id));
		return "Pages/forTutor/updateTutor";
	}
	
	
	@PostMapping ("/tutors/update") 
    public String tutorUpdateSubmit(  @ModelAttribute @Valid Tutor tutor, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forTutor/updateTutor";
		}
	
		///update the entity in the db
	Tutor tutorSaved = tutorService.updateTutor(tutor);
	redAttr.addAttribute("id", tutorSaved.getId());
	redAttr.addFlashAttribute("message", "Tutor successfully updated");
		return "redirect:/tutors/view/{id}";

	}
	
	

	@GetMapping	("/tutors/link") 
	public String linkForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("tutor",   tutorService.findTutor(id));
		model.addAttribute("listSubjects",   subjectService.listSubjects());
		return "Pages/forTutor/linkingForm";
	}
	
	
	@PostMapping ("/tutors/link") 
    public String linkSubmit(  @ModelAttribute  Tutor tutor, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forTutor/linkingForm";
		}
		///update the entity in the db
	Tutor tutorSaved = tutorService.linkWithSubject(tutor);
	redAttr.addAttribute("id", tutorSaved.getId());
	redAttr.addFlashAttribute("message", "Tutor successfully updated");
		return "redirect:/tutors/view/{id}";

	}
	
	@GetMapping	("/tutors/myRoles") 
	public String linkWithRoleForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("tutor",   tutorService.findTutor(id));
		model.addAttribute("listRoles",   roleService.listRoles());
		return "Pages/forTutor/linkWithRoleForm";
	}
	
	
	@PostMapping ("/tutors/myRoles") 
    public String linkWithRoleSubmit(  @ModelAttribute  Tutor tutor, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forTutor/linkWithRoleForm";
		}
		///update the entity in the db
	Tutor tutorSaved = tutorService.linkWithRole(tutor);
	redAttr.addAttribute("id", tutorSaved.getId());
	redAttr.addFlashAttribute("message", "Tutor successfully updated");
		return "redirect:/tutors/view/{id}";

	}
	
	
	
	
}		