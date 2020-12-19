package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.DataAccessor;
import sample.entity.Doctor;
import sample.entity.Meeting;
import sample.entity.Patient;
import sample.exception.EmptyPersonException;

import javax.xml.soap.Text;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class PersonCard {

    @FXML private ImageView avatar;
    private Doctor doctor;
    @FXML private Label docFullName;
    @FXML private Label docSpecialty;
    @FXML private Label docNote;

    @FXML private TableView<Meeting> meetingTable;

    @FXML private TableColumn<Meeting, String> diagnos;
    @FXML private TableColumn<Meeting, String> patientFullName;
    @FXML private TableColumn<Meeting, String> date;

    @FXML private Button addMeeting;
    @FXML private DatePicker datePicker;
    @FXML private TextField timePicker;
    @FXML private ChoiceBox<Patient> patientChoiceBox;

    @FXML private Button deleteMeeting;

    @FXML private VBox info;

    @FXML
    void initialize() {
        initializeTable();
        DataAccessor da = DataAccessor.getDataAccessor();
        addMeeting.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    Meeting newMeet = new Meeting();
                    Patient selected = patientChoiceBox.getValue();
                    String newDate;
                    newDate = datePicker.getValue().toString();
                    String newTime = timePicker.getText();
                    if(selected == null || newDate == null || newTime == null) throw new EmptyPersonException("Нет  информации о новом приеме");
                    String query = doctor.getId()+","+selected.getId()+",'"+newDate+" "+newTime+"'";
                    da.createMeeting(query);
                    newMeet.setPatientName(selected.getFullName());
                    newMeet.setDiagnos(selected.getDiagnos());
                    newMeet.setDate(newDate+" "+newTime);
                    newMeet.setPatientId(selected.getId());
                    newMeet.setDoctorId(doctor.getId());
                    meetingTable.getItems().add(newMeet);
                }catch (EmptyPersonException ex){
                    ex.getAlert();
                }
            }
        });
        deleteMeeting.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Meeting deleted = meetingTable.getSelectionModel().getSelectedItem();
                da.deleteMeeting(deleted);
                meetingTable.getItems().remove(deleted);
            }

        });
    }
    public void dataTransfer(Doctor doctor, ObservableList<Patient> patientList, ObservableList<Meeting> meetingList) {
        this.doctor = doctor;
        docFullName.setText(doctor.getSurname()+" "+doctor.getName()+" "+doctor.getMiddleName());
        docSpecialty.setText(doctor.getSpecialty());
        docNote.setText("Примечение: "+doctor.getNote());

        Comparator<Patient> patientComparator = Comparator.comparing(Patient::getSurname);
        Collections.sort(patientList, patientComparator);

        patientChoiceBox.setItems(patientList);
        String surname = doctor.getSurname();
        Integer imgNumber  = 6+(int) (Math.random() * 5); //Костыль с картинками. Есть 5 мужских фото(6-10) и 5 женских(1-5).
        char lastCh = surname.charAt(surname.length() - 1);  //По умолчанию генерим число от 6 до 10
        if(lastCh == 'а') imgNumber-=5; //Если фамилия врача женская(оканчинвается на "а", то генерируем id от 1 до 5, то есть -5 от исходного)
        File file = new File("src/img/"+imgNumber.toString()+".jpeg");
        Image image = new Image(file.toURI().toString());
        avatar.setImage(image);
        for (Meeting meet: meetingList) {
            if(meet.getDoctorId() == doctor.getId()) {
                for (Patient patient: patientList) {
                    if(patient.getId() == meet.getPatientId()){
                        meet.setPatientName(patient.getSurname()+" "+patient.getName()+" "+patient.getMiddleName());
                        meet.setDiagnos(patient.getDiagnos());
                        meetingTable.getItems().add(meet);
                    }
                }
            }
        }
    }

    public void initializeTable(){
        patientFullName.setCellValueFactory(new PropertyValueFactory<Meeting, String>("patientName"));
        patientFullName.setEditable(true);
        patientFullName.setCellFactory(TextFieldTableCell.forTableColumn());
        patientFullName.setOnEditCommit(e -> e.getRowValue().setDiagnos(e.getNewValue()));

        diagnos.setCellValueFactory(new PropertyValueFactory<Meeting, String>("diagnos"));
        diagnos.setEditable(true);
        diagnos.setCellFactory(TextFieldTableCell.forTableColumn());
        diagnos.setOnEditCommit(e -> e.getRowValue().setDiagnos(e.getNewValue()));

        date.setCellValueFactory(new PropertyValueFactory<Meeting, String>("date"));
        date.setEditable(true);
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setOnEditCommit(e -> e.getRowValue().setDate(e.getNewValue()));
    }

}
