package com.loginapp.login;

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

import com.loginapp.DBconnection.DB_connection;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		//out.print("<h1 style=\"color: blueviolet;\">Welcome to login servlet</h1>");
		RequestDispatcher rd= null;
		
		String uemail= request.getParameter("email");
		
		String pass= request.getParameter("password");
		HttpSession session= request.getSession();
		
		if(uemail==null || uemail.equals("")) {
			request.setAttribute("status", "Invalid email");
			rd= request.getRequestDispatcher("login.jsp") ;
			rd.forward(request, response);
		}
		else if(pass==null || pass.equals("")) {
			request.setAttribute("status", "Invalid password");
			rd= request.getRequestDispatcher("login.jsp") ;
			rd.forward(request, response);
		}
		try {
			Connection con= DB_connection.dbconnect();
			PreparedStatement ps= con.prepareStatement("select * from users where email=? and password=?");
			ps.setString(1, uemail);
			ps.setString(2, pass);
			
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				rd= request.getRequestDispatcher("Index.jsp");
			}
			else {
				request.setAttribute("status", "failed");
				rd= request.getRequestDispatcher("login.jsp") ;
			}
			rd.forward(request, response);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
