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

@WebServlet("/LineManEditMaterialRequest")
public class LineManEditMaterialRequest extends HttpServlet{
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
			pst = con.prepareStatement("select allocation_id,material_details from material_req where allocation_id=?");
			pst.setInt(1, id);
			rs=pst.executeQuery();
			rs.next();
			
			out.print("<h4>Edit Material Request </h4>");
			out.print("<div class='workallocation'>");
			out.print("<form name='LineManEditMaterialRequest' method='post' action='LineManEditMaterialRequest'>");
			out.print("<h5>Allocation No  "+rs.getInt(1)+"</h5>");
			out.print("<label>Material Details </label>");
			out.print("<input type='text' name='materialdetails' value='"+rs.getString(2)+"' ><br><br>");
			
				
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
	            String materialdetails = request.getParameter("materialdetails");
			
			con = dbcon1.getConnection();
			pst1 = con.prepareStatement("update material_req set material_details=? where allocation_id=?");


			pst1.setString(1, materialdetails);
			pst1.setInt(2, id);
			int i=pst1.executeUpdate();
			
			if (i == 1) {
				response.getWriter().println("Record updated successfully");

                response.sendRedirect("LineManMaterialRequest");
            } else {
              //  response.getWriter().println("Record not updated");
                response.sendRedirect("LineManMaterialRequest");
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

