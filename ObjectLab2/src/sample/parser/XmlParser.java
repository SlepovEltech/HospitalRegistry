package sample.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.entity.Doctor;
import sample.entity.Patient;

import java.io.File;

public class XmlParser {

    private File srcFile;
    private ObservableList<Doctor> doctorData = FXCollections.observableArrayList();
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();

    public XmlParser(File srcFile) {
        this.srcFile = srcFile;
    }

    public void parseDoctor()
    {
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
            // Создает пустой документ
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.parse(this.srcFile);
            doc.getDocumentElement().normalize();
            NodeList nodeDotors = doc.getElementsByTagName("doctor");
            for ( int i = 0; i < nodeDotors.getLength(); i++) {
                Doctor newDoctor = new Doctor();
                Node elem = nodeDotors.item(i);
                NamedNodeMap attrs = elem.getAttributes();
                newDoctor.setId(Integer.parseInt((attrs.getNamedItem("id").getNodeValue())));
                newDoctor.setSurname(attrs.getNamedItem("surname").getNodeValue());
                newDoctor.setName(attrs.getNamedItem("name").getNodeValue());
                newDoctor.setMiddleName(attrs.getNamedItem("middleName").getNodeValue());
                newDoctor.setSpecialty(attrs.getNamedItem("specialty").getNodeValue());
                newDoctor.setNote(attrs.getNamedItem("note").getNodeValue());
                doctorData.add(newDoctor);
            }
        } catch(Exception e){ e.printStackTrace();}
    }

    public void parsePatient() {
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
            // Создает пустой документ
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.parse(this.srcFile);
            doc.getDocumentElement().normalize();
            NodeList nodePatient = doc.getElementsByTagName("patient");
            for ( int i = 0; i < nodePatient.getLength(); i++) {
                Patient newPatient = new Patient();
                Node elem = nodePatient.item(i);
                NamedNodeMap attrs = elem.getAttributes();
                newPatient.setId(Integer.parseInt((attrs.getNamedItem("id").getNodeValue())));
                newPatient.setSurname(attrs.getNamedItem("surname").getNodeValue());
                newPatient.setName(attrs.getNamedItem("name").getNodeValue());
                newPatient.setMiddleName(attrs.getNamedItem("middleName").getNodeValue());
                newPatient.setDiagnos(attrs.getNamedItem("diagnos").getNodeValue());
                newPatient.setNote(attrs.getNamedItem("note").getNodeValue());
                patientData.add(newPatient);
            }
        } catch(Exception e){ e.printStackTrace();}
    }

    public ObservableList<Doctor> getDoctorList(){
        return doctorData;
    }

    public ObservableList<Patient> getPatientList(){
        return patientData;
    }

}
