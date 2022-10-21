package App_Classes;

public class Appointments {
	private String date, start_date, end_date, doctorName, doctorSurname, patientName, patientSurname, doctorSpeciality;
	private Double patientAmka, doctorAmka;
	private int isAvailable, age;
	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getDoctorSpeciality() {
		return doctorSpeciality;
	}


	public void setDoctorSpeciality(String doctorSpeciality) {
		this.doctorSpeciality = doctorSpeciality;
	}

	
	public String getPatientName() {
		return patientName;
	}


	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}


	public String getPatientSurname() {
		return patientSurname;
	}


	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}


	public Appointments() {
		super();
	}
	
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorSurname() {
		return doctorSurname;
	}
	public void setDoctorSurname(String doctorSurname) {
		this.doctorSurname = doctorSurname;
	}

	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public double getPatientAmka() {
		return patientAmka;
	}
	public void setPatientAmka(double patientAmka) {
		this.patientAmka = patientAmka;
	}
	public double getDoctorAmka() {
		return doctorAmka;
	}
	public void setDoctorAmka(double doctorAmka) {
		this.doctorAmka = doctorAmka;
	}
	public int getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
