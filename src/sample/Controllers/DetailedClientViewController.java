package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Mappers.ClientMapper;
import sample.model.Client;

public class DetailedClientViewController {

    @FXML
    private TextField clientNameTextField;
    @FXML private TextField clientPhoneNumberTextField;
    private ClientMapper currentClientMapper;
    @FXML
    private Button cancelButton;
    @FXML private Button saveButton;

    public DetailedClientViewController(){
        currentClientMapper = new ClientMapper(new Client("",""));
    }

    @FXML private void initialize(){
        clientNameTextField.textProperty().bindBidirectional(currentClientMapper.clientNameProperty());
        clientPhoneNumberTextField.textProperty().bindBidirectional(currentClientMapper.clientPhoneNumberProperty());
    }
    public void setClient(Client client){
        if(client==null)
            return;
        currentClientMapper.setClient(client);
    }


    @FXML protected void handleCancelButton(MouseEvent event){
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();

    }
    @FXML protected void handleSaveButton(MouseEvent event){
        if(currentClientMapper.clientNameProperty().getValue().equals(""))
        {
            Alert SuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            SuccessAlert.setTitle("Save Client");
            SuccessAlert.setHeaderText("Please enter Data to save");
            SuccessAlert.showAndWait();
        }
        currentClientMapper.sync();
        if(currentClientMapper.save())
        {
            Alert SuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            SuccessAlert.setTitle("Save Client");
            SuccessAlert.setHeaderText("Success in saving Client");
            SuccessAlert.showAndWait();
        }
        else{
            Alert FailAlert = new Alert(Alert.AlertType.ERROR);
            FailAlert.setTitle("Save Client");
            FailAlert.setHeaderText("Fail to save Client");
            FailAlert.showAndWait();
        }
        Stage currentStage = (Stage) saveButton.getScene().getWindow();
        currentStage.close();


    }

}
