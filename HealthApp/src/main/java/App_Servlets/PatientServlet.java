package App_Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import App_Classes.Users;
import App_Classes.Users.Patient;
import Util.DbUtil;


@WebServlet("/login")

public class PatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DecimalFormat df = new DecimalFormat("##########");
	private Connection connection;
	
	public PatientServlet() {
		connection = DbUtil.getConnection();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String amka=request.getParameter("amka"); 
		String password=request.getParameter("password");
		
		String address ="";
		String requestType= request.getParameter("formlogin");
		HttpSession session=request.getSession();
		if (requestType == null) {
			createDynPage(response, "Invalid request type");
		}
		
		//LOGIN
		if	(requestType.equalsIgnoreCase("login")) {
			String securePassword = getSecurePassword(password);
			try{
				PreparedStatement patientLogin = connection.prepareStatement("select * from patient where patientAMKA='"+amka+"' and hashedpassword='"+securePassword+"';");
				ResultSet rs = patientLogin.executeQuery();
				if(rs.next()){
					session.setAttribute("username",rs.getString("name"));
					session.setAttribute("password",rs.getString("hashedpassword"));
					session.setAttribute("amka", amka);
					
					address = "/home.jsp";
				}else {
					address = "/error.jsp";	
				}
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		//MY PROFILE
		if (requestType.equalsIgnoreCase("profile")) {
			double patientAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Patient patient = new Patient(patientAmka);
			patient.showPatient();
			session.setAttribute("amka", df.format(patient.getAmka()));
			session.setAttribute("userid", patient.getUserid());
			session.setAttribute("name", patient.getName());
			session.setAttribute("surname", patient.getSurname());
			address = "/profile.jsp";
		}
		
		//APPOINTMENT HISTORY
		if(requestType.equalsIgnoreCase("history")){
			double patientAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Patient patient = new Patient(patientAmka);
			address = "/history.jsp";
			request.setAttribute("AppointmentHistory", patient.history());
			
		}
		
		//CREATE USER ACCOUNT
		if(requestType.equalsIgnoreCase("register")) {
			double patientAmka = Double.parseDouble(request.getParameter("amka").toString());
			Patient patient = new Patient(patientAmka);
			patient.setName(request.getParameter("name").toString());
			patient.setSurname(request.getParameter("surname").toString());
			patient.setPassword(getSecurePassword(request.getParameter("password").toString()));
			patient.setAge(Integer.parseInt(request.getParameter("age").toString()));
			patient.registration();
			try {
				PreparedStatement preparedStatement = connection.prepareStatement("select * from patient where patientAMKA=?");
				preparedStatement.setDouble(1, patient.getAmka());
				ResultSet rs = preparedStatement.executeQuery();
				if(rs.next()) {
					address="/login.jsp";
				}else {
					address="/error.jsp";
				}
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//SEARCH APPOINTMENT BY SPECIALITY
		if(requestType.equalsIgnoreCase("specialitysearch")) {
			double patientAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Patient patient = new Patient(patientAmka);
			address="/appointmentSearch.jsp";
			request.setAttribute("FreeDates", patient.appointmentSearchBySpeciality(request.getParameter("speciality").toString()));
		}
		
		
		if(requestType.equalsIgnoreCase("datecheck")) {
			double patientAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Patient patient = new Patient(patientAmka);
			String searchType= request.getParameter("date");
			address="/appointmentSearch.jsp";
			String[] arrOfStr = searchType.split(",", 2);
			String docAmka = arrOfStr[0];
			String startDate = arrOfStr[1];
			patient.checkAppointment(docAmka, startDate);
			
		}
		
		//COMING DATES
		if(requestType.equalsIgnoreCase("comingdates")) {
			double patientAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Patient patient = new Patient(patientAmka);
			String query = "SELECT * FROM appointment,doctor where appointment.startSlotTime>CURRENT_TIMESTAMP and appointment.PATIENT_patientAMKA=? and doctor.doctorLicenseNumber = appointment.DOCTOR_doctorAMKA and isAvailable=1;";
			request.setAttribute("ComingAppointments", patient.appointments(query));
			address = "comingDates.jsp";
		}
		
		//CANCEL DATE
		if(requestType.equalsIgnoreCase("canceldate")) {
			double patientAmka = Double.parseDouble(session.getAttribute("amka").toString());
			Patient patient = new Patient(patientAmka);
			String query = "SELECT * FROM appointment,doctor where DAYOFYEAR(startSlotTime)-DAYOFYEAR(now())>3 and isAvailable=1 and PATIENT_patientAMKA=? and doctor.doctorLicenseNumber = appointment.DOCTOR_doctorAMKA;";
			request.setAttribute("legalCancellation", patient.appointments(query));
			String searchType= request.getParameter("cancelation");
			if(searchType != null) {
				String[] arrOfStr = searchType.split(",", 2);
				String docAmka = arrOfStr[0];
				String startDate = arrOfStr[1];
				patient.cancelAppointment(docAmka, startDate);
				address="home.jsp";
			}else {
				address="cancelAppointment.jsp";
			}
			
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		
		dispatcher.forward(request, response);
		
	}
	
	//ERROR PAGE
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