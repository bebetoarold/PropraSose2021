package com.teamanime.Propra.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamanime.Propra.Entities.Role;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.RoleRepository;

@Transactional
@Service
public class RoleService implements RoleInterface {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> listRoles() throws PropraException {
		
		// TODO Auto-generated method stub
		try {
		return roleRepository.findAll();
		}catch(Exception e) {
			
			throw new PropraException(e);	
		}
	}

	@Override
	public Role addRole(Role r) throws PropraException {
		// TODO Auto-generated method stub
		
		try {
	 		
			return roleRepository.save(r);
			
		}catch(Exception e) {
				
				throw new PropraException(e);
				
				
			}
	
	}

	@Override
	public Role findRole(Integer id) throws PropraException{
		// TODO Auto-generated method stub
		
		try {
		return roleRepository.getById(id);
		}catch(Exception e) {
			
			throw new PropraException(e);
		}
	
	}

	
	@Override
	public void removeRole(Integer id) throws PropraException {
		// TODO Auto-generated method stub
		try {
		roleRepository.deleteById(id);
		}catch(Exception e) {
			throw new PropraException(e);
			
			
		}
		
	}

	@Override
	public Role updateRole(Role role) throws PropraException {
		// TODO Auto-generated method stub
		try {
			Role indb= roleRepository.getById(role.getId());
			indb.setName(role.getName());
			Role updated=roleRepository.save(indb);
			return updated;
			}catch(Exception e) {
				
				throw new PropraException(e);	
			}
	}

	
	
}
