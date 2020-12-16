package sample.entity;

import java.sql.Date;
import java.time.LocalDate;

public class Meeting {
    private Integer id;
    private Integer doctorId;
    private Integer patientId;
    private String patientName;
    private String diagnos;
    private String  date;

    public Meeting(int id, int doctorId, int patientId, String date) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
    }

    public Meeting() {

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientName() { return patientName; }

    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDiagnos() { return diagnos; }

    public void setDiagnos(String diagnos) { this.diagnos = diagnos; }
}
