package App_Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import App_Classes.Appointments;
import App_Classes.Users.Patient;
import Util.DbUtil;


public class Users {
	private static Connection connection;
	DecimalFormat df = new DecimalFormat("#######");
	private String userid, password, name, surname, speciality, adminid;
	private int age;
	private double amka,doctorLNumber;

    public Users(){
    	connection = DbUtil.getConnection();
    }
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAmka() {
        return amka;
    }

    public void setAmka(double amka) {
        this.amka = amka;
    }
	public String getAdminid() {
		return adminid;
	}
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
 
    public double getDoctorLNumber() {
		return doctorLNumber;
	}
	public void setDoctorLNumber(double doctorLNumber) {
		this.doctorLNumber = doctorLNumber;
	}
	
    public Users(String speciality){

    }
    public Users(double AMKA){

    } 
    
    

    public static class Patient extends Users{
    	DecimalFormat df = new DecimalFormat("#######");

    	public void registration(){
    		Random rand = new Random();
        	try {
        		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patient (patientAMKA, userid, hashedpassword, name, surname, age) VALUES (?,?,?,?,?,?);");
                preparedStatement.setDouble(1, this.getAmka());
                preparedStatement.setInt(2, rand.nextInt(10000));
                preparedStatement.setString(3, this.getPassword());
                preparedStatement.setString(4, this.getName());
                preparedStatement.setString(5, this.getSurname());
                preparedStatement.setInt(6, this.getAge());
                preparedStatement.executeUpdate();
                preparedStatement.close();

        	}catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
        
    	public void showPatient(){
        	try {
        		PreparedStatement preparedStatement = connection.prepareStatement("select * from patient where patientAMKA='"+this.getAmka()+"';");
        		ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					this.setUserid(rs.getString("userid"));
					this.setName(rs.getString("name"));
					this.setSurname(rs.getString("surname"));
				}
				rs.close();
    		}catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
        
        
        public List<Appointments> appointmentSearchBySpeciality(String speciality){
        	List<Appointments> FreeDates = new ArrayList<Appointments>();
        	try {
        		PreparedStatement preparedStatement = connection.prepareStatement("select * from appointment,doctor where  doctor.doctorLicenseNumber = appointment.DOCTOR_doctorAMKA and doctor.speciality = ? and appointment.isAvailable=0;");
        		preparedStatement.setString(1, speciality);
        		ResultSet rs = preparedStatement.executeQuery();
        		while(rs.next()) {
					Appointments appointment = new Appointments();
					appointment.setDoctorAmka(rs.getDouble("DOCTOR_doctorAMKA"));
					appointment.setDoctorName(rs.getString("name"));
					appointment.setDoctorSurname(rs.getString("surname"));
					appointment.setDoctorSpeciality(rs.getString("speciality"));
					appointment.setStart_date(rs.getTimestamp("startSlotTime").toString());
					appointment.setEnd_date(rs.getTimestamp("endSlotTime").toString());
					FreeDates.add(appointment);
				}
        		rs.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        	return FreeDates;
        }
        
        public void checkAppointment(String docAmka, String start_date) {
        	try {
				PreparedStatement preparedStatement = connection.prepareStatement("UPDATE appointment SET date=CURRENT_TIMESTAMP, PATIENT_patientAMKA=?, isAvailable=1 where DOCTOR_doctorAMKA=? and startSlotTime=?");
				
				preparedStatement.setDouble(1, this.getAmka());
                preparedStatement.setString(2, docAmka);
                preparedStatement.setString(3, start_date);
                preparedStatement.executeUpdate();
                preparedStatement.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
        }
        public List<Appointments> appointments(String query){
        	List<Appointments> ComingAppointments = new ArrayList<Appointments>();
        	try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setDouble(1, this.getAmka());
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					Appointments appointment = new Appointments();
					appointment.setPatientAmka(rs.getDouble("PATIENT_patientAMKA"));
					appointment.setDoctorAmka(rs.getDouble("DOCTOR_doctorAMKA"));
					appointment.setDoctorName(rs.getString("name"));
					appointment.setDoctorSurname(rs.getString("surname"));
					appointment.setDate(rs.getTimestamp("date").toString());
					appointment.setStart_date(rs.getTimestamp("startSlotTime").toString());
					appointment.setEnd_date(rs.getTimestamp("endSlotTime").toString());
					ComingAppointments.add(appointment);
				}
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
        	return ComingAppointments;
        }
        
        public void cancelAppointment(String docAmka, String startDate){
            try {
            	PreparedStatement preparedStatement = connection.prepareStatement("UPDATE appointment SET date=NULL, PATIENT_patientAMKA=NULL, isAvailable=0 WHERE DOCTOR_doctorAMKA=? and startSlotTime=? and DAYOFYEAR(startSlotTime)-DAYOFYEAR(now())>3");
            	preparedStatement.setString(1, docAmka);
            	preparedStatement.setString(2, startDate);
            	preparedStatement.executeUpdate();
                preparedStatement.close();
  
            }catch(SQLException e) {
            	e.printStackTrace();
            }
            
        }
        public List<Appointments> history(){
        	List<Appointments> AppointmentHistory = new ArrayList<Appointments>();
        	try {
        		PreparedStatement preparedStatement = connection.prepareStatement("select appointment.PATIENT_patientAMKA, appointment.DOCTOR_doctorAMKA,  doctor.name, doctor.surname, appointment.date, appointment.startSlotTime, appointment.endSlotTime "
        				+ "from appointment, doctor where PATIENT_patientAMKA = ? and doctor.doctorLicenseNumber = appointment.DOCTOR_doctorAMKA");
        		preparedStatement.setDouble(1, this.getAmka());
        		ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					Appointments appointment = new Appointments();
					appointment.setPatientAmka(rs.getDouble("PATIENT_patientAMKA"));
					appointment.setDoctorAmka(rs.getDouble("DOCTOR_doctorAMKA"));
					appointment.setDoctorName(rs.getString("name"));
					appointment.setDoctorSurname(rs.getString("surname"));
					appointment.setDate(rs.getTimestamp("date").toString());
					appointment.setStart_date(rs.getTimestamp("startSlotTime").toString());
					appointment.setEnd_date(rs.getTimestamp("endSlotTime").toString());
					AppointmentHistory.add(appointment);
				}
				rs.close();
    		}catch(SQLException e) {
    			e.printStackTrace();
        	}
        	return AppointmentHistory;
        }

        public Patient(final double AMKA) {
            super(AMKA);
            setAmka(AMKA);
            connection = DbUtil.getConnection();
        }
    }


