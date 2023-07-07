package com.loginapp.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loginapp.DBconnection.DB_connection;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
	//	out.print("<h1 style=\"color: blueviolet;\">Welcome to registration servlet</h1>");
		
		String uname= request.getParameter("name");
		String uemail= request.getParameter("email");
		String upass= request.getParameter("pass");
		String repass= request.getParameter("re_pass");
		String ucontact= request.getParameter("contact");
		RequestDispatcher rd=null;
		
		if(uname==null || uname.equals("")) {
			request.setAttribute("status", "Name cannot be empty");
			rd= request.getRequestDispatcher("registration.jsp") ;
			rd.forward(request, response);
		}
		 if(uemail==null || uemail.equals("")) {
			request.setAttribute("status", "Email cannot be empty");
			rd= request.getRequestDispatcher("registration.jsp") ;
			rd.forward(request, response);
		}
		 if(upass==null || upass.equals("")) {
			request.setAttribute("status", "Password cannot be empty");
			rd= request.getRequestDispatcher("registration.jsp") ;
			rd.forward(request, response);
		}
		 else if(!upass.equals(repass)) {
			 request.setAttribute("status", "Passwords do not match");
				rd= request.getRequestDispatcher("registration.jsp") ;
				rd.forward(request, response);
			 
		 }
		 if(ucontact==null || ucontact.equals("")) {
			request.setAttribute("status", "Contact cannot be empty");
			rd= request.getRequestDispatcher("registration.jsp") ;
			rd.forward(request, response);
		}
		 else if(ucontact.length()>10) {
			 request.setAttribute("status", "Invalid mobile number length");
				rd= request.getRequestDispatcher("registration.jsp") ;
				rd.forward(request, response);	 
		 }
		
		try {
			Connection con= DB_connection.dbconnect();
			PreparedStatement ps= con.prepareStatement("insert into users (uname,email,password,mobile) values(?,?,?,?)");
			ps.setString(1, uname);
			ps.setString(2, uemail);
			ps.setString(3, upass);
			ps.setString(4, ucontact);
			int n= ps.executeUpdate();
			 
			if(n>0) {
				request.setAttribute("status", "success");
				rd = request.getRequestDispatcher("registration.jsp");	
	/*			out.print("<h1 style=\"color: blueviolet;\">"+uname+"</h1>");
				out.print("<h1 style=\"color: blueviolet;\">"+uemail+"</h1>");
				out.print("<h1 style=\"color: blueviolet;\">"+upass+"</h1>");
				out.print("<h1 style=\"color: blueviolet;\">"+ucontact+"</h1>");
				*/
			/*	boolean data= true;
				if(data==true) {
				response.sendRedirect("registration.jsp");
				}*/
			}else {
				request.setAttribute("status", "failed");
			}
			rd.forward(request, response);
			
			con.close();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		doGet(request, response);
	}

}
