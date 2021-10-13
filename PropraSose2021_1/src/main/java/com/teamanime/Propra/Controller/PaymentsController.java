package com.teamanime.Propra.Controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.teamanime.Propra.Entities.Bill;
import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Entities.Salary;
import com.teamanime.Propra.Entities.Session;
import com.teamanime.Propra.Entities.Tutor;
import com.teamanime.Propra.Exceptions.PropraException;
import com.teamanime.Propra.Repository.SessionRepository;
import com.teamanime.Propra.Services.BillService;
import com.teamanime.Propra.Services.LearnerService;
import com.teamanime.Propra.Services.PdfService;
import com.teamanime.Propra.Services.SalaryService;
import com.teamanime.Propra.Services.TutorService;
import com.teamanime.Propra.Utilitary.FindObject;
import com.teamanime.Propra.Utilitary.SearchForm;

@Controller
public class PaymentsController {

	
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
	private PdfService pdfService;
	/////////////////////////////////////////////////////////////
	////////////////END INJECTIONS////////////////////////////
	//////////////////////////////////////////////////////////////
	
	
	
    ////////////////////////////////////////////////////////////////////
	///////
	
	@GetMapping	("/payments") 
	public String payments( Model model) {
	
		
		return "Pages/forSalaryAndBill/payments";
	}
	
	
	
	@GetMapping	("/payments/bill") 
	public String paymentsAddForm( Model model   ) {
	
		model.addAttribute("bill",  new Bill());
		model.addAttribute("listLearners",learnerService.listLearners());
	
		return "Pages/forSalaryAndBill/bill";
	}
	
	
	@PostMapping ("/payments/bill") 
    public String paymentsAddSubmit(  @ModelAttribute @Valid Bill bill, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSalaryAndBill/bill";
		}
	
