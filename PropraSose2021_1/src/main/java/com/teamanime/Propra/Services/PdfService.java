package com.teamanime.Propra.Services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.teamanime.Propra.Entities.Bill;
import com.teamanime.Propra.Entities.Learner;
import com.teamanime.Propra.Repository.SessionRepository;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/templates/Pages/forSalaryAndBill/";
    private SpringTemplateEngine templateEngine;
    
    @Autowired
    BillService billService;
    
    @Autowired
    LearnerService learnerService;
    
    @Autowired
    SessionRepository sessionRepository;

   @Autowired
    public PdfService( ) {
	   ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
       templateResolver.setSuffix(".html");
       templateResolver.setTemplateMode(TemplateMode.HTML);

       SpringTemplateEngine templateEngine = new SpringTemplateEngine();
       templateEngine.setTemplateResolver(templateResolver);
	   this.templateEngine = templateEngine;
    }

    public File generatePdf(Long id) throws IOException, DocumentException {
    	 System.err.println("PDF WELL generated0.5");
        Context context = getContext(id);
        System.err.println("PDF WELL generated1");
        String html = loadAndFillTemplate(context);
        System.err.println("PDF WELL generated2");
        return renderPdf(html);
    }


    private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("factures", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContext(Long id) {
        Context context = new Context();
  
        Bill bill= billService.findBill(id);
    	Learner owner=learnerService.findLearner(bill.getOwner().getId());
    	
    	if(owner.isSubventioned()) {
	    	Calendar calendar = Calendar.getInstance();
			calendar.setTime(bill.getBillDate());
	    	List<Object[]> slist=sessionRepository.findSubventioned(calendar.get(Calendar.MONTH), owner.getId());
	    	context.setVariable("listSubvention" ,slist );
	    	}
        context.setVariable("bill", bill);
        System.err.println("PDF WELL generated1.5");
        return context;
    }

    private String loadAndFillTemplate(Context context) {
    	
        return templateEngine.process("/templates/Pages/forSalaryAndBill/bill_pdf", context);
    }


}
