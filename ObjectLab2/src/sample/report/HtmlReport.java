package sample.report;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.entity.Doctor;
import sample.entity.Patient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.table.DefaultTableModel;


public class HtmlReport {

    private ObservableList<Doctor> doctorList;
    private ObservableList<Patient> patientList;
    private String fileName;

    public HtmlReport(ObservableList<Doctor> doctorList, ObservableList<Patient> patientList, String fileName){
        this.doctorList = doctorList;
        this.patientList = patientList;
        this.fileName = fileName;
    }

    public void saveHtml(){

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fileName+".html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println("<TABLE BORDER><TR><TH>Фамилия</TH><TH>Имя</TH><TH>Отчество</TH><TH>Диагноз</TH>" +
                "<TH>Примечание</TH></TR>");
        if(doctorList != null){
            PrintWriter finalPw = pw;
            doctorList.forEach(doc ->{
                finalPw.println("<TR><TD>" + doc.getSurname()+"</TD>"+
                            "<TD>"+doc.getName()+"</TD>"+
                            "<TD>"+doc.getMiddleName()+"</TD>"+
                            "<TD>"+doc.getSpecialty()+"</TD>"+
                            "<TD>"+doc.getNote()+"</TD></TR>");
            });
        }
        if(patientList != null){
            PrintWriter finalPw1 = pw;
            patientList.forEach(patient ->{
                finalPw1.println("<TR><TD>" + patient.getSurname()+"</TD>"+
                            "<TD>"+patient.getName()+"</TD>"+
                            "<TD>"+patient.getMiddleName()+"</TD>"+
                            "<TD>"+patient.getDiagnos()+"</TD>"+
                            "<TD>"+patient.getNote()+"</TD></TR>");
            });
        }
        pw.println("</TABLE>");
        pw.close();
    }
}

