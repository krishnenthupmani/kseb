package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin")

public class AdminHome extends HttpServlet {
	RequestDispatcher dis=null;
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		try{
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();		
			
			
			HttpSession session=request.getSession();
			String name=(String)(session.getAttribute("uname"));
			//out.print("Hi" +name);
			out.print("<html>");
			out.print("<head><link rel='stylesheet' type='text/css' href='css/adminhomestyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome Administrator " + Character.toUpperCase(name.charAt(0)) + name.substring(1) + "</h1>");
			
			
			out.print("<div class='welcomeadmin'>");
			out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
			out.print("<a href='AdminViewComplaint'>View Complaints </a>");
			out.print("<a href='WorkAllocation'>Work Allocation</a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div></body></html>");
			
			
			
	
			
			/*String name=request.getParameter("uname");
			out.print("Welcome " +name);*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		doGet(request,response);
	}

}
