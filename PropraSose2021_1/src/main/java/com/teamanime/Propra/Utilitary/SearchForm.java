package com.teamanime.Propra.Utilitary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class SearchForm {

	
	private static final String STATS_FIELD="stats";
	private static final String SEARCH_FIELD="search";
	private static final String CATEGORIE_FIELD="categorie";
	private static final String MAIL_FIELD="mail";
	private static final String PASSWORD_FIELD="password";


	
	//main method
	public FindObject initializeSearch(HttpServletRequest request) {
		
		FindObject person=new FindObject();
		//extraction from the request
		String search=getFieldValue(request,SEARCH_FIELD);
		String categorie=getFieldValue(request,CATEGORIE_FIELD);
		person.setSearch(search);
		person.setCategorie(categorie);
		return person;
	}
	
public StatsObject initializeStats(HttpServletRequest request) throws ParseException {
		
		StatsObject stats=new StatsObject();
		//extraction from the request
		String s=getFieldValue(request,STATS_FIELD);
		System.err.println("String value of the date: "+s);
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(s);  
		stats.setStats(date);
		return stats;
	}
	
	
public FindObject initializeSearchSalary(HttpServletRequest request) {
		
		FindObject salary=new FindObject();
		//extraction from the request
		String search=getFieldValue(request,SEARCH_FIELD);
		String categorie=getFieldValue(request,CATEGORIE_FIELD);
		salary.setSearch(search);
		salary.setCategorie(categorie);
		return salary;
	}


public FindObject initializeSearchBill(HttpServletRequest request) {
	
	FindObject bill=new FindObject();
	//extraction from the request
	String search=getFieldValue(request,SEARCH_FIELD);
	String categorie=getFieldValue(request,CATEGORIE_FIELD);
	bill.setSearch(search);
	bill.setCategorie(categorie);
	return bill;
}
	
public LoginObject initializeLogin(HttpServletRequest request) {
		
		LoginObject credentials=new LoginObject();
		//extraction from the request
		String mail=getFieldValue(request,MAIL_FIELD);
		String pass=getFieldValue(request,PASSWORD_FIELD);
		credentials.setMail(mail);
		credentials.setPassword(pass);
		return credentials;
	}
		
	
	public String getFieldValue(HttpServletRequest request, String field ) {
		String value=request.getParameter(field);
		if(value==null || value.trim().length()==0) {
			return null;
	
	}else {
		return value.trim();
		}
	}
	
	
}