    public static class Doctor extends Users{
    	
        public void setAvailability(String start_date, String end_date){
        	Appointments appointment = new Appointments();
    		appointment.setStart_date(start_date);
    		appointment.setEnd_date(end_date);
        	try {
        		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO appointment (startSlotTime, endSlotTime, DOCTOR_doctorAMKA, isAvailable) VALUES (?,?,?,?);");
                preparedStatement.setString(1, appointment.getStart_date());
                preparedStatement.setString(2, appointment.getEnd_date());
                preparedStatement.setDouble(3, this.getDoctorLNumber());
                preparedStatement.setInt(4, 0); 		
                preparedStatement.executeUpdate();
                preparedStatement.close();
        	}catch (SQLException e) {
        		e.printStackTrace();
        	}
        }

        public List<Appointments> appointments(String query){
        	List<Appointments> ComingAppointments = new ArrayList<Appointments>();
            try {
            	PreparedStatement preparedStatement = connection.prepareStatement(query);
            	preparedStatement.setDouble(1, this.getDoctorLNumber());
            	ResultSet rs = preparedStatement.executeQuery();
            	while(rs.next()) {
            		Appointments appointment = new Appointments();
            		appointment.setPatientAmka(rs.getDouble("PATIENT_patientAMKA"));
            		appointment.setPatientName(rs.getString("name"));
            		appointment.setPatientSurname(rs.getString("surname"));
            		appointment.setAge(rs.getInt("age"));
            		appointment.setStart_date(rs.getTimestamp("startSlotTime").toString());
            		appointment.setEnd_date(rs.getTimestamp("endSlotTime").toString()); 
            		ComingAppointments.add(appointment);
				}
				rs.close();
            }catch(SQLException e) {
            	e.printStackTrace();
            }
            return ComingAppointments;
        }
        
