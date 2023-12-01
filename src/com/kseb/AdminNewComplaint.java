package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminNewComplaint")
public class AdminNewComplaint extends HttpServlet {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String usertype = null;
	String username = null;
	RequestDispatcher dis = null;
	DBConnection dbcon = new DBConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession(false);
	//	String name=(String)(session.getAttribute("uname"));
		//out.print("Hi" +name);
		
			
			out.print("<html>");
			out.print("<head><link rel='stylesheet' type='text/css' href='css/adminnewcomplaintstyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome Administrator</h1>");

			out.print("<div class='welcomeadmin'>");
			out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
			out.print("<a href='AdminViewComplaint'>View Complaints </a>");
			out.print("<a href='WorkAllocation'>Work Allocation</a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div>");
			out.print("<h4>New Complaint Registration</h4>");
			out.print("<div class='complaint'>");
			out.print("<form name='complaint-Registration' method='post' action='AdminNewComplaint'>");
			out.print("<label>Date </label>");
				out.print("<input type='Date' name='date'/><br><br>");
			out.print("<label>Consumer Name</label>");
				out.print("<input type='text' name='consumername'/><br><br>");
			out.print("<label>Address</label>");
				out.print("<textarea id='address' name='address' rows='5' column='50'/></textarea><br><br>");	
			out.print("<label>Location</label>");
				out.print("<input type='text' name='location'/><br><br>");
			out.print("<label>Post no</label>");
				out.print("<input type='text' name='postno'/><br><br>	");
			out.print("<label>Complaint</label> ");
				out.print("<textarea id='complaint' name='complaint' rows='5' column='50'/></textarea><br><br>");
			out.print("<input type='submit' value='save' />");
			out.print("</form></div>");
			out.print("</body></html>");
			
			try {
				String date =request.getParameter("date"); 
				String consumername = request.getParameter("consumername");
				String address = request.getParameter("address");
				String location = request.getParameter("location");
				String postno = request.getParameter("postno");
				String complaint = request.getParameter("complaint");
			con = dbcon.getConnection();
			pst = con.prepareStatement("insert into complaint_reg(comp_reg_date,cons_name,cons_add,cons_loc,cons_postno,complaint) values(?,?,?,?,?,?)");
		
			
			pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			pst.setString(2, consumername);
			pst.setString(3, address);
			pst.setString(4, location);
			pst.setString(5, postno);
			pst.setString(6, complaint);
			System.out.println(pst);
			int i=pst.executeUpdate();
			//to write in console
			rs=pst.executeQuery();
			while(rs.next()){
			System.out.println(rs.getDate(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
			}
			if(i==1){
				out.println("<h2>values entered</h2>");	
				
			}	
			else{
				out.println("<h2>registration failed</h2");	
			}	
			
			con.close();
		} catch (SQLException sql) {
			sql.printStackTrace();
			//out.print("<h1>" +sql.getMessage()+"</h1>");
			
		}catch(Exception e){
			e.printStackTrace();
			//out.print("<h1>" +e.getMessage()+"</h1>");
		}
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		doGet(request,response);
	}

}
