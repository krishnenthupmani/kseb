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
@WebServlet("/WorkAllocation")
public class AdminNewWorkAllocation extends HttpServlet {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	PreparedStatement pst1 = null;
	ResultSet rs1 = null;
	PreparedStatement pst2 = null;
	ResultSet rs2 = null;
	String usertype = null;
	String username = null;
	RequestDispatcher dis = null;
	DBConnection dbcon1 = new DBConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession(false);
		
		
			
			out.print("<html>");
			out.print("<head><link rel='stylesheet' type='text/css' href='css/adminnewworkallocationstyle.css'></head>");
			out.print("<body>");
			out.print("<h1>Welcome Administrator</h1>");

			out.print("<div class='welcomeadmin'>");
			out.print("<a href='AdminNewComplaint'>Complaint Registration</a>");
			out.print("<a href='AdminViewComplaint'>View Complaints </a>");
			out.print("<a href='WorkAllocation'>Work Allocation</a>");
			out.print("<a href='login.html'>LogOut</a>");
			out.print("</div>");
			try {
				con = dbcon1.getConnection();
				pst = con.prepareStatement("select comp_id from complaint_reg ");
				
				rs=pst.executeQuery();
				 PreparedStatement pst2 = con.prepareStatement("SELECT user_name FROM user_details where user_type='LineMan'");
		            ResultSet rs2 = pst2.executeQuery();
		            rs2=pst2.executeQuery();
		           
		            
					
				
				
			out.print("<h4>New Work Allocation</h4>");
			out.print("<div class='workallocation'>");
			out.print("<form name='New-work-Allocation' method='post' action='WorkAllocation'>");
			out.print("<label>Date </label>");
				out.print("<input type='Date' name='allocateddate'/><br><br>");
				
				
			out.print("<label for='compId'>Complaint No</label>");
			out.print("<select name='compId' id='compId'>");
			while(rs.next()){
				 int compId = rs.getInt("comp_id");
			
			out.print("<option value='"+ compId + "'>" + compId + "</option>");
					}
			out.print("</select><br><br>");
	
			
			out.print("<label for='lineMan'>Line Man</label>");
			out.print("<select name='lineMan' id='lineMan'>");
			while (rs2.next()) {
			    String lineMan = rs2.getString("user_name");
			    out.print("<option value='" + lineMan + "'>" + lineMan + "</option>");
			}
			out.print("</select><br><br>");
			
			
			out.print("</br>");
			
			out.print("<input type='submit' value='save' />");
			out.print("</form></div>");
			out.print("</body></html>");
			
			
			if ("POST".equalsIgnoreCase(request.getMethod())) {
                // Form submission is a POST request
                String allocateddate = request.getParameter("allocateddate");
                int compId = Integer.parseInt(request.getParameter("compId"));
                String lineMan = request.getParameter("lineMan");

                // Insert the values into the database
                PreparedStatement insertPst = con.prepareStatement(
                        "INSERT INTO work_allocation (allocation_date, comp_id, line_man) VALUES (?, ?, ?)");

                insertPst.setDate(1, java.sql.Date.valueOf(allocateddate));
                insertPst.setInt(2, compId);
                insertPst.setString(3, lineMan);

                int rowsAffected = insertPst.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("<h2>Values inserted into work_allocation table</h2>");
                } else {
                    out.println("<h2>Insert failed</h2>");
                }
          }
			
			
			
			con = dbcon1.getConnection();
			pst1 = con.prepareStatement("select allocation_id,allocation_date,comp_id,line_man from work_allocation");
			rs1=pst1.executeQuery();
			out.print("<h4>Work Allocation Details</h4>");
			out.print("<table border='1'>");
			out.print("<tr>");
			out.print("<th> Allocation ID</th>");
			out.print("<th>WorkAllocation date</th>");
			out.print("<th>Complaint No </th>");
			out.print("<th>LineMan </th>");
			
			out.print("<th>Edit</th>");
			out.print("<th>Delete</th>");
			out.print("</tr>");
			while(rs1.next()){
				out.print("<tr>");
				out.print("<td>"+rs1.getInt(1)+"</td>");
				out.print("<td>"+rs1.getDate(2)+"</td>");
				out.print("<td>"+rs1.getInt(3)+"</td>");
				out.print("<td>"+rs1.getString(4)+"</td>");
				
				
				out.print("<td>");
				out.print("<a href='EditWorkAllocation?id="+rs1.getInt(1)+"'>'Edit'</a>");
				out.print("</td>");
				out.print("<td>");
				out.print("<a href='DeleteWorkAllocation?id="+rs1.getInt(1)+"'>'Delete'</a>");
				out.print("</td>");
				
				out.print("</tr>");
			}	

			} catch (SQLException sql) {
				sql.printStackTrace();
				//out.print("<h1>" +sql.getMessage()+"</h1>");
				
			}catch(Exception e){
				e.printStackTrace();
				//out.print("<h1>" +e.getMessage()+"</h1>");
			}
			
			finally {
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
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		doGet(request,response);
		
	}

}



