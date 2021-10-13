package com.teamanime.Propra.Controller;


import java.util.List;

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

import com.teamanime.Propra.Entities.Admin;
import com.teamanime.Propra.Entities.Person;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Services.AdminService;
import com.teamanime.Propra.Services.PersonDetails;
import com.teamanime.Propra.Services.RoleService;

@Controller
public class AdminController {
		
	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleService roleService;
	
	
	
	
	
	//////////////////////////////	///////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
    ////////////////////////////////////////////////////////////////////
	///////

	
	@GetMapping	("/admins") 
	public String admins( Model model) {
	
		
		return "Pages/forAdmin/admins";
	}
	
	
	
	
	@GetMapping	("/admins/myArea") 
	public String myArea( Model model) {
	
		
		return "Pages/forAdmin/myArea/myMenu";
	}
	
	@GetMapping	("/admins/myArea/myProfile") 
	public String myProfile( Model model,@AuthenticationPrincipal PersonDetails userDetails ) {
		

        Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Admin admin=adminService.findAdmin(asker.getId());
		model.addAttribute("admin",admin);
		
		return "Pages/forAdmin/myArea/myProfile";
	}
	
	@GetMapping	("/admins/myArea/updateProfile") 
	public String myUpdateForm( Model model ,@AuthenticationPrincipal PersonDetails userDetails  ) {
		
		Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Admin admin=adminService.findAdmin(asker.getId());
		model.addAttribute("admin",admin);
		return "Pages/forAdmin/myArea/updateProfile";
	}
	
	
	@PostMapping ("/admins/myArea/updateProfile") 
    public String myUpdateSubmit(  @ModelAttribute @Valid Admin admin, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forAdmin/myArea/updateProfile";
		}
	
		///update the entity in the db
	 adminService.updateAdmin(admin);
	redAttr.addFlashAttribute("message", " changes applied!!");
		return "redirect:/admins/myArea/myProfile";

	}
	
	
	
	
	
	@GetMapping	("/admins/add") 
	public String tutorAddForm( Model model ,@RequestParam (name="failure", required=false) boolean failure  ) {
	
		model.addAttribute("admin", new Admin());
		model.addAttribute("failure", failure);
		
		return "Pages/forAdmin/adminForm";
	}
	
	
	@PostMapping ("/admins/add")
    public String tutorAddSubmit(  @ModelAttribute @Valid Admin admin, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forAdmin/adminForm";
		}
	
		//save the tutor in the db first
	Admin Saved =adminService.addAdmin(admin);
	redAttr.addAttribute("id", Saved.getId());
	redAttr.addFlashAttribute("message", "Admin successfully addded");
		return "redirect:/admins/view/{id}";

	}
	
	// for the view of the element after form submission
	@GetMapping	("/admins/view/{id}") 
	public String viewTutor( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		Admin admin = adminService.findAdmin(id);
		model.addAttribute("admin", admin);
		model.addAttribute("message", message);
		
		return "Pages/forAdmin/viewAdmin";
	}
	
	@GetMapping	("/admins/single") 
	public String view( Model model,@RequestParam(name="id", required=true) Long id ) {
			
		 		
		Admin admin = adminService.findAdmin(id);
		model.addAttribute("admin", admin);

		

		
		return "Pages/forAdmin/viewAdmin";
	}
	
	@GetMapping	("/admins/list") 
	public String listAdmins( Model model,  @ModelAttribute("message") String message  ) {
	
		List<Admin>adminList=adminService.listAdmins();
		model.addAttribute("adminList",adminList);
		model.addAttribute("message",message);
		
		return "Pages/forAdmin/listAdmins";
	}
	
	
	
	
	@GetMapping	("/admins/delete") 
	public String adminDelete( Model model, @RequestParam(name="id" ,required=true) Long id,RedirectAttributes redAttr) {
	
		if(id==null ||(!id.toString().matches("\\d+")))
		throw new PropraException("error on parameters");
		
	  adminService.removeAdmin(id);
	  redAttr.addFlashAttribute("message", "Admin successfully removed!!");
		return "redirect:/admins/list";
	}
	
	
	@GetMapping	("/admins/update") 
	public String adminUpdateForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("admin",   adminService.findAdmin(id));
		return "Pages/forAdmin/updateAdmin";
	}
	
	
	@PostMapping ("/admins/update") 
    public String adminUpdateSubmit(  @ModelAttribute @Valid Admin admin, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forAdmin/updateAdmin";
		}
	
		///update the entity in the db
	Admin Saved = adminService.updateAdmin(admin);
	redAttr.addAttribute("id", Saved.getId());
	redAttr.addFlashAttribute("message", "Admin successfully updated");
		return "redirect:/admins/view/{id}";

	}
	
	@GetMapping	("/admins/myRoles") 
	public String linkWithRoleForm( Model model ,@RequestParam (name="id", required=true) Long id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("admin",   adminService.findAdmin(id));
		model.addAttribute("listRoles",   roleService.listRoles());
		return "Pages/forAdmin/linkWithRoleForm";
	}
	
	
	@PostMapping ("/admins/myRoles") 
    public String linkWithRoleSubmit(  @ModelAttribute  Admin admin, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		//doesn't work as long as the @valid is not before admin
		if (bindingResult.hasErrors()) {
			return "Pages/forAdmin/linkWithRoleForm";
		}
		///update the entity in the db
	Admin adminSaved = adminService.linkWithRole(admin);
	redAttr.addAttribute("id", adminSaved.getId());
	redAttr.addFlashAttribute("message", "Admin successfully updated");
		return "redirect:/admins/view/{id}";

	}

	
	
	
}		