package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.Mappers.ClientMapper;
import sample.Mappers.PolicyMapper;
import sample.model.Client;
import sample.model.Policy;
import sample.util.ClientConnector;
import sample.util.PolicyConnector;
import sample.util.Utils;

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

    private PolicyMapper currentPolicyMapper = null;

    private ObservableList<String> insuranceTypes;
    private ObservableList<ClientMapper> clients;

    public newPolicyController() {
        currentPolicyMapper = new PolicyMapper(new Policy());
        this.insuranceTypes = FXCollections.observableArrayList();
        this.clients = FXCollections.observableArrayList();
        List<String> insuranceTypesList = PolicyConnector.getInsuranceTypes();
        insuranceTypes.addAll(insuranceTypesList);
        List<Client> clientList = ClientConnector.getClients();
        if (clientList != null) {
            for (Client client : clientList) {
                clients.add(new ClientMapper(client));
            }
        }
        currentPolicyMapper.clientMapperProperty().getValue().clientPhoneNumberProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));

    }

    @FXML
    private void initialize() {
        policyNumberTextField.textProperty().bindBidirectional(currentPolicyMapper.policyNumberProperty());
        agentNameTextField.textProperty().bindBidirectional(currentPolicyMapper.agentNameProperty());
        insuranceCompanyTextField.textProperty().bindBidirectional(currentPolicyMapper.insuranceCompanyProperty());
        insuranceTypeComboBox.setItems(insuranceTypes);
        insuranceTypeComboBox.valueProperty().bindBidirectional(currentPolicyMapper.insuranceTypeProperty());
        beneficiaryTextField.textProperty().bindBidirectional(currentPolicyMapper.beneficiaryProperty());
        grossCommissionTextField.textProperty().bindBidirectional(currentPolicyMapper.grossPremuimProperty());
        expiryDateDatePicker.valueProperty().bindBidirectional(currentPolicyMapper.expiryDateProperty());
        clientComboBox.setItems(clients);
        clientComboBox.setCellFactory(new Callback<ListView<ClientMapper>, ListCell<ClientMapper>>() {
            @Override
            public ListCell<ClientMapper> call(ListView<ClientMapper> param) {
                final ListCell<ClientMapper> cell = new ListCell<ClientMapper>() {
                    @Override
                    protected void updateItem(ClientMapper item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) setText(null);
                        else setText(item.clientNameProperty().getValue());
                    }

                };
                return cell;
            }
        });
        clientComboBox.valueProperty().bindBidirectional(currentPolicyMapper.clientMapperProperty());
        clientPhoneNumberTextField.textProperty().bindBidirectional(currentPolicyMapper.clientMapperProperty().getValue().clientPhoneNumberProperty());


    }

    private List<File> getImages(String title) {
        System.out.println(currentPolicyMapper.clientMapperProperty().getValue().clientPhoneNumberProperty().getValue());
        System.out.println(clientPhoneNumberTextField.textProperty().getValue());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser.showOpenMultipleDialog(Main.primaryStage);

    }

    @FXML
    protected void handlePolicyImageBrowseButton(MouseEvent event) {
        List<File> images = getImages("Policy Images");
        if (images == null) return;
        for (File image : images) {


        }

    }

    @FXML
    protected void handleClaimImageBrowseButton(MouseEvent event) {

        List<File> images = getImages("Claim Images");
        if (images == null) return;
        for (File image : images) {

        }

    }

    @FXML
    protected void handleCollectiveImageBrowseButton(MouseEvent event) {
        List<File> images = getImages("Collective Images");
        if (images == null) return;
        if (images.size() != 1) {
            Alert tooMuchImagesAlert = new Alert(Alert.AlertType.ERROR);
            tooMuchImagesAlert.setTitle("Only one collective Image");
            tooMuchImagesAlert.setHeaderText("you can only choose one collective image");
            tooMuchImagesAlert.showAndWait();
        }
    }

    @FXML
    protected void handleCancelButton(MouseEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }
}