package com.teamanime.Propra.Filters;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

/**
 *
 * @author Arold Bebeto Ateumo
 */

@Component
@WebFilter(urlPatterns={""})
public class ApplicationFilter  implements Filter{
    private static final String ATT_USER_IN_SESSION="session_user";
     private static final String CONNECTION="/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        //casting
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse resp=(HttpServletResponse)response;
        
        //excluding /public/*: form,base.jsp
        
       /* String road = req.getRequestURI().substring( req.getContextPath().length() );
        if ( road.startsWith( "/public" ) ) {
            chain.doFilter( req, resp );
            return;
        }*/
        
        //session looking
        
        HttpSession session=req.getSession();
        
        if(session.getAttribute(ATT_USER_IN_SESSION)==null){
        
            //forwarding
            
            //req.getServletContext().getRequestDispatcher(CONNECTION).forward(req, resp);
        	 chain.doFilter(req,resp);
        }else{
        
            chain.doFilter(req,resp);
        }
        
        
        
    }

    @Override
    public void destroy() {
        
    }
    
}