        public void cancelAppointment(String patAmka, String startDate){
            try {
            	PreparedStatement preparedStatement = connection.prepareStatement("DELETE from appointment where DOCTOR_doctorAMKA=? and PATIENT_patientAMKA=? and startSlotTime=?");
            	preparedStatement.setDouble(1,this.getDoctorLNumber());
            	preparedStatement.setString(2,patAmka);
            	preparedStatement.setString(3,startDate);
            	preparedStatement.executeUpdate();
                preparedStatement.close();
            }catch(SQLException e) {
            	e.printStackTrace();
            }
        }

        public Doctor(double doctorLNumber) {
            super(doctorLNumber);
            setDoctorLNumber(doctorLNumber);
            connection = DbUtil.getConnection();
        }
    }

    public static class Admin extends Users{

    	public void addDoctor() throws NoSuchAlgorithmException, NoSuchProviderException{
        	try {
        		Random rand = new Random();
        		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO doctor (doctorLicenseNumber, userid, hashedpassword, name, surname, speciality, ADMIN_userid) VALUES (?,?,?,?,?,?,?);");
        		String password= getSecurePassword(this.getPassword());
        		preparedStatement.setDouble(1, this.getDoctorLNumber());
                preparedStatement.setInt(2, rand.nextInt(10000));
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, this.getName());
                preparedStatement.setString(5, this.getSurname());
                preparedStatement.setString(6, this.getSpeciality());
                preparedStatement.setString(7, this.getAdminid());
                preparedStatement.executeUpdate();
                preparedStatement.close();

        	}catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
        public void addPatient() throws NoSuchAlgorithmException, NoSuchProviderException {
        	try {
        		Random rand = new Random();
        		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patient (patientAMKA, userid, hashedpassword, name, surname, age) VALUES (?,?,?,?,?,?);");
        		String password= getSecurePassword(this.getPassword());
        		preparedStatement.setDouble(1, this.getAmka());
                preparedStatement.setInt(2, rand.nextInt(10000));
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, this.getName());
                preparedStatement.setString(5, this.getSurname());
                preparedStatement.setInt(6, this.getAge());
                preparedStatement.executeUpdate();
                preparedStatement.close();

        	}catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
        public void deleteDoctor(){
        	try {
        		PreparedStatement preparedStatement = connection.prepareStatement("DELETE from doctor where doctorLicenseNumber=?;");
        		preparedStatement.setDouble(1, this.getDoctorLNumber());
        		preparedStatement.executeUpdate();
                preparedStatement.close();
        	}catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
        public List<Doctor> showDoctors(){
        	List<Doctor> allDoctors = new ArrayList<Doctor>();
        	try {
            	PreparedStatement preparedStatement = connection.prepareStatement("Select * from doctor");
            	ResultSet rs = preparedStatement.executeQuery();
            	while(rs.next()) {
            		Doctor doctor = new Doctor(rs.getDouble("doctorLicenseNumber"));
            		doctor.setName(rs.getString("name"));
            		doctor.setSurname(rs.getString("surname"));
            		doctor.setSpeciality(rs.getString("speciality"));
            		allDoctors.add(doctor);
            	}
            	rs.close();
            }catch(SQLException e) {
            	e.printStackTrace();
            }
            return allDoctors;
        }
        public List<Patient> showPatients(){
        	List<Patient> allPatients = new ArrayList<Patient>();
        	try {
            	PreparedStatement preparedStatement = connection.prepareStatement("Select * from patient");
            	ResultSet rs = preparedStatement.executeQuery();
            	while(rs.next()) {
            		Patient patient = new Patient(rs.getDouble("patientAMKA"));
            		patient.setName(rs.getString("name"));
            		patient.setSurname(rs.getString("surname"));
            		patient.setAge(rs.getInt("age"));
            		allPatients.add(patient);
            	}
            	rs.close();
            }catch(SQLException e) {
            	e.printStackTrace();
            }
            return allPatients;
        }
        public Admin(){

        }

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