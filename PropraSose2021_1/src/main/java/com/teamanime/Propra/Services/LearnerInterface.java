package com.teamanime.Propra.Services;

import java.util.List;

import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Utilitary.FindObject;

public interface LearnerInterface {
	
	List<Learner> listLearners()throws PropraException;
	Learner addLearner(Learner learner) throws PropraException;
	Learner findLearner(Long id) throws PropraException;
	List<Learner> findLearners(FindObject ft) throws PropraException;
	void removeLearner(Long id) throws PropraException;
	Learner updateLearner(Learner learner) throws PropraException;
	Learner linkWithSubject(Learner learner) throws PropraException;
	//Learner linkWithTutor(Learner learner) throws PropraException;
	Learner linkWithRole(Learner learner) throws PropraException;
}
