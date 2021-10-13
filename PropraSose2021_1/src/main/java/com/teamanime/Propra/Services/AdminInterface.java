package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Admin;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;

public interface AdminInterface {
	
	List<Admin> listAdmins()throws PropraException;
	Admin addAdmin(Admin admin) throws PropraException;
	Admin findAdmin(Long id) throws PropraException;
	void removeAdmin(Long id) throws PropraException;
	Admin updateAdmin(Admin admin) throws PropraException;
	Admin linkWithRole(Admin admin) throws PropraException;
	
	
}
