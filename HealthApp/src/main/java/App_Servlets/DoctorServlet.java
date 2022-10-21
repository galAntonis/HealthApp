package App_Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import Util.DbUtil;
import App_Classes.Users;
import App_Classes.Users.Doctor;
import App_Classes.Appointments;

@WebServlet("/DoctorServlet")
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DecimalFormat df = new DecimalFormat("##########");
	private Connection connection;
	
	public DoctorServlet() {
		connection = DbUtil.getConnection();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doctorAmka=request.getParameter("doctorAmka"); 
		String password=request.getParameter("password");
		String requestType= request.getParameter("doctorlogin");
		String address ="";
		HttpSession session=request.getSession();
		if (requestType == null) {
			createDynPage(response, "Invalid request type");
		}
		if	(requestType.equalsIgnoreCase("doclogin")) {
			try {
				PreparedStatement doctorLogin = connection.prepareStatement("select * from doctor where doctorLicenseNumber = "+doctorAmka+" and hashedpassword = '"+getSecurePassword(password)+"'");
				ResultSet rs = doctorLogin.executeQuery();
				if(rs.next()){
					session.setAttribute("name",rs.getString("name"));
					session.setAttribute("amka", doctorAmka);
					
					address = "/doctorHome.jsp";
				}else {
					address = "/error.jsp";	
				}
				rs.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if (requestType.equalsIgnoreCase("setdate")) {
			double docAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Doctor doctor = new Doctor(docAmka);
			String start_Date = request.getParameter("start_date");
			String end_Date = request.getParameter("end_date");
			doctor.setAvailability(start_Date,end_Date);
			try {
				PreparedStatement preparedStatement = connection.prepareStatement("Select * from appointment where DOCTOR_doctorAMKA=? and startSlotTime=? and endSlotTime=?");
				preparedStatement.setDouble(1, docAmka);
				preparedStatement.setString(2, start_Date);
				preparedStatement.setString(3, end_Date);
				ResultSet rs = preparedStatement.executeQuery();
				if(rs.next()) {
					address="doctorHome.jsp";
				}
				else {
					address="error.jsp";
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}

		}
		if (requestType.equalsIgnoreCase("dates")) {
			double doc_Amka = Double.parseDouble(session.getAttribute("amka").toString()); 
			Doctor doctor = new Doctor(doc_Amka);
			address = "doctorDates.jsp";
			String query = "select * from appointment,patient where DOCTOR_doctorAMKA=? and isAvailable=1 and appointment.PATIENT_patientAMKA=patient.patientAMKA and appointment.startSlotTime>CURRENT_TIMESTAMP";
			request.setAttribute("ComingAppointments", doctor.appointments(query));
		}
		if (requestType.equalsIgnoreCase("canceldate")) {
			double doc_Amka = Double.parseDouble(session.getAttribute("amka").toString()); 
			Doctor doctor = new Doctor(doc_Amka);
			String query="select * from appointment,patient where DOCTOR_doctorAMKA=? and isAvailable=1 and appointment.PATIENT_patientAMKA=patient.patientAMKA and DAYOFYEAR(startSlotTime)-DAYOFYEAR(now())>3";
			request.setAttribute("legalCancellation", doctor.appointments(query));
			String searchType= request.getParameter("cancelation");
			if(searchType != null) {
				String[] arrOfStr = searchType.split(",", 2);
				String patAmka = arrOfStr[0];
				String startDate = arrOfStr[1];
				doctor.cancelAppointment(patAmka, startDate);
				address="doctorHome.jsp";
			}else {
				address="doctorCancelDate.jsp";
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}
	
	
	
	private void createDynPage(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>”ˆ‹ÎÏ·</title></head>");
		out.println("<body>");
		out.println("<p>" + message + "</p>");
		out.println("</body></html>");
	}
	 private static String getSecurePassword(String passwordToHash)
	    {
	        String generatedPassword = null;
	        try {
	            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Get the hash's bytes 
	            byte[] bytes = md.digest(passwordToHash.getBytes());
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        } 
	        catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return generatedPassword;
	    }

}
