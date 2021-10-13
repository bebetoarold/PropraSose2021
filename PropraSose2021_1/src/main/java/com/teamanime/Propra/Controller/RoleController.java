package com.teamanime.Propra.Controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamanime.Propra.Entities.Role;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Services.RoleService;

@Controller
public class RoleController {

	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	@Autowired
	private RoleService roleService;
	
	
	/////////////////////////////////////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
	
	
    ////////////////////////////////////////////////////////////////////
	///////
	
	@GetMapping	("/roles") 
	public String subjects( Model model) {
	
		
		return "Pages/forRole/roles";
	}
	
	
	///////////////////////////////////////////////////////////
	///////////////////// ROLES
	////////////////////////////////////////////////////////////
	@GetMapping	("/roles/add") 
	public String roleAddForm( Model model ,@RequestParam (name="failure", required=false) boolean failure  ) {
	
		model.addAttribute("role",  new Role());
		model.addAttribute("failure", failure);
		
		return "Pages/forRole/roleForm";
	}
	
	
	@PostMapping ("/roles/add") 
    public String roleAddSubmit(  @ModelAttribute @Valid Role role, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forRole/roleForm";
		}
	
		//save the tutor in the db first
	Role roleSaved = roleService.addRole(role);
	
	redAttr.addFlashAttribute("message", "Subject successfully addded");
		return "redirect:/roles/list";

	}
	

	
	
	@GetMapping	("/roles/list") 
	public String listRoles( Model model  ) {
	
		List<Role>roleList=roleService.listRoles();
		model.addAttribute("roleList",roleList);
		
		return "Pages/forRole/listRoles";
	}
	
	
	
	
	@GetMapping	("/roles/delete") 
	public String roleDelete( Model model, @RequestParam(name="id" ,required=true) Integer id) {
	
	  roleService.removeRole(id);
		return "Pages/success";
	}
	
	
	@GetMapping	("/roles/update") 
	public String roleUpdateForm( Model model ,@RequestParam (name="id", required=true) Integer id  ) {
		
		if(id==null ||(!id.toString().matches("\\d+")))
			throw new PropraException("error on parameters");
		
		model.addAttribute("role",   roleService.findRole(id));
		return "Pages/forRole/updateRole";
	}
	
	
	/**
	 * @param subject
	 * @param bindingResult
	 * @param redAttr
	 * @return
	 * the redirection message is not handled in the GetMapping of the route /roles/list
	 */
	@PostMapping ("/roles/update") 
    public String subjectUpdateSubmit(  @ModelAttribute @Valid Role role, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forRole/updateRole";
		}
	
		///update the entity in the db
	Role roleSaved = roleService.updateRole(role);
	redAttr.addFlashAttribute("message", "Role successfully updated");
		return "redirect:/roles/list";

	}
	
	
}		