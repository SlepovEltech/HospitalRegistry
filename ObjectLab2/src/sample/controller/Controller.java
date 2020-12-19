package sample.controller;

import java.io.FileReader;

import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.DataAccessor;
import sample.entity.Meeting;
import sample.report.HtmlReport;
import sample.report.PdfReport;
import sample.entity.Doctor;
import sample.entity.Patient;
import sample.exception.EmptyPersonException;
import sample.exception.NoFileException;
import sample.parser.XmlParser;
import sample.parser.XmlSaver;
import sample.thread.MyThread;

public class Controller {


    private DataAccessor da;

    private ObservableList<Meeting> meetingsList = FXCollections.observableArrayList();

    @FXML private TableView<Doctor> doctorList;

    @FXML private TableColumn<Doctor, Integer> doctorId;
    @FXML private TableColumn<Doctor, String> docSurname;
    @FXML private TableColumn<Doctor, String> docName;
    @FXML private TableColumn<Doctor, String> docMiddleName;
    @FXML private TableColumn<Doctor, String> docSpecialty;
    @FXML private TableColumn<Doctor, String> docNote;

    @FXML private TextField addDocSurname;
    @FXML private TextField addDocName;
    @FXML private TextField addDocMiddle;
    @FXML private TextField addDocSpecialty;
    @FXML private TextField addDocNote;

    @FXML private Button addDoctor;
    @FXML private Button deleteDoctor;

    @FXML private TableView<Patient> patientList;
    @FXML private TableColumn<Patient, Integer> patientId;
    @FXML private TableColumn<Patient, String> patientSurname;
    @FXML private TableColumn<Patient, String> patientName;
    @FXML private TableColumn<Patient, String> patientMiddleName;
    @FXML private TableColumn<Patient, String> patientDiagnos;
    @FXML private TableColumn<Patient, String> patientNote;

    @FXML private TextField addPatSurname;
    @FXML private TextField addPatName;
    @FXML private TextField addPatMiddle;
    @FXML private TextField addPatDiagnos;
    @FXML private TextField addPatNote;

    @FXML private Button addPatient;
    @FXML private Button deletePatient;

    @FXML private Button makePdfDoctor;
    @FXML private Button makePdfPatient;
    @FXML private Button makeHtmlDoctor;
    @FXML private Button makeHtmlPatient;
    @FXML private Button moreDocInfo;

