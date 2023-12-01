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

//@WebServlet("/EditUpdate")
public class AdminEditUpdate extends HttpServlet{
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String usertype = null;
	String username = null;
	RequestDispatcher dis = null;
	DBConnection dbcon1 = new DBConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			int id=Integer.parseInt(request.getParameter("id"));
			
			
			out.print("<html>");
			out.print("<head><link rel='stylesheet' type='text/css' href='css/adminhomestyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome Administrator</h1>");
			
			
			out.print("<div class='welcomeadmin'>");
			out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
			out.print("<a href='AdminViewComplaint'>View Complaints </a>");
			out.print("<a href='#'>Work Allocation</a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div></body></html>");
			out.print("<form method='post' action='EditUpdate'>");
			out.print("</form>");
			String date =request.getParameter("date");
		    java.sql.Date sqlDate=java.sql.Date.valueOf(date);
			String consumername = request.getParameter("consumername");
			String address = request.getParameter("address");
			
			String location = request.getParameter("location");
			String postno = request.getParameter("postno");
			String complaint = request.getParameter("complaint");

			con = dbcon1.getConnection();
			pst = con.prepareStatement("update complaint_reg set comp_reg_date=?,cons_name=?,cons_add=?,cons_ph=?,cons_loc=?,cons_postno=?,complaint=? where comp_id=?");

			pst.setDate(1, sqlDate);
			pst.setString(2, consumername);
			pst.setString(3, address);
		
			pst.setString(4, location);
			pst.setString(5, postno);
			pst.setString(6, complaint);
			pst.setInt(7, id);
			int i=pst.executeUpdate();
			
			if(i==1){
				out.print("<h2>Record is updated</h2>");
			}else{
				out.print("<h2>Record is not updated</h2>");
			}
			out.print("<a href='/AdminViewComplaint'>view</a>");
			
		} catch (SQLException sql) {
			sql.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		doGet(request,response);
	}
}



