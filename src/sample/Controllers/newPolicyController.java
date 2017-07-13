package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Mappers.ClientMapper;

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


}