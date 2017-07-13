package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sample.Main;
import sample.Mappers.ClientMapper;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * Created by hassan on 7/13/17.
 */
public class newPolicyController {

    @FXML
    private TextField policyNumberTextField;
    @FXML
    private TextField agentNameTextField;
    @FXML
    private TextField insuranceCompanyTextField;
    @FXML
    private ComboBox<String> insuranceTypeComboBox;
    @FXML
    private TextField beneficiaryTextField;
    @FXML
    private ComboBox<ClientMapper> clientComboBox;
    @FXML
    private TextField grossPremiumTextField;
    @FXML
    private TextField specialDiscountTextField;
    @FXML
    private TextField netPremiumTextField;
    @FXML
    private TextField grossCommissionTextField;
    @FXML
    private ComboBox<String> taxesComboBox;
    @FXML
    private DatePicker expiryDateDatePicker;
    @FXML
    private PasswordField commissionPasswordField;
    @FXML
    private TextField sumInsuredTextField;
    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    private ComboBox<String> collectiveComboBox;
    @FXML
    private ComboBox<String> policyStatusComboBox;
    @FXML
    private ComboBox<String> endorsementNumberComboBox;
    @FXML
    private TextField clientPhoneNumberTextField;
    @FXML
    private ListView<String> policyImageListView;
    @FXML
    private ListView<String> claimImageListView;
    @FXML
    private ListView<String> collectiveImageTextField;
    @FXML
    private Button policyImageBrowseButton;
    @FXML
    private Button claimImageBrowseButton;
    @FXML
    private Button collectiveImageBrowseButton;
    @FXML
    private Button addClientButton;
    @FXML
    private Button editClientButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    newPolicyController(){

    }

    @FXML  private void initialize(){

    }

    private List<File> getImages (String title){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser.showOpenMultipleDialog(Main.primaryStage);

    }

   /* @FXML protected void handlePolicyImageBrowseButton (MouseEvent event)
    {
        List<File> images = getImages("Policy Images");

    }*/

}