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
@WebServlet("/EditWorkAllocation")
public class AdminEditWorkAllocation extends HttpServlet{
	Connection con = null;
	PreparedStatement pst = null;
	PreparedStatement pst1 = null;
	PreparedStatement pst2 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
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
		out.print("<head><link rel='stylesheet' type='text/css' href='css/adminnewworkallocationstyle.css'></head>");
		out.print("<body>");
		out.print("<h1>Welcome Administrator</h1>");
		
		
		out.print("<div class='welcomeadmin'>");
		out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
		out.print("<a href='AdminViewComplaint'>View Complaints </a>");
		out.print("<a href='WorkAllocation'>Work Allocation</a>");
		out.print("<a href='login.html'>LogOut</a>");
		out.print("</div></body></html>");

		

		con = dbcon1.getConnection();
		pst = con.prepareStatement("select allocation_id,allocation_date,comp_id,line_man from work_allocation  where allocation_id=?");
		pst.setInt(1, id);
		rs=pst.executeQuery();
		rs.next();
		
		
		 PreparedStatement pst2 = con.prepareStatement("SELECT user_name FROM user_details where user_type='LineMan'");
            ResultSet rs2 = pst2.executeQuery();
            rs2=pst2.executeQuery();
		
		out.print("<h4>Edit Work Allocation </h4>");
		out.print("<div class='workallocation'>");
		out.print("<form name='EditWorkAllocation' method='post' action='EditWorkAllocation'>");
		

		
		out.print("<h5>Allocation Id "+rs.getInt(1)+"</h5>");
		out.print("<label>Date </label>");	
			out.print("<input type='Date' name='allocateddate' value='"+rs.getDate(2)+"'/><br><br>");
		out.print("<label>Complaint  No</label>");
			out.print("<input type='text' name='complaintno' value='"+rs.getInt(3)+"'/><br><br>");
		//out.print("<label>LineMan</label>");
		out.print("<label for='lineMan'>Line Man</label>");
		out.print("<select name='lineMan' id='lineMan'>");
		while (rs2.next()) {
		    String lineMan = rs2.getString("user_name");
		    out.print("<option value='" + lineMan + "'>" + lineMan + "</option>");
		}
		out.print("</select><br><br>");
			//out.print("<input type='text' name='lineman' value='"+rs.getString(4)+"'/><br><br>");
		
			
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
		
		String allocateddate =request.getParameter("allocateddate");
	    java.sql.Date sqlDate=java.sql.Date.valueOf(allocateddate);
		int complaintno = Integer.parseInt(request.getParameter("complaintno"));
		String lineMan = request.getParameter("lineMan");
		
		con = dbcon1.getConnection();
		pst1 = con.prepareStatement("update work_allocation set allocation_date=?,comp_id=?,line_man=? where allocation_id=?");
		//pst1 = con.prepareStatement("update work_allocation set line_man=? where allocation_id=?");
		pst1.setDate(1, sqlDate);
		pst1.setInt(2, complaintno);
		pst1.setString(3, lineMan);
		pst1.setInt(4, id);
		int i=pst1.executeUpdate();
		
		if (i == 1) {
            response.sendRedirect("WorkAllocation");
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
