package sample.parser;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import sample.entity.Doctor;
import sample.entity.Patient;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class XmlSaver {

    @FXML
    private ObservableList<Doctor> doctorList;
    private final File srcFile;
    private ObservableList<Patient> patientList;

    public XmlSaver(ObservableList<Doctor> doctorList, ObservableList<Patient> patientList, File srcFile) {
        this.srcFile = srcFile;
        this.doctorList = doctorList;
        this.patientList = patientList;
    }


    public void saveDoctor() throws ParserConfigurationException {
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.newDocument();
            Node doctorNode = doc.createElement("doctorList");
            doc.appendChild(doctorNode);
            AtomicReference<Integer> newId = new AtomicReference<>(1);
            doctorList.forEach(doctor -> {
                Element docElement = doc.createElement("doctor");
                doctorNode.appendChild(docElement);
                docElement.setAttribute("id", String.valueOf(newId));
                docElement.setAttribute("surname", (String) doctor.getSurname());
                docElement.setAttribute("name", doctor.getName());
                docElement.setAttribute("middleName", doctor.getMiddleName());
                docElement.setAttribute("specialty", doctor.getSpecialty());
                docElement.setAttribute("note", doctor.getNote());
                newId.getAndSet(newId.get() + 1);
            });

            Transformer trans= TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.transform(new DOMSource(doc), new StreamResult(srcFile));
        } catch (Exception e) {e.printStackTrace();}
    }

    public void savePatient() throws ParserConfigurationException {
        try{
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();// Новый экземпляр
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.newDocument();
            Node patientNode = doc.createElement("patientList");
            doc.appendChild(patientNode);
            AtomicReference<Integer> newId = new AtomicReference<>(1);
            patientList.forEach(patient -> {
                Element docElement = doc.createElement("patient");
                patientNode.appendChild(docElement);
                docElement.setAttribute("id", String.valueOf(newId));
                docElement.setAttribute("surname", (String) patient.getSurname());
                docElement.setAttribute("name", patient.getName());
                docElement.setAttribute("middleName", patient.getMiddleName());
                docElement.setAttribute("diagnos", patient.getDiagnos());
                docElement.setAttribute("note", patient.getNote());
                newId.getAndSet(newId.get() + 1);
            });
            Transformer trans= TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.transform(new DOMSource(doc), new StreamResult(srcFile));
        } catch (Exception e) {e.printStackTrace();}
    }
}
