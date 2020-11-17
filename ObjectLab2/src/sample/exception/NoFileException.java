package sample.exception;

import javafx.scene.control.Alert;

public class NoFileException extends Exception{
    public NoFileException() { super(); }
    public NoFileException(String fileName) {
        super("Файл для сохрания "+fileName+" не выбран");
    }
    public void getAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(this.getMessage());
        alert.showAndWait();
    }
}