		Learner owner=learnerService.findLearner(bill.getOwner().getId());
		owner.getBills().add(bill);
		double total=0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bill.getBillDate());
		System.err.println("month::::::::::::::::::"+(calendar.get(Calendar.MONTH)));
		List<Session> mySessions=sessionRepository.findSessionOfMonthLearner((calendar.get(Calendar.MONTH)), owner.getId());
		for(Session session: mySessions) {
			//System.err.println("month::::::::::::::::::"+(calendar.get(Calendar.MONTH)-1));
			total+=(session.getSubject().getSubjectCost())*(session.getDuration());
		}
		bill.setTotal(total);
		bill.setSessions(mySessions);
	Bill billSaved = billService.addBill(bill);
	redAttr.addAttribute("id", billSaved.getId());
	redAttr.addFlashAttribute("message", "Bill successfully generated");
		return "redirect:/payments/viewBill/{id}";

	}
	
	@GetMapping	("/payments/salary") 
	public String salaryAddForm( Model model   ) {
	
		model.addAttribute("salary",  new Salary());
		model.addAttribute("listTutors",tutorService.listTutors());
	
		return "Pages/forSalaryAndBill/salary";
	}
	
	
	@PostMapping ("/payments/salary") 
    public String salaryAddSubmit(  @ModelAttribute @Valid Salary salary, BindingResult bindingResult,RedirectAttributes redAttr) {
	
		if (bindingResult.hasErrors()) {
			return "Pages/forSalaryAndBill/salary";
			
		}
		
		Tutor owner=tutorService.findTutor(salary.getOwner().getId());
		owner.getFees().add(salary);
		double total=0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(salary.getSince());
		System.err.println("month::::::::::::::::::"+(calendar.get(Calendar.MONTH)));
		List<Session> mySessions=sessionRepository.findSessionOfMonth((calendar.get(Calendar.MONTH)), owner.getId());
		for(Session session: mySessions) {
			//System.err.println("month::::::::::::::::::"+(calendar.get(Calendar.MONTH)-1));
			total+=owner.getWage()*(session.getDuration());
		}
		salary.setTotal(total);
		salary.setSessions(mySessions);
		//save the tutor in the db first
	Salary salarySaved = salaryService.addSalary(salary);
	///Salary salaryUpdate=salaryService.updateSalary(salarySaved);
	redAttr.addAttribute("id", salarySaved.getId());
	redAttr.addFlashAttribute("message", "Salary successfully addded");
		return "redirect:/payments/viewSalary/{id}";

	}
	

	@GetMapping	("/payments/viewSalary/{id}") 
	public String viewAfterCreationSalary( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		    	if(id==null) {
					return "redirect:/payments";
				}
		   
		
		Salary salary=salaryService.findSalary(id);
		model.addAttribute("message", message);
		model.addAttribute("salary", salary);
		 				
		return "Pages/forSalaryAndBill/salaryView";
	}
	
	@GetMapping	("/payments/viewBill/{id}") 
	public String viewAfterCreationBill( Model model,@ModelAttribute("id") Long id, @ModelAttribute("message") String message ) {
		
		    	if(id==null) {
					return "redirect:/payments";
				}
		    	Bill bill= billService.findBill(id);
				model.addAttribute("bill", bill);
				model.addAttribute("message", message);
			
				
		 				
		return "Pages/forSalaryAndBill/billView";
	}
	
	@GetMapping	("/payments/singleSalary") 
	public String viewSalary( Model model,@RequestParam(name="id", required=true) Long id ) {
			
		    	if(id==null || (!id.toString().matches("\\d+"))) {
		    		throw new PropraException("error on parameters");
				}
		    	Salary salary= salaryService.findSalary(id);
				model.addAttribute("salary", salary);
		
		return "Pages/forSalaryAndBill/salaryView";
	}
	
	@GetMapping	("/payments/singleBill") 
	public String viewBill( Model model,@RequestParam(name="id", required=true) Long id ) {
			
		    	if(id==null || (!id.toString().matches("\\d+"))) {
		    		throw new PropraException("error on parameters");
				}
		    	
		    	Bill bill= billService.findBill(id);
		    	Learner owner=learnerService.findLearner(bill.getOwner().getId());
		    	
		    	if(owner.isSubventioned()) {
		    	Calendar calendar = Calendar.getInstance();
				calendar.setTime(bill.getBillDate());
		    	List<Object[]> slist=sessionRepository.findSubventioned(calendar.get(Calendar.MONTH), owner.getId());
		    	model.addAttribute("listSubvention" ,slist );
		    	}
		    	model.addAttribute("bill", bill);
		return "Pages/forSalaryAndBill/billView";
	}
	
	
	
	
	@GetMapping	("/payments/findSalary") 
	public String salaryfindForm( Model model ) {
	
		return "Pages/forSalaryAndBill/findSalary";
	}
	
	
	@PostMapping ("/payments/findSalary")
    public String salaryFind(  HttpServletRequest request,Model model) {
		
		SearchForm sf=new SearchForm();
		FindObject toFind=sf.initializeSearchSalary(request);
	List<Salary> found= salaryService.findSalaries(toFind);
	model.addAttribute("listFound", found);
	return "Pages/forSalaryAndBill/findSalary";
	

	}
	
	@GetMapping	("/payments/findBill") 
	public String billFindForm( Model model ) {
	
		return "Pages/forSalaryAndBill/findBill";
	}
	
	
	@PostMapping ("/payments/findBill")
    public String billFindSubmit(  HttpServletRequest request,Model model) {
		
		SearchForm sf=new SearchForm();
		FindObject toFind=sf.initializeSearchBill(request);
	List<Bill> found= billService.findBills(toFind);
	model.addAttribute("listFound", found);
	return "Pages/forSalaryAndBill/findBill";
	

	}
	
	
	@GetMapping	("/payments/deleteBill") 
	public String BillDelete( Model model, @RequestParam(name="id" ,required=true) Long id) {
		
		if(id==null || (!id.toString().matches("\\d+"))) {
    		throw new PropraException("error on parameters");
		}
	  billService.removeBill(id);
		return "Pages/success";
	}
	
	@GetMapping	("/payments/deleteSalary") 
	public String salaryDelete( Model model, @RequestParam(name="id" ,required=true) Long id) {
		
		if(id==null || (!id.toString().matches("\\d+"))) {
    		throw new PropraException("error on parameters");
		}
		
	  salaryService.removeSalary(id);
		return "Pages/success";
	}
	
	
	@GetMapping("/payments/download_pdf")
    public void downloadPDFResource(HttpServletResponse response,@RequestParam(name="id" ,required=true) Long id) {
       
		if(id==null || (!id.toString().matches("\\d+"))) {
    		throw new PropraException("error on parameters");
		}
		try {
			 System.err.println("PDF WELL generated000");
            Path file = Paths.get(pdfService.generatePdf(id).getAbsolutePath());
            System.err.println(">>>>>>>>>>"+file.getFileName());
            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
    }
	
	
	
}		