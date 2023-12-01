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
@WebServlet("/LineManWorkAllocation")
public class LineManWorkAllocation extends HttpServlet {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String usertype = null;
	String username = null;
	RequestDispatcher dis = null;
	DBConnection dbcon1 = new DBConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			HttpSession session=request.getSession();
			//String name=(String)(session.getAttribute("uname"));
			String name = (String) request.getSession().getAttribute("uname");
			//out.print("<h2>"+ name+"</h2>");
			out.print("<html>");
			out.print("<head><link rel='stylesheet' type='text/css' href='css/adminhomestyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome LineMan  " + name+"</h1>");
			
			
			
			out.print("<div class='welcomeadmin'>");
			out.print("<a href='#'>WorK Allocated </a>");
			out.print("<a href='LineManMaterialRequest'>Material Request </a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div></body></html>");
			
			con = dbcon1.getConnection();
			pst = con.prepareStatement("select c.comp_id,c.comp_reg_date,c.cons_name,c.cons_add,c.cons_loc,c.cons_postno,c.complaint,w.allocation_id,w.allocation_date,w.comp_id,w.line_man,w.status,w.status_date from complaint_reg c,work_allocation w where c.comp_id=w.comp_id and w.line_man = ?");
			pst.setString(1, name);
			rs=pst.executeQuery();
			out.print("<h4>My work Allocation</h4>");
			out.print("<table border='1'>");
			out.print("<tr>");
			out.print("<th> Complaint No</th>");
			out.print("<th>Complaint date</th>");
			out.print("<th>Consumer name </th>");
			out.print("<th>Consumer Address </th>");
			
			out.print("<th>Location </th>");
			out.print("<th>post no</th>");
			out.print("<th>Complaint</th>");
			out.print("<th>Allocation Id</th>");
			out.print("<th>Allocated Date</th>");
			out.print("<th>LineMan</th>");
			out.print("<th>Status</th>");
			out.print("<th>StatusDate</th>");
			out.print("<th>EditStatus</th>");
			
			
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
				out.print("<td>"+rs.getInt(8)+"</td>");
				out.print("<td>"+rs.getDate(9)+"</td>");
				//out.print("<td>"+rs.getInt(10)+"</td>");
				out.print("<td>"+rs.getString(11)+"</td>");
				//out.print("<td>"+rs.getString(11)+"</td>");
				
				out.print("<td>"+rs.getString(12)+"</td>");
				
				
				out.print("<td>"+rs.getDate(13)+"</td>");
				out.print("<td>");
				out.print("<a href='LineManEditStatus?id="+rs.getInt(8)+"'>'Edit'</a>");
				out.print("</td>");
				out.print("</tr>");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
