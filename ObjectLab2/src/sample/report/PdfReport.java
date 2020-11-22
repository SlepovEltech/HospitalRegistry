package sample.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.entity.Doctor;
import sample.entity.Patient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfReport {

    private ObservableList<Doctor> doctorList;
    private ObservableList<Patient> patientList;
    private String fileName;

    public PdfReport(ObservableList<Doctor> doctorList, ObservableList<Patient> patientList, String fileName){
        this.doctorList = doctorList;
        this.patientList = patientList;
        this.fileName = fileName;
    }

    public void pdfSave( ) {

        Document document = new Document(PageSize.A4, 25, 25, 25, 25);
        PdfPTable table = new PdfPTable(5);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        BaseFont bfComic = null, bfComicBold = null;
        try {
            bfComic = BaseFont.createFont("/System/Library/Fonts/Supplemental/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Font font = new Font(bfComic, 12);
        Font fontBold = new Font(bfComic, 12);
        fontBold.setStyle(Font.BOLDITALIC);
        if(doctorList != null){
            table.addCell(new PdfPCell(new Phrase("Фамилия",fontBold)));
            table.addCell(new PdfPCell(new Phrase("Имя",fontBold )));
            table.addCell(new PdfPCell(new Phrase("Отчество",fontBold )));
            table.addCell(new PdfPCell(new Phrase("Специализация",fontBold )));
            table.addCell(new PdfPCell(new Phrase("Примечание",fontBold )));
            doctorList.forEach(doc -> {
                table.addCell(new Phrase((String) doc.getSurname(),font));
                table.addCell(new Phrase((String) doc.getName(),font));
                table.addCell(new Phrase((String) doc.getMiddleName(),font));
                table.addCell(new Phrase((String) doc.getSpecialty(),font));
                table.addCell(new Phrase((String) doc.getNote(),font)); });

        }
        if(patientList != null){
            table.addCell(new PdfPCell(new Phrase("Фамилия",fontBold)));
            table.addCell(new PdfPCell(new Phrase("Имя",fontBold )));
            table.addCell(new PdfPCell(new Phrase("Отчество",fontBold )));
            table.addCell(new PdfPCell(new Phrase("Диагноз",fontBold )));
            table.addCell(new PdfPCell(new Phrase("Примечание",fontBold )));
            patientList.forEach(patient -> {
                table.addCell(new Phrase((String) patient.getSurname(),font));
                table.addCell(new Phrase((String) patient.getName(),font));
                table.addCell(new Phrase((String) patient.getMiddleName(),font));
                table.addCell(new Phrase((String) patient.getDiagnos(),font));
                table.addCell(new Phrase((String) patient.getNote(),font)); });
        }


        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }
}
