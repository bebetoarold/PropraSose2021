package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Subject;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Utilitary.FindObject;

public interface SubjectInterface {
	
	List<Subject> listSubjects() throws PropraException;
	Subject addSubject(Subject subject)throws PropraException;
	Subject findSubject(Long id) throws PropraException;
	List<Subject> findSubjects(FindObject ft) throws PropraException;
	void removeSubject(Long id) throws PropraException;
	Subject updateSubject(Subject subject) throws PropraException;
	
}
