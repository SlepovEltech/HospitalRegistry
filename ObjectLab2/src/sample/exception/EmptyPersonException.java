package sample.exception;

import javafx.scene.control.Alert;

public class EmptyPersonException extends  Exception{
    public EmptyPersonException() { super(); }
    public EmptyPersonException(String message) {
        super(message);
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
