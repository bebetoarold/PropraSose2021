package com.teamanime.Propra.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Admin;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.AdminRepository;

@Transactional
@Service
public class AdminService implements AdminInterface {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public Admin addAdmin(Admin admin) throws PropraException {
		// TODO Auto-generated method stub
		try {
		String pass=admin.getPassword();
		String passE=passwordEncoder.encode(pass);
		admin.setPassword(passE);
		admin.setPasswordConfirmation(passE);
			
		return adminRepository.save(admin);
	}catch(Exception e) {
		
		throw new PropraException(e);
	}
	
	}

	@Override
	public Admin findAdmin(Long id) throws PropraException {
		// TODO Auto-generated method stub
		
try {
		return  adminRepository.getById(id);	
			
		}catch(Exception e) {
			
			throw new PropraException(e);
		}

	}

	@Override
	public void removeAdmin(Long id) throws PropraException {
		// TODO Auto-generated method stub
try {
			
		adminRepository.deleteById(id);
			
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
		
	}

	@Override
	public Admin updateAdmin(Admin admin) throws PropraException {
		// TODO Auto-generated method stub
		
		try {
			Admin indb=  adminRepository.getById(admin.getId());
			indb.setFirstname(admin.getFirstname());
			indb.setLastname(admin.getLastname());
			indb.setContact(admin.getContact());
			indb.setMailAddress(admin.getMailAddress());
			String passE=passwordEncoder.encode(admin.getPassword());
			indb.setPassword(passE);
			indb.setPasswordConfirmation(passE);
			indb.setAddress(admin.getAddress());
			Admin updated=adminRepository.save(indb);
			
			return updated;
					
				}catch(Exception e) {
					
					throw new PropraException(e);
				}
			
			}

	@Override
	public List<Admin> listAdmins() throws PropraException {
		// TODO Auto-generated method stub
	
		
try {
			
			return adminRepository.findAll();
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
	}

	
	
	@Override
	public Admin linkWithRole(Admin admin) throws PropraException {
		// TODO Auto-generated method stub

		try {
			Admin indb= adminRepository.getById(admin.getId()) ;
			indb.setRoles(admin.getRoles());
			return adminRepository.save(indb);
			
			
		}catch(Exception e) {
			throw new PropraException(e);
		}
	}
	


}
