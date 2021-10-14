package com.teamanime.Propra.Config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.teamanime.Propra.Entities.Admin;
import com.teamanime.Propra.Entities.Role;
import com.teamanime.Propra.Repository.AdminRepository;
import com.teamanime.Propra.Repository.RoleRepository;
import com.teamanime.Propra.Services.AdminService;
import com.teamanime.Propra.Services.RoleService;

@Component
public class UserDataLoader implements CommandLineRunner {
	
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	RoleService roleService;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		loadAdmin();
		
	}	
	
	public void loadAdmin() {
		
		if(adminRepository.count()==0) {
			
			Admin admin =new Admin();
			admin.setMailAddress("admin@gmail.com");
			admin.setFirstname("admin");
			admin.setPassword("admin");
			admin.setLastname("admin_lastname");
			admin.setBankData("DE12345678910121314151");
			admin.setContact("004917669417431");
			
			Admin res1=adminService.addAdmin(admin);
			
			Role role=new Role();
			role.setName("ADMIN");
			
			Role res2= roleService.addRole(role);
			List<Role> listR=new ArrayList<>();
			listR.add(res2);
			
			res1.setRoles(listR);
			adminService.linkWithRole(res1);
			
			System.err.println("OPERATION DONE FOR DEFAULT ENTRY IN DB");
		}
		
	}

}