    @FXML
    void initialize() {

        addDoctor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { addDoctor(); }
        });
        addPatient.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int newId = patientList.getItems().size()+1;
                    if(addPatSurname.getText().isEmpty() || addPatName.getText().isEmpty() || addPatMiddle.getText().isEmpty() ||
                            addPatDiagnos.getText().isEmpty() || addPatNote.getText().isEmpty())
                        throw new EmptyPersonException("Пациент содержит пустые поля");
                    System.out.println(addPatSurname.getText());
                    Patient newPatient = new Patient(newId, addPatSurname.getText(), addPatName.getText(),
                            addPatMiddle.getText(), addPatDiagnos.getText(), addPatNote.getText());
                    patientList.getItems().add(newPatient);
                    da.createPatient(newPatient);
                    addPatSurname.clear();
                    addPatName.clear();
                    addPatMiddle.clear();
                    addPatDiagnos.clear();
                    addPatNote.clear();

                    getAlert("Добавление", "Пациент успешно добавлен!");
                }
                catch (EmptyPersonException e) {e.getAlert();}

            }
        });

        deleteDoctor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = doctorList.getSelectionModel().getSelectedIndex();
                da.deleteDoctor(doctorList.getItems().get(index));
                doctorList.getItems().remove(index);
                doctorList.refresh();
                getAlert("Доктор", "Выбранный доктор успешно исключен из таблицы");
            }
        });
        deletePatient.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = patientList.getSelectionModel().getSelectedIndex();
                da.deletePatient(patientList.getItems().get(index));
                patientList.getItems().remove(index);
                patientList.refresh();
                getAlert("Пациент", "Выбранный пациент успешно исключен из таблицы");
            }
        });

        moreDocInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../personcard.fxml"));
                    Parent root = fxmlLoader.load();
                    PersonCard cardController = fxmlLoader.getController();
                    Doctor selectedDoc = doctorList.getSelectionModel().getSelectedItem();
                    if(selectedDoc == null)  throw new EmptyPersonException("Не выбран доктор для просмотра личной карточки");
                    cardController.dataTransfer(selectedDoc, patientList.getItems(), meetingsList);

                    Scene scene = new Scene(root, 800, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Подробная информация");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            da.meetingQuery();
                            meetingsList.setAll(da.getMeetingList());
//
                        }
                    });
                } catch (EmptyPersonException e) {
                    e.getAlert();
                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }
        });

        makePdfDoctor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)  {
                PdfReport report = new PdfReport(doctorList.getItems(), null, "../DataSrc/DoctorDataPdf");
                try{ report.pdfSave();}
                catch (NoFileException ex) {ex.getAlertMessage();}
                getAlert("PDF", "Отчет построен");
            }
        });
        makePdfPatient.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PdfReport report = new PdfReport(null, patientList.getItems(), "../DataSrc/PatientDataPdf");
                try{ report.pdfSave();}
                catch (NoFileException ex) {ex.getAlertMessage();}
                getAlert("PDF", "Отчет построен");
            }
        });

        makeHtmlDoctor.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HtmlReport report = new HtmlReport(doctorList.getItems(), null, "../DataSrc/DoctorDataHtml");
                report.saveHtml();
                getAlert("HTML", "Отчет построен");
            }
        });
        makeHtmlPatient.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HtmlReport report = new HtmlReport(null, patientList.getItems(), "../DataSrc/PatientDataHtml");
                report.saveHtml();
                getAlert("HTML", "Отчет построен");
            }
        });
        da =  DataAccessor.getDataAccessor();
        da.doctorQuery();
        da.meetingQuery();
        da.patientQuery();

        //Set tables editable
        doctorList.setEditable(true);
        patientList.setEditable(true);

        doctorId.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));

        docSurname.setCellValueFactory(new PropertyValueFactory<Doctor, String>("surname"));
        docSurname.setEditable(true);
        docSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        docSurname.setOnEditCommit(e -> {
            e.getRowValue().setSurname(e.getNewValue());
            da.updateDoctor(e.getRowValue());
        });

        docName.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        docName.setEditable(true);
        docName.setCellFactory(TextFieldTableCell.forTableColumn());
        docName.setOnEditCommit(e -> {
            e.getRowValue().setName(e.getNewValue());
            da.updateDoctor(e.getRowValue());
        });

        docMiddleName.setCellValueFactory(new PropertyValueFactory<Doctor, String>("middleName"));
        docMiddleName.setEditable(true);
        docMiddleName.setCellFactory(TextFieldTableCell.forTableColumn());
        docMiddleName.setOnEditCommit(e -> {
            e.getRowValue().setMiddleName(e.getNewValue());
            da.updateDoctor(e.getRowValue());
        });

        docSpecialty.setCellValueFactory(new PropertyValueFactory<Doctor, String>("specialty"));
        docSpecialty.setEditable(true);
        docSpecialty.setCellFactory(TextFieldTableCell.forTableColumn());
        docSpecialty.setOnEditCommit(e -> {
            e.getRowValue().setSpecialty(e.getNewValue());
            da.updateDoctor(e.getRowValue());
        });

        docNote.setCellValueFactory(new PropertyValueFactory<Doctor, String>("note"));
        docNote.setEditable(true);
        docNote.setCellFactory(TextFieldTableCell.forTableColumn());
        docNote.setOnEditCommit(e -> {
            e.getRowValue().setNote(e.getNewValue());
            da.updateDoctor(e.getRowValue());
        });

        patientId.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));

        patientSurname.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
        patientSurname.setEditable(true);
        patientSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        patientSurname.setOnEditCommit(e -> {
            e.getRowValue().setSurname(e.getNewValue());
            da.updatePatient(e.getRowValue());
        });

        patientName.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        patientName.setEditable(true);
        patientName.setCellFactory(TextFieldTableCell.forTableColumn());
        patientName.setOnEditCommit(e -> {
            e.getRowValue().setName(e.getNewValue());
        });

        patientMiddleName.setCellValueFactory(new PropertyValueFactory<Patient, String>("middleName"));
        patientMiddleName.setEditable(true);
        patientMiddleName.setCellFactory(TextFieldTableCell.forTableColumn());
        patientMiddleName.setOnEditCommit(e -> {
            e.getRowValue().setMiddleName(e.getNewValue());
            da.updatePatient(e.getRowValue());
        });

        patientDiagnos.setCellValueFactory(new PropertyValueFactory<Patient, String>("diagnos"));
        patientDiagnos.setEditable(true);
        patientDiagnos.setCellFactory(TextFieldTableCell.forTableColumn());
        patientDiagnos.setOnEditCommit(e -> {
            e.getRowValue().setDiagnos(e.getNewValue());
            da.updatePatient(e.getRowValue());
        });

        patientNote.setCellValueFactory(new PropertyValueFactory<Patient, String>("note"));
        patientNote.setEditable(true);
        patientNote.setCellFactory(TextFieldTableCell.forTableColumn());
        patientNote.setOnEditCommit(e -> {
            e.getRowValue().setNote(e.getNewValue());
            da.updatePatient(e.getRowValue());
        });


        doctorList.setItems(da.getDoctorList());
        patientList.setItems(da.getPatientList());
        meetingsList.setAll(da.getMeetingList());
    }

    public void getAlert(String header,String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addDoctor(){
        try{
            if(addDocSurname.getText().isEmpty() || addDocName.getText().isEmpty() || addDocMiddle.getText().isEmpty() ||
                    addDocSpecialty.getText().isEmpty() || addDocNote.getText().isEmpty())
                throw new EmptyPersonException("Доктор содержит пустые поля");
            int newId = doctorList.getItems().size()+1;
            Doctor newDoctor = new Doctor(newId, addDocSurname.getText(), addDocName.getText(),
                    addDocMiddle.getText(), addDocSpecialty.getText(), addDocNote.getText());
            doctorList.getItems().add(newDoctor);

            da.createDoctor(newDoctor);
            addDocSurname.clear();
            addDocName.clear();
            addDocMiddle.clear();
            addDocSpecialty.clear();
            addDocNote.clear();
            getAlert("Врач", "Врач успешно добавлен!");
        }
        catch (EmptyPersonException e) {e.getAlert();}
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }
}
