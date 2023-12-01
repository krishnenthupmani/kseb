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

@WebServlet("/LineManMaterialRequest")
public class LineManMaterialRequest extends HttpServlet {
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
	DBConnection dbcon2 = new DBConnection();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = (String) request.getSession().getAttribute("uname");
		out.print("<html>");
		out.print("<head><link rel='stylesheet' type='text/css' href='css/adminnewworkallocationstyle.css'></head>");
		out.print("<body>");
		out.print("<h1>Welcome LineMan</h1>");
		out.print("<div class='welcomeadmin'>");
		out.print("<a href='LineManWorkAllocation'>WorK Allocated </a>");
		out.print("<a href='LineManMaterialRequest'>Material Request </a>");
		out.print("<a href='login.html'>LogOut</a>");
		out.print("</div>");
		
		try {

			con = dbcon1.getConnection();
			pst = con.prepareStatement("select allocation_id from work_allocation where line_man=?");
			pst.setString(1, name);
			rs = pst.executeQuery();

			out.print("<h4>New Material Request </h4>");
			out.print("<div class='workallocation'>");
			out.print("<form name='LineManMaterialRequest' method='post' action='LineManMaterialRequest'>");

			out.print("<label>Date </label>");
			out.print("<input type='Date' name='date' value='date'/><br><br>");

			out.print("<label for='allocationId'>Allocation No</label>");
			out.print("<select name='allocationId' id='allocationId'>");
			while (rs.next()) {
				int allocationId = rs.getInt("allocation_id");
				out.print("<option value='" + allocationId + "'>" + allocationId + "</option>");
			}
			out.print("</select><br><br>");

			out.print("<label>Material Details </label>");
			out.print(
					"<textarea id='materialdetails' name='materialdetails' rows='5' columns='50'></textarea><br><br>");

			out.print("<input type='submit' value='Save'/>");
			out.print("</form></div>");
			out.print("</body></html>");
			
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String date = request.getParameter("date");
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);

			int allocationId = Integer.parseInt(request.getParameter("allocationId"));
			String materialDetails = request.getParameter("materialdetails");
			con = dbcon1.getConnection();
			pst = con.prepareStatement("INSERT INTO material_req (req_date, allocation_id, material_details) VALUES (?, ?, ?)");
			pst.setDate(1, sqlDate);
			pst.setInt(2, allocationId);
			pst.setString(3, materialDetails);
			int i = pst.executeUpdate();
			if (i == 1) {
				response.sendRedirect("LineManHome");
			} else {
				response.getWriter().println("Material request not saved");
			}

		}
			con = dbcon2.getConnection();
			//pst2 = con.prepareStatement("select req_id,allocation_id,req_date,material_details from material_req");
			pst2 = con.prepareStatement("select c.comp_id,w.line_man,w.status, m.req_id,m.allocation_id,m.req_date,m.material_details from complaint_reg c,work_allocation w,material_req m where c.comp_id=w.comp_id and w.allocation_id=m.allocation_id and w.line_man =? ");
			pst2.setString(1, name);
			rs2 = pst2.executeQuery();

			out.print("<h4>Material Request Details</h4>");
			out.print("<table border='1'>");
			out.print("<tr>");
			out.print("<th>Complaint No</th>");
			out.print("<th>Line Man</th>");
			out.print("<th>Status</th>");
			out.print("<th>Request ID</th>");
			out.print("<th>Allocation No</th>");
			out.print("<th>Request Date </th>");
			out.print("<th>Material Details </th>");
			
			out.print("<th>Edit</th>");
			out.print("<th>Delete</th>");
			out.print("</tr>");
			while (rs2.next()) {
				out.print("<tr>");
				out.print("<td>" + rs2.getInt(1) + "</td>");
				out.print("<td>" + rs2.getString(2) + "</td>");
				out.print("<td>" + rs2.getString(3) + "</td>");
				out.print("<td>" + rs2.getInt(4) + "</td>");
				out.print("<td>" + rs2.getInt(5) + "</td>");
				out.print("<td>" + rs2.getDate(6) + "</td>");
				out.print("<td>" + rs2.getString(7) + "</td>");
				out.print("<td>");
				out.print("<a href='LineManEditMaterialRequest?id=" + rs2.getInt(5) + "'>Edit</a>");
				out.print("</td>");
				out.print("<td>");
				out.print("<a href='LineManDeleteMaterialRequest?id=" + rs2.getInt(5) + "'>Delete</a>");
				out.print("</td>");
				out.print("</tr>");
			}
			

		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the ResultSet and PreparedStatement in a finally block
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);

	}

}
