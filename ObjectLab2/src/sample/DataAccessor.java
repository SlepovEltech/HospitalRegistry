package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Doctor;
import sample.entity.Meeting;
import sample.entity.Patient;

import java.sql.*;

public class DataAccessor {

    private static DataAccessor dataAccessor = new DataAccessor("jdbc:postgresql://localhost:5432/hospital",
            "postgres","root");

    private ObservableList<Doctor> doctorList;
    private ObservableList<Patient> patientList;
    private ObservableList<Meeting> meetingList;

    private Connection connection;
    private DataAccessor(String url, String username, String password){
        try{
            this.connection = DriverManager.getConnection(url,username,password);

        }catch (SQLException e){
            System.out.println("NO CONNECTION: "+e);
        }
    }

    public static DataAccessor getDataAccessor(){
        return dataAccessor;
    }

    public void doctorQuery(){
        doctorList = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM doctor ORDER BY id");
            while(res.next()){
                Doctor newDoc = new Doctor(Integer.parseInt(res.getString("id")), res.getString("surname"),
                        res.getString("name"), res.getString("middlename"),
                        res.getString("specialty"), res.getString("note"));
                doctorList.add(newDoc);
            }
        } catch (SQLException throwables) {
            System.out.println("Problems with list");
        }
    }

    public void patientQuery(){
        patientList = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM patient ORDER BY id");
            while(res.next()){
                Patient newPatient = new Patient(Integer.parseInt(res.getString("id")), res.getString("surname"),
                        res.getString("name"), res.getString("middlename"),
                        res.getString("diagnos"), res.getString("note"));
                patientList.add(newPatient);
            }
        } catch (SQLException throwables) {
            System.out.println("Problems with list");
        }
    }

    public void meetingQuery(){
        meetingList = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM meeting ORDER BY id");
            while(res.next()){
                Meeting newMeet = new Meeting(Integer.parseInt(res.getString("id")), Integer.parseInt(res.getString("doctorId")),
                        Integer.parseInt(res.getString("patientId")), res.getString("date"));
                meetingList.add(newMeet);
            }
        } catch (SQLException throwables) {
            System.out.println("Problems with list");
        }
    }

    public void updateDoctor(Doctor doctor){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("UPDATE doctor SET surname='"+doctor.getSurname()+
                    "',name='"+doctor.getName()+"',middlename='"+doctor.getMiddleName()+
                    "',specialty='"+doctor.getSpecialty()+"',note='"+doctor.getNote()+"' WHERE id="+doctor.getId()+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with update "+ throwables.getErrorCode());
        }
    }

    public void updatePatient(Patient patient){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("UPDATE doctor SET surname='"+patient.getSurname()+
                    "',name='"+patient.getName()+"',middlename='"+patient.getMiddleName()+
                    "',specialty='"+patient.getDiagnos()+"',note='"+patient.getNote()+"' WHERE id="+patient.getId()+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with update "+ throwables.getErrorCode());
        }
    }

    public void createDoctor(Doctor doctor){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("INSERT INTO doctor" + " VALUES("+doctor.getId()+",'"+doctor.getSurname()+
                    "','"+doctor.getName()+"', '"+doctor.getMiddleName()+
                    "','"+doctor.getSpecialty()+"', '"+doctor.getNote()+"');");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with create "+ throwables.getErrorCode());
        }
    }
    public void createPatient(Patient patient){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("INSERT INTO patient" + " VALUES("+patient.getId()+",'"+patient.getSurname()+
                    "','"+patient.getName()+"', '"+patient.getMiddleName()+
                    "','"+patient.getDiagnos()+"', '"+patient.getNote()+"');");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with update "+ throwables.getErrorCode());
        }
    }

    public void deleteDoctor(Doctor doctor){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("DELETE FROM doctor WHERE id="+doctor.getId()+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with create "+ throwables.getErrorCode());
        }
    }

    public void deletePatient(Patient patient){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("DELETE FROM patient WHERE id="+patient.getId()+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with update "+ throwables.getErrorCode());
        }
    }

    public void createMeeting(String query){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("INSERT INTO meeting(doctorid, patientid,date) VALUES ("+query+");");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with create Meet "+ throwables.getErrorCode());
        }
    }

    public void deleteMeeting(Meeting meet){
        try {
            Statement st = connection.createStatement();
            int res = st.executeUpdate("DELETE FROM meeting WHERE id="+meet.getId()+";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Problems with delete Meet "+ throwables.getErrorCode());
        }
    }

    public ObservableList<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(ObservableList<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public ObservableList<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(ObservableList<Patient> patientList) {
        this.patientList = patientList;
    }

    public ObservableList<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(ObservableList<Meeting> meetingList) {
        this.meetingList = meetingList;
    }
}
