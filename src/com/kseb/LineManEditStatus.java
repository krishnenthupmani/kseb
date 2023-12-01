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

@WebServlet("/LineManEditStatus")
public class LineManEditStatus extends HttpServlet{
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
		out.print("<h1>Welcome LineMan</h1>");
		
		
		out.print("<div class='welcomeadmin'>");
		out.print("<a href='LineManWorkAllocation'>WorK Allocated </a>");
		out.print("<a href='LineManMaterialRequest'>Material Request </a>");
		out.print("<a href='login.html'>LogOut</a>");
		out.print("</div></body></html>");

		

		con = dbcon1.getConnection();
		pst = con.prepareStatement("select allocation_id,allocation_date,comp_id,line_man from work_allocation  where allocation_id=?");
		pst.setInt(1, id);
		rs=pst.executeQuery();
		rs.next();
		
		
		
		
		out.print("<h4>Edit Work Allocation </h4>");
		out.print("<div class='workallocation'>");
		out.print("<form name='LineManEditStatus' method='post' action='LineManEditStatus'>");
		

		
		out.print("<h5>Allocation Id "+rs.getInt(1)+"</h5>");
		out.print("<label>Date </label>");	
			out.print("<input type='Date' name='date' value='date'/><br><br>");
		
		out.print("<label>Status </label>");
		out.print("<select name='status' id='status'>");
		out.print("<option value='Pending'>Pending</option>");
		out.print("<option value='Resolved'>Resolved</option>");
		out.print("</select><br><br>");
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
		
            String status = request.getParameter("status");
		
		con = dbcon1.getConnection();
		pst1 = con.prepareStatement("update work_allocation set  status=?, status_date=? where allocation_id=?");
		//pst1 = con.prepareStatement("update work_allocation set line_man=? where allocation_id=?");

		
		pst1.setString(1, status);
		pst1.setDate(2, sqlDate);
		pst1.setInt(3, id);
		int i=pst1.executeUpdate();
		
		if (i == 1) {
            response.sendRedirect("LineManWorkAllocation");
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



