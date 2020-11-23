package sample.thread;

import javafx.collections.ObservableList;
import sample.entity.Doctor;
import sample.parser.XmlParser;
import sample.parser.XmlSaver;
import sample.report.HtmlReport;
import org.apache.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class MyThread extends Thread {

    private Integer type;

    Object mutex;

    private ObservableList<Doctor> doctorList;

    private static final Logger log = Logger.getLogger("MyThread.class");


    public MyThread(Object mutex, Integer type ) {
        this.type = type;
        this.mutex = mutex;
    }

    @Override
    public void run(){
        switch (type){
            case 1:
                log.info("Thread Reader start");
                XmlParser newParser = new XmlParser(new File("./doctors.xml"));
                newParser.parseDoctor();
                doctorList = newParser.getDoctorList();
                log.info("Thread Reader end");
                break;
            case 2:
                log.info("Thread Editer start");
                doctorList.add(new Doctor(doctorList.size()+1,"Петров",
                    "Петр", "Петрович", "Врач-диагност", "Не имеется"));
                log.debug("Thread Editer add new row");
                XmlSaver saver = new XmlSaver(doctorList,null, new File("./doctors.xml"));
                try {
                    saver.saveDoctor();
                    log.debug("Thread Editer save changes");
                } catch (ParserConfigurationException e) {
                    log.warn("XML saver crash: ", e);
                }
                log.info("Thread Editer end");
                break;
            case 3:
                log.info("Thread Reporer start");
                HtmlReport report = new HtmlReport(this.doctorList, null, "../DataSrc/ThreadReport");
                report.saveHtml();
                log.info("Thread Reporter end. HTML-report made");
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
