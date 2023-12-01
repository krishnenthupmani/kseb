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

@WebServlet("/LineManDeleteMaterialRequest")
public class LineManDeleteMaterialRequest extends HttpServlet{
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
			out.print("<head><link rel='stylesheet' type='text/css' href='css/adminnewworkallocationstyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome LineMan</h1>");
			out.print("<div class='welcomeadmin'>");
			out.print("<a href='LineManWorkAllocation'>WorK Allocated </a>");
			out.print("<a href='LineManMaterialRequest'>Material Request </a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div>");

			

			con = dbcon1.getConnection();
			pst = con.prepareStatement("delete  from material_req where allocation_id=?");
			pst.setInt(1, id);
			int i=pst.executeUpdate();
			
			if (i == 1) {
                response.sendRedirect("LineManMaterialRequest");
            } else {
            	response.sendRedirect("LineManMaterialRequest");
                response.getWriter().println("Record not deleted");
            }
			
			
			
			
		} catch (SQLException sql) {
			sql.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        
			

        
    }


}
