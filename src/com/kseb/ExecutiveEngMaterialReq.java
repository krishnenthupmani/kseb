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

@WebServlet("/ExecutiveEngMaterialReq")
public class ExecutiveEngMaterialReq extends HttpServlet {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String usertype = null;
	String username = null;
	RequestDispatcher dis = null;
	DBConnection dbcon = new DBConnection();
	
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
			out.print("<h1>Welcome ExcecutiveEngineer</h1>");
			
			
			
			out.print("<div class='welcomeadmin'>");
			out.print("<a href='ExecutiveEngMaterialReq'> Material Requested </a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div></body></html>");
			
			con = dbcon.getConnection();
			pst = con.prepareStatement("select w.allocation_id,w.allocation_date,w.comp_id,w.line_man,w.status,w.status_date, m.req_id,m.req_date,m.material_details,m.req_status,m.req_acc_date from work_allocation w ,material_req m where w.allocation_id=m.allocation_id");
			
			rs=pst.executeQuery();
			out.print("<h4>My work Allocation</h4>");
			out.print("<table border='1'>");
			out.print("<tr>");
			out.print("<th> Allocation No</th>");
			out.print("<th>Allocation date</th>");
			out.print("<th>Complaint No </th>");
			out.print("<th>LineMan</th>");
			out.print("<th>Status </th>");
			out.print("<th>Status Date</th>");
			out.print("<th>Request No</th>");
			out.print("<th>Request Date</th>");;
			out.print("<th>Material Details</th>");
			
			out.print("<th>Approve</th>");
			//out.print("<th>Material ReqStatus</th>");
			//out.print("<th>Material ReqAcceptDAte</th>");
			out.print("</tr>");
			while(rs.next()){
				out.print("<tr>");
				out.print("<td>"+rs.getInt(1)+"</td>");
				out.print("<td>"+rs.getDate(2)+"</td>");
				out.print("<td>"+rs.getInt(3)+"</td>");
				out.print("<td>"+rs.getString(4)+"</td>");
				out.print("<td>"+rs.getString(5)+"</td>");
				out.print("<td>"+rs.getDate(6)+"</td>");
				out.print("<td>"+rs.getInt(7)+"</td>");
				out.print("<td>"+rs.getDate(8)+"</td>");
				out.print("<td>"+rs.getString(9)+"</td>");
				
				out.print("<td>");
				out.print("<a href='ExecutiveEngApprove?id="+rs.getInt(7)+"'>'Edit'</a>");
				out.print("</td>");
				/*out.print("<td>"+rs.getString(10)+"</td>");
				
				out.print("<td>");
				if (rs.getDate(11) != null) {
				    out.print(rs.getDate(11));
				} else {
				    out.print("No Date");
				}
				out.print("</td>");
				//out.print("<td>"+rs.getDate(11)+"</td>");*/		
				out.print("</tr>");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
 
