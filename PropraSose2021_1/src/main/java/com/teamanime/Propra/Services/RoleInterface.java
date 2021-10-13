package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Role;
import com.teamanime.Propra.Exceptions.PropraException;

public interface RoleInterface {
	
	List<Role> listRoles() throws PropraException;
	Role addRole(Role role)throws PropraException;
	Role findRole(Integer id) throws PropraException;
	void removeRole(Integer id) throws PropraException;
	Role updateRole(Role role) throws PropraException;
	
}
