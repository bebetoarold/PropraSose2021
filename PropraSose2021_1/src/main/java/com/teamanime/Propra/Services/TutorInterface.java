package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Utilitary.FindObject;

public interface TutorInterface {
	
	List<Tutor> listTutors()throws PropraException;
	//Tutor suitableTutor()throws PropraException; //finds tutors teaching the same subjects as the subjects 
	Tutor addTutor(Tutor tutor) throws PropraException;
	Tutor findTutor(Long id) throws PropraException;
	List<Tutor> findTutors(FindObject ft) throws PropraException;
	void removeTutor(Long id) throws PropraException;
	Tutor updateTutor(Tutor tutor) throws PropraException;
	Tutor linkWithSubject(Tutor tutor) throws PropraException;
	Tutor linkWithRole(Tutor tutor) throws PropraException;
}
