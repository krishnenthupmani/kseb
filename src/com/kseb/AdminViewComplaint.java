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

@WebServlet("/AdminViewComplaint")
public class AdminViewComplaint extends HttpServlet {
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
				
				
				
				out.print("<html>");
				out.print("<head><link rel='stylesheet' type='text/css' href='css/adminhomestyle.css'></head>");
				out.print("<body>");
				out.print("<h1>Welcome Administrator</h1>");
				
				
				out.print("<div class='welcomeadmin'>");
				out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
				out.print("<a href='AdminViewComplaint'>View Complaints </a>");
				out.print("<a href='WorkAllocation'>Work Allocation</a>");
				out.print("<a href='login.html'>LogOut</a>");
				out.print("</div></body></html>");

				

				con = dbcon1.getConnection();
				pst = con.prepareStatement("select comp_id,comp_reg_date,cons_name,cons_add,cons_loc,cons_postno,complaint from complaint_reg");
				rs=pst.executeQuery();
				out.print("<h4>Complaints</h4>");
				out.print("<table border='1'>");
				out.print("<tr>");
				out.print("<th> Complaint ID</th>");
				out.print("<th>Complaint date</th>");
				out.print("<th>Consumer name </th>");
				out.print("<th>Consumer Address </th>");
				
				out.print("<th>Location </th>");
				out.print("<th>post no</th>");
				out.print("<th>Complaint</th>");
				out.print("<th>Edit</th>");
				out.print("<th>Delete</th>");
				out.print("</tr>");
				while(rs.next()){
					out.print("<tr>");
					out.print("<td>"+rs.getInt(1)+"</td>");
					out.print("<td>"+rs.getDate(2)+"</td>");
					out.print("<td>"+rs.getString(3)+"</td>");
					out.print("<td>"+rs.getString(4)+"</td>");
					out.print("<td>"+rs.getString(5)+"</td>");
					out.print("<td>"+rs.getString(6)+"</td>");
					out.print("<td>"+rs.getString(7)+"</td>");
					
					out.print("<td>");
					out.print("<a href='Editcomplaint?id="+rs.getInt(1)+"'>'Edit'</a>");
					out.print("</td>");
					out.print("<td>");
					out.print("<a href='Deletecomplaint?id="+rs.getInt(1)+"'>'Delete'</a>");
					out.print("</td>");
					
					out.print("</tr>");
				}
				
				
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
