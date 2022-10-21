package App_Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import App_Classes.Users.*;
import Util.DbUtil;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	DecimalFormat df = new DecimalFormat("#######");
	private Connection connection;
	public AdminServlet() {
		connection = DbUtil.getConnection();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String address ="";
		String username = request.getParameter("adminUsername");
		String password = request.getParameter("adminPassword");
		String requestType= request.getParameter("admin");
		HttpSession session=request.getSession();
		if (requestType == null) {
			createDynPage(response, "Invalid request type");
		}
		if	(requestType.equalsIgnoreCase("login")) {
			
			try {
				PreparedStatement patientLogin = connection.prepareStatement("select * from admin where username='"+username+"' and hashedpassword='"+password+"'");
				ResultSet rs = patientLogin.executeQuery();
				if(rs.next()){
					session.setAttribute("adminUsername",rs.getString("username"));
					session.setAttribute("adminPassword",rs.getString("hashedpassword"));
					session.setAttribute("adminid",rs.getString("userid"));
					address = "/adminHome.jsp";
				}else {
					address = "/error.jsp";	
				}
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if (requestType.equalsIgnoreCase("patRegistration")) {
			double patientAmka = Double.parseDouble(request.getParameter("amka").toString());
			Admin admin = new Admin();
			admin.setAmka(patientAmka);
			admin.setName(request.getParameter("name").toString());
			admin.setSurname(request.getParameter("surname").toString());
			admin.setPassword(request.getParameter("password").toString());
			admin.setAge(Integer.parseInt(request.getParameter("age").toString()));
			try {
				admin.addPatient();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (NoSuchProviderException e1) {
				e1.printStackTrace();
			}
			try {
				PreparedStatement preparedStatement = connection.prepareStatement("select * from patient where patientAMKA=?");
				preparedStatement.setDouble(1, admin.getAmka());
				ResultSet rs = preparedStatement.executeQuery();
				if(rs.next()) {
					address="/adminHome.jsp";
				}else {
					address="/error.jsp";
				}
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(requestType.equalsIgnoreCase("docregistration")) {
			double doctorLNumber = Double.parseDouble(request.getParameter("doctorLicenseNumber").toString());
			Admin admin = new Admin();
			admin.setDoctorLNumber(doctorLNumber);
			admin.setName(request.getParameter("name").toString());
			admin.setSurname(request.getParameter("surname").toString());
			admin.setPassword(request.getParameter("password").toString());
			admin.setSpeciality(request.getParameter("speciality").toString());
			admin.setAdminid(session.getAttribute("adminid").toString());
			try {
				admin.addDoctor();
			} catch (NoSuchAlgorithmException | NoSuchProviderException e1) {
				e1.printStackTrace();
			}
			try {
				PreparedStatement preparedStatement = connection.prepareStatement("select * from doctor where doctorLicenseNumber=?");
				preparedStatement.setDouble(1, admin.getDoctorLNumber());
				ResultSet rs = preparedStatement.executeQuery();
				if(rs.next()) {
					address="/adminHome.jsp";
				}else {
					address="/error.jsp";
				}
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(requestType.equalsIgnoreCase("deletedoctor")) {
			String inputPass = request.getParameter("password").toString();
			String checkPass = session.getAttribute("adminPassword").toString();
			if(inputPass.equals(checkPass)) {
				double doctorLNumber = Double.parseDouble(request.getParameter("doctorLicenseNumber").toString());
				Admin admin = new Admin();
				admin.setDoctorLNumber(doctorLNumber);
				admin.deleteDoctor();
				try {
					PreparedStatement preparedStatement = connection.prepareStatement("select * from doctor where doctorLicenseNumber=?");
					preparedStatement.setDouble(1, admin.getDoctorLNumber());
					ResultSet rs = preparedStatement.executeQuery();
					if(rs.next()) {
						address="/error.jsp";					
					}else {
						address="/adminHome.jsp";
					}
					rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(requestType.equalsIgnoreCase("alldoctors")) {
			Admin admin= new Admin();
			address = "/allDoctors.jsp";
			request.setAttribute("allDoctors", admin.showDoctors());
		}
		if(requestType.equalsIgnoreCase("allpatients")) {
			Admin admin= new Admin();
			address = "/allPatients.jsp";
			request.setAttribute("allPatients", admin.showPatients());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		
		dispatcher.forward(request, response);
	}
	private void createDynPage(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Σφάλμα</title></head>");
		out.println("<body>");
		out.println("<p>" + message + "</p>");
		out.println("<a href=\"index.jsp\"> &#60;&#60; Επιστροφή στην αρχική σελίδα </a> ");
		out.println("</body></html>");
	}
}
