package com.teamanime.Propra.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Services.SubjectService;
import com.teamanime.Propra.Utilitary.FindObject;
import com.teamanime.Propra.Utilitary.SearchForm;
import com.teamanime.Propra.Validations.ValidationErrorResponse;
import com.teamanime.Propra.Validations.Violation;

@Controller
public class SubjectController {

	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private SubjectService subjectService;
	
	
	/////////////////////////////////////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
	
	
    ////////////////////////////////////////////////////////////////////
	///////
	
	@GetMapping	("/subjects") 
	public String subjects( Model model) {
	
		
		return "Pages/forSubject/subjects";
	}
	
	
	///////////////////////////////////////////////////////////
	///////////////////// SUBJECTS
	////////////////////////////////////////////////////////////
	@GetMapping	("/subjects/add") 
	public String subjectAddForm( Model model ,@RequestParam (name="failure", required=false) boolean failure  ) {
	
		model.addAttribute("subject",  new Subject());
		model.addAttribute("failure", failure);
		
		return "Pages/forSubject/subjectForm";
	}
	
	
	@PostMapping ("/subjects/add") 
    public String subjectAddSubmit(  @ModelAttribute @Valid Subject subject, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSubject/subjectForm";
		}
	
		//save the tutor in the db first
	Subject subjectSaved = subjectService.addSubject(subject);
	
	redAttr.addAttribute("id", subjectSaved.getId());
	redAttr.addFlashAttribute("message", "Subject successfully addded");
		return "redirect:/subjects/view/{id}";

	}
	
	// for the view of the element after form submission
	@GetMapping	("/subjects/view/{id}") 
	public String viewSubject( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		    	if(id==null) {
					return "redirect:/subjects";
				}
		 		
		
		Subject subject = subjectService.findSubject(id);
		model.addAttribute("subject", subject);
		model.addAttribute("message", message);
		
		return "Pages/forSubject/viewSubject";
	}
	
	@GetMapping	("/subjects/single") 
	public String view( Model model,@RequestParam(name="id", required=true) Long id ) {
			
				//findByid doesn't accept null parameter so the verification
				
		    	if(id==null || (!id.toString().matches("\\d+"))) {
		    		throw new PropraException("error on parameters");
				}
		 		
		Subject subject = subjectService.findSubject(id);
		model.addAttribute("subject", subject);

		
		return "Pages/forSubject/viewSubject";
	}
	
	@GetMapping	("/subjects/list") 
	public String listTutor( Model model  ) {
	
		List<Subject>subjectList=subjectService.listSubjects();
		model.addAttribute("subjectList",subjectList);
		
		return "Pages/forSubject/listSubjects";
	}
	
	
	@GetMapping	("/subjects/find") 
	public String subjectfindForm( Model model ) {
	
		return "Pages/forSubject/findSubject";
	}
	
	
	@PostMapping ("/subjects/find")
    public String subjectFind(  HttpServletRequest request,Model model) {
		
		SearchForm sf=new SearchForm();
		FindObject toFind=sf.initializeSearch(request);
	List<Subject> found= subjectService.findSubjects(toFind);
	model.addAttribute("listFound", found);
	return "Pages/forSubject/findSubject";
	

	}
	
	@GetMapping	("/subjects/delete") 
	public String subjectdelete( Model model, @RequestParam(name="id" ,required=true) Long id) {
	
	  subjectService.removeSubject(id);
		return "Pages/success";
	}
	
	
	@GetMapping	("/subjects/update") 
	public String subjectUpdateForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("subject",   subjectService.findSubject(id));
		return "Pages/forSubject/updateSubject";
	}
	
	
	@PostMapping ("/subjects/update") 
    public String subjectUpdateSubmit(  @ModelAttribute @Valid Subject subject, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSubject/updateSubject";
		}
	
		///update the entity in the db
	Subject subjectSaved = subjectService.updateSubject(subject);
	redAttr.addAttribute("id", subjectSaved.getId());
	redAttr.addFlashAttribute("message", "Subject successfully updated");
		return "redirect:/subjects/view/{id}";

	}
	
	
	
}		