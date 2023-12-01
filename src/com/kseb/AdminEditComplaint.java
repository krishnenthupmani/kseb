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
@WebServlet("/Editcomplaint")
public class AdminEditComplaint extends HttpServlet{
	Connection con = null;
	PreparedStatement pst = null;
	PreparedStatement pst1 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
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
			out.print("<head><link rel='stylesheet' type='text/css' href='css/admineditcomplaintstyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome Administrator</h1>");
			
			
			out.print("<div class='welcomeadmin'>");
			out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
			out.print("<a href='AdminViewComplaint'>View Complaints </a>");
			out.print("<a href='WorkAllocation'>Work Allocation</a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div></body></html>");

			

			con = dbcon1.getConnection();
			pst = con.prepareStatement("select comp_id,comp_reg_date,cons_name,cons_add,cons_loc,cons_postno,complaint from complaint_reg where comp_id=?");
			pst.setInt(1, id);
			rs=pst.executeQuery();
			rs.next();
			
			out.print("<h4>Edit Complaint </h4>");
			out.print("<div class='editcomplaint'>");
			out.print("<form name='Editcomplaint' method='post' action='Editcomplaint'>");
			//out.print("<form name='Editcomplaint' method='post' action='EditUpdate?id="+id+"'>");
			out.print("<h5>complaint no "+rs.getInt(1)+"</h5>");
			out.print("<label>Date </label>");	
				out.print("<input type='Date' name='date' value='"+rs.getDate(2)+"'/><br><br>");
			out.print("<label>Consumer Name</label>");
				out.print("<input type='text' name='consumername' value='"+rs.getString(3)+"'/><br><br>");
			out.print("<label>Address</label>");
				out.print("<input type='text' name='address' value='"+rs.getString(4)+"'/><br><br>");
			out.print("<label>Location </label>");
				
				out.print("<input type='text' name='location' value='"+rs.getString(5)+"'/><br><br>");
			out.print("<label>Post no</label>");
				out.print("<input type='text' name='postno' value='"+rs.getString(6)+"'/><br><br>");
			out.print("<label>Complaint</label> ");
				out.print("<input type='text'  name='complaint'  value='"+rs.getString(7)+"'/><br><br>");
				
				out.print("<input type='hidden' name='id' value='" + id + "'/>");
				
			out.print("<input type='submit' value='Update'/> ");
			out.print("</form></div>");
			out.print("</body></html>");
		} catch (SQLException sql) {
			sql.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        try {
	            int id = Integer.parseInt(request.getParameter("id"));
			
			String date =request.getParameter("date");
		    java.sql.Date sqlDate=java.sql.Date.valueOf(date);
			String consumername = request.getParameter("consumername");
			String address = request.getParameter("address");
			
			String location = request.getParameter("location");
			String postno = request.getParameter("postno");
			String complaint = request.getParameter("complaint");
			con = dbcon1.getConnection();
			pst1 = con.prepareStatement("update complaint_reg set comp_reg_date=?,cons_name=?,cons_add=?,cons_loc=?,cons_postno=?,complaint=? where comp_id=?");

			pst1.setDate(1, sqlDate);
			pst1.setString(2, consumername);
			pst1.setString(3, address);
		
			pst1.setString(4, location);
			pst1.setString(5, postno);
			pst1.setString(6, complaint);
			pst1.setInt(7, id);
			int i=pst1.executeUpdate();
			
			if (i == 1) {
                response.sendRedirect("AdminViewComplaint");
            } else {
                response.getWriter().println("Record not updated");
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
