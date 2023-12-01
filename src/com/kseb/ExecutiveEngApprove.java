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

@WebServlet("/ExecutiveEngApprove")
public class ExecutiveEngApprove extends HttpServlet{
	Connection con = null;
	PreparedStatement pst = null;
	PreparedStatement pst1 = null;
	
	ResultSet rs = null;
	ResultSet rs1 = null;
	
	String usertype = null;
	String username = null;
	RequestDispatcher dis = null;
	DBConnection dbcon = new DBConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	try {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id=Integer.parseInt(request.getParameter("id"));
		
		
		out.print("<html>");
		out.print("<head><link rel='stylesheet' type='text/css' href='css/adminnewworkallocationstyle.css'></head>");
		out.print("<body>");
		out.print("<h1>Welcome ExcecutiveEngineer</h1>");
		
		
		out.print("<div class='welcomeadmin'>");
		out.print("<a href='ExecutiveEngMaterialReq'> Material Requested </a>");
		out.print("<a href='login.html'>LogOut</a>");
		out.print("</div></body></html>");

		

		con = dbcon.getConnection();
		pst = con.prepareStatement("select req_id from material_req where req_id=?");
		pst.setInt(1, id);
		rs=pst.executeQuery();
		rs.next();
		
		
		
		
		out.print("<h4>Approve Material Request </h4>");
		out.print("<div class='workallocation'>");
		out.print("<form name='ExecutiveEngApprove' method='post' action='ExecutiveEngApprove'>");
		

		
		out.print("<h5>Request Id "+rs.getInt(1)+"</h5>");
		out.print("<label>Date </label>");	
			out.print("<input type='Date' name='date' value='date'/><br><br>");
		out.print("<label>Request Status </label>");	
			out.print("<input type='text' name='reqstatus' /><br><br>");
		
		
		out.print("<input type='hidden' name='id' value='" + id + "'/>");
		
			
		out.print("<input type='submit' value='Approve'/> ");
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
		
           String reqstatus = request.getParameter("reqstatus");
		
		con = dbcon.getConnection();
		pst1 = con.prepareStatement("update  material_req set  req_acc_date=?, req_status=? where req_id=?");//edit
		//pst1 = con.prepareStatement("update work_allocation set line_man=? where allocation_id=?");

		pst1.setDate(1, sqlDate);
		pst1.setString(2, reqstatus);
		
		pst1.setInt(3, id);
		int i=pst1.executeUpdate();
		
		if (i == 1) {
            response.sendRedirect("ExecutiveEngMaterialReq");
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




