package com.otsuka.loe.util;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		System.out.println("**************start filter*******************");
		
		String urlKey = new String("");
		String sessionKey = new String("");
		String configKey = new String("");
		Boolean validUser = false;
		
		final String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
		System.out.println("url = "+ url);
		 
		if (!url.endsWith("/loe/") && (!url.endsWith("/login"))) {
			try {	
				HttpServletRequest request = (HttpServletRequest) servletRequest;
				 HttpSession session = request.getSession();
				 sessionKey = (String)session.getAttribute("userName");
				 if(sessionKey == null)
					 validUser = false;
				 else
					 validUser = true;
				 
				 /*
				 urlKey = servletRequest.getParameter("key");
				 if(urlKey == null)
					 urlKey="";
				 //configKey = configProperties("userkey");
				 if(configKey == null)
					 configKey="";
				 validUser = false;
				 */
				 
				 System.out.println("sessionKey="+sessionKey);
				 
				 /*
				 if (sessionKey.equals(configKey)){
					 //System.out.println("---sessionKey equals configKey-----user is valid!!!");
					 validUser = true;
				 }
				 
				 if (!urlKey.isEmpty()  && !validUser){
					 //System.out.println("urlKey not empty  and  not a validUser");
					 if (sessionKey.isEmpty()){
						 if(urlKey.equals(configKey)){
							 //System.out.println("urlKey equals configKey");
							 session = request.getSession(true);
							 session.setAttribute("user", urlKey);
							 validUser = true;
							 //System.out.println("doFilter():---user set in new session");
						 }else {
							 validUser = false;
							 //System.out.println("---doFilter():---else----------- validUser = "+validUser);	 
						 }
					 }	 
				 }
				 */
				 
			}catch (Exception e) {
				e.printStackTrace();
			}
		
			if (!validUser) {
				
				System.out.println("not valid user....sending to logout page");		        
	             if (servletRequest instanceof HttpServletRequest) {
	                 HttpServletRequest httpServletRequest = ((HttpServletRequest)servletRequest);                                    
	                 httpServletRequest.getSession().getServletContext().getRequestDispatcher(
	                     "/logout").forward(servletRequest,servletResponse);
	             }
	             
				

			}else {
				filterChain.doFilter(servletRequest, servletResponse);

			}

		}else {
			filterChain.doFilter(servletRequest, servletResponse);

		}
		System.out.println("**************end filter*******************");
	}
	
	/*
	//read properties file
	 public String configProperties(String prop) {

	    Properties configProperties = new Properties();
		String propertyFile = "configurations.properties";
	
		if(UserFilter.class.getClassLoader().getResourceAsStream(propertyFile) == null){
			System.out.println("configurations file not loaded");
		}
	
		try {
			configProperties.load(UserFilter.class.getClassLoader().getResourceAsStream(propertyFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		String propVal = (String)configProperties.get(prop);
		//System.out.println("inside configProperties(): propVal= "+propVal);
	
		return propVal;
			
	 }
	 */
}
