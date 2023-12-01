package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	String usertype=null;
	String username=null;
	RequestDispatcher dis=null;
	DBConnection dbcon=new DBConnection();
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		try{
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			String inputUsername = request.getParameter("username");
	        String inputPassword = request.getParameter("password");
	        String inputUsertype = request.getParameter("usertype");

	        con = dbcon.getConnection();
	        pst = con.prepareStatement("SELECT user_name, user_type FROM user_details WHERE user_name=? AND user_password=? AND user_type=?");
	        pst.setString(1, inputUsername);
	        pst.setString(2, inputPassword);
	        pst.setString(3, inputUsertype);

	        rs = pst.executeQuery();

	        if (rs.next()) {
	            // User authentication successful
	            String dbUsername = rs.getString("user_name");
	            String dbUsertype = rs.getString("user_type");

	            HttpSession session = request.getSession();
	            session.setAttribute("uname", dbUsername);

	            if (dbUsertype.equals("Administrator")) {
	                dis = request.getRequestDispatcher("admin");
	                dis.forward(request, response);
	            } else if (dbUsertype.equals("LineMan")) {
	                dis = request.getRequestDispatcher("LineManHome");
	                dis.forward(request, response);
	            } else if (dbUsertype.equals("MaterialDepoManager")) {
	                dis = request.getRequestDispatcher("MaterialManagerHome");
	                dis.forward(request, response);
	            } else if (dbUsertype.equals("ExcecutiveEngineer")) {
	                dis = request.getRequestDispatcher("ExcecutiveEngrHome");
	                dis.forward(request, response);
	            } else {
	                dis = request.getRequestDispatcher("login");
	                dis.include(request, response);
	            }
	        } else {
	            // User authentication failed
	            out.println("<h3>Invalid username, password, or user type</h3>");
	        }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		doGet(request,response);
	}

}
