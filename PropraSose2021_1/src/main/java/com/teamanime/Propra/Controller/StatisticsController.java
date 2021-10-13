package com.teamanime.Propra.Controller;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Entities.Person;
import com.teamanime.Propra.Repository.PersonRepository;
import com.teamanime.Propra.Repository.SessionRepository;
import com.teamanime.Propra.Services.BillService;
import com.teamanime.Propra.Services.LearnerService;
import com.teamanime.Propra.Services.PdfService;
import com.teamanime.Propra.Services.PersonDetails;
import com.teamanime.Propra.Services.SalaryService;
import com.teamanime.Propra.Services.TutorService;
import com.teamanime.Propra.Utilitary.ChartObject;
import com.teamanime.Propra.Utilitary.SearchForm;
import com.teamanime.Propra.Utilitary.StatsObject;

@Controller
public class StatisticsController {

	
	//////////////////////////////////////////////////////////
	/////////////////////Injections///////////////////////////
	/////////////////////////////////////////////////////////
	
	
	@Autowired
	private SalaryService salaryService;
	

	@Autowired
	private BillService billService;
	
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private LearnerService learnerService;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PdfService pdfService;
	/////////////////////////////////////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
	
	
    ////////////////////////////////////////////////////////////////////
	///////
	

	
	@GetMapping	("/statistics") 
	public String statistics(Model model) {
	
		return "Pages/forStatistics/statistics";
	}
	
	@PostMapping	("/statistics") 
	public String statisticsSubmit(HttpServletRequest request,Model model,@AuthenticationPrincipal PersonDetails userDetails ) throws ParseException {
		
		Person asker= personRepository.findByMailAddress(userDetails.getUsername());
		Learner learner=learnerService.findLearner(asker.getId());
		
		SearchForm sf=new SearchForm();
		StatsObject toFind=sf.initializeStats(request);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toFind.getStats());
    	List<Object[]> slist=sessionRepository.findSubventioned(calendar.get(Calendar.MONTH), learner.getId());
    	List<List<Object>> myobjects = new ArrayList<>();
    	List<Object> temp;
   
    	for (Object[] objects : slist) {
			/*
			 * ChartObject a=new ChartObject(); a.setSubject((String)objects[0]); int
			 * bd1=((BigDecimal)objects[2]).intValue();
			 * 
			 * int bd2= ((int)objects[7]);
			 * 
			 * a.setCost( bd1*bd2);
			 * 
			 * myobjects.add(a);
			 */
    		
    			temp=List.of((String)objects[0],(((BigDecimal)objects[2]).intValue())*(((int)objects[7])));
    				
    			myobjects.add(temp);
		}
    	
    	List<ChartObject> list = new ArrayList<>();
   
    	for (Object[] objects : slist) {
			
			 ChartObject a=new ChartObject(); a.setSubject((String)objects[0]);
			 int bd1=((BigDecimal)objects[2]).intValue();
			  int bd2= ((int)objects[7]);
			  a.setCost( bd1*bd2);
			  list.add(a);
		}
    	
   
    	model.addAttribute("chartData" ,myobjects );
    	model.addAttribute("chartData2" ,list );
    	model.addAttribute("learner" ,learner );
    	model.addAttribute("currentDate",toFind.getStats());
		
		
		return "Pages/forStatistics/statisticsView";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}		