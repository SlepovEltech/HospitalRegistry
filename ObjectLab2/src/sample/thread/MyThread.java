package sample.thread;

import javafx.collections.ObservableList;
import sample.entity.Doctor;
import sample.parser.XmlParser;
import sample.parser.XmlSaver;
import sample.report.HtmlReport;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class MyThread extends Thread {

    private Integer type;

    Object mutex;

    private ObservableList<Doctor> doctorList;

    public MyThread(Object mutex, Integer type ) {
        this.type = type;
        this.mutex = mutex;
    }

    @Override
    public void run(){
        switch (type){
            case 1:
                System.out.println("Thread "+type);
                XmlParser newParser = new XmlParser(new File("./doctors.xml"));
                newParser.parseDoctor();
                doctorList = newParser.getDoctorList();
                break;
            case 2:
                System.out.println("Thread "+type);
                doctorList.add(new Doctor(doctorList.size()+1,"Петров",
                    "Петр", "Петрович", "Врач-диагност", "Не имеется"));
                XmlSaver saver = new XmlSaver(doctorList,null, new File("./doctors.xml"));
                try {
                    saver.saveDoctor();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                System.out.println("Изменения успешно сохрнены в исходный файл");
                break;
            case 3:
                System.out.println("Thread "+type);
                HtmlReport report = new HtmlReport(this.doctorList, null, "../DataSrc/ThreadReport");
                report.saveHtml();
                System.out.println("Html Отчет построен");
                break;
        }
    }

    public ObservableList<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(ObservableList<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

}
