package sample.Controllers;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Created by hassan on 7/13/17.
 */
public class NewPolicyController {

    private ObservableList<PolicyMapper> policyMappers;

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
    private ComboBox<String> clientComboBox;
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
    private Button imageBrowseButton;
    @FXML
    private Button addClientButton;
    @FXML
    private Button editClientButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> imageTypeComboBox;
    @FXML
    private ListView<String> imagePathListView;

    @FXML
    private TextField paidClaimsTextField;

    @FXML
    Button deleteImageButton;

    private PolicyMapper currentPolicyMapper = null;

    private ObservableList<String> insuranceTypes;
    private ObservableList<String> clients;
    private ObservableList<String> collectiveList;
    private ObservableList<String> currencyList;
    private ObservableList<String> policyStatus;
    private ObservableList<String> taxes;
    private ObservableList<String> policyImagePath, claimImagePath, collectiveImagePath;
    private ObservableList<String> selectedImageList;
    private ObservableList<String> imageTypes;
    private ObservableList<String> endorsementNumbers;
    private List<Client> clientList;

    private Callback<ListView<String>, ListCell<String>> callback = new Callback<ListView<String>, ListCell<String>>() {
        @Override
        public ListCell<String> call(ListView<String> param) {
            final ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null)
                        setText(null);
                    else setText(item);
                }
            };
            return cell;
        }
    };

    public NewPolicyController() {

        currentPolicyMapper = new PolicyMapper(new Policy());
        this.insuranceTypes = FXCollections.observableArrayList();
        this.clients = FXCollections.observableArrayList();
        List<String> insuranceTypesList = PolicyConnector.getInsuranceTypes();
        insuranceTypes.addAll(insuranceTypesList);
        clientList = ClientConnector.getClients();
        if (clientList != null) {
            for (Client client : clientList) {
                clients.add(client.getClientName());
            }
        }
        policyStatus = FXCollections.observableArrayList();
        policyStatus.addAll(PolicyConnector.getPolicyStatus());
        collectiveList = FXCollections.observableArrayList();
        collectiveList.addAll("Cache", "Check", "None");
        currencyList = FXCollections.observableArrayList();
        currencyList.addAll("EGP", "USD", "EUR");
        taxes = FXCollections.observableArrayList();
        taxes.addAll("20%", "22.5%");
        policyImagePath = FXCollections.observableArrayList();
        claimImagePath = FXCollections.observableArrayList();
        collectiveImagePath = FXCollections.observableArrayList();
        imageTypes = FXCollections.observableArrayList("Policy Image", "Claim Image", "Collective Image");
        endorsementNumbers = FXCollections.observableArrayList();
        for (Policy policy : PolicyConnector.policies) {
            endorsementNumbers.add(policy.getPolicyNumber());
        }
    }

    @FXML
    private void initialize() {

        imageTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Policy Image"))
                selectedImageList = policyImagePath;
            else if (newValue.equals("Claim Image"))
                selectedImageList = claimImagePath;
            else selectedImageList = collectiveImagePath;
            imagePathListView.setItems(selectedImageList);
        });
        endorsementNumberComboBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (endorsementNumberComboBox.getSelectionModel().getSelectedIndex() == -1)
                return;
            currentPolicyMapper.setPolicy(PolicyConnector.policies.get(endorsementNumberComboBox.getSelectionModel().getSelectedIndex()));
        }));
        clientComboBox.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (clientComboBox.getSelectionModel().getSelectedItem() == null)
                return;
            currentPolicyMapper.clientNumberProperty().setValue(clientList.get((int) newValue).getClientPhoneNumber());
        }));
        setBindings();
        setComboBoxItems();
    }

    private void setBindings() {
        policyNumberTextField.textProperty().bindBidirectional(currentPolicyMapper.policyNumberProperty());
        agentNameTextField.textProperty().bindBidirectional(currentPolicyMapper.agentNameProperty());
        insuranceCompanyTextField.textProperty().bindBidirectional(currentPolicyMapper.insuranceCompanyProperty());
        insuranceTypeComboBox.valueProperty().bindBidirectional(currentPolicyMapper.insuranceTypeProperty());
        beneficiaryTextField.textProperty().bindBidirectional(currentPolicyMapper.beneficiaryProperty());
        grossCommissionTextField.textProperty().bindBidirectional(currentPolicyMapper.grossCommissionProperty());
        expiryDateDatePicker.valueProperty().bindBidirectional(currentPolicyMapper.expiryDateProperty());
        clientComboBox.valueProperty().bindBidirectional(currentPolicyMapper.clientNameProperty());
        clientPhoneNumberTextField.textProperty().bindBidirectional(currentPolicyMapper.clientNumberProperty());
        grossPremiumTextField.textProperty().bindBidirectional(currentPolicyMapper.grossPremuimProperty());
        specialDiscountTextField.textProperty().bindBidirectional(currentPolicyMapper.specialDiscountProperty());
        netPremiumTextField.textProperty().bindBidirectional(currentPolicyMapper.netPremiumProperty());
        taxesComboBox.valueProperty().bindBidirectional(currentPolicyMapper.taxesProperty());
        grossCommissionTextField.disableProperty().bind(commissionPasswordField.textProperty().isNotEqualTo("ahmed"));
        sumInsuredTextField.textProperty().bindBidirectional(currentPolicyMapper.sumInsuredProperty());
        currencyComboBox.valueProperty().bindBidirectional(currentPolicyMapper.currencyProperty());
        collectiveComboBox.valueProperty().bindBidirectional(currentPolicyMapper.collectiveProperty());
        policyStatusComboBox.valueProperty().bindBidirectional(currentPolicyMapper.policyStatusProperty());
        endorsementNumberComboBox.valueProperty().bindBidirectional(currentPolicyMapper.indoresmentNumberProperty());
        imageBrowseButton.disableProperty().bind(imageTypeComboBox.getSelectionModel().selectedItemProperty().isNull());
        deleteImageButton.disableProperty().bind(imagePathListView.getSelectionModel().selectedItemProperty().isNull());
        editClientButton.disableProperty().bind(clientComboBox.getSelectionModel().selectedItemProperty().isNull());
        policyNumberTextField.editableProperty().bind(endorsementNumberComboBox.getSelectionModel().selectedItemProperty().isEqualTo(""));
        paidClaimsTextField.textProperty().bindBidirectional(currentPolicyMapper.paidClaimsProperty());
    }

    private void setComboBoxItems() {
        insuranceTypeComboBox.setItems(insuranceTypes);
        clientComboBox.setItems(clients);
        collectiveComboBox.setItems(collectiveList);
        currencyComboBox.setItems(currencyList);
        policyStatusComboBox.setItems(policyStatus);
        taxesComboBox.setItems(taxes);
        imageTypeComboBox.setItems(imageTypes);
        endorsementNumberComboBox.setItems(endorsementNumbers);

    }

    private FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser;
    }

    public void setPolicyMappers(ObservableList<PolicyMapper> policyMappers) {
        this.policyMappers = policyMappers;
    }

    @FXML
    protected void handleImageBrowseButton(MouseEvent event) {
        if (imageTypeComboBox.getSelectionModel().getSelectedItem().equals("Collective Image")) {
            if (!selectedImageList.isEmpty()) {
                Alert tooMuchImagesAlert = new Alert(Alert.AlertType.ERROR);
                tooMuchImagesAlert.setTitle("Only one collective Image");
                tooMuchImagesAlert.setHeaderText("you can only choose one collective image");
                tooMuchImagesAlert.showAndWait();
                return;
            } else {
                File image = getFileChooser(imageTypeComboBox.getSelectionModel().getSelectedItem()).showOpenDialog(Main.primaryStage);
                if (image == null) return;
                selectedImageList.add(image.getAbsolutePath());
            }
        } else {
            List<File> images = getFileChooser(imageTypeComboBox.getSelectionModel().getSelectedItem()).showOpenMultipleDialog(Main.primaryStage);
            if (images == null) return;
            for (File image : images) {
                selectedImageList.add(image.getAbsolutePath());
            }
        }
    }

    @FXML
    protected void handleDeleteImageButton(MouseEvent event) {
        selectedImageList.remove(imagePathListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    protected void handleCancelButton(MouseEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void handleSaveButton(MouseEvent event) {
        if (!collectiveImagePath.isEmpty())
            currentPolicyMapper.sync(claimImagePath, policyImagePath, collectiveImagePath.get(0));
        else
            currentPolicyMapper.sync(claimImagePath,policyImagePath,null);
        if(currentPolicyMapper.save()){
            Alert SuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            SuccessAlert.setTitle("Save Policy");
            SuccessAlert.setHeaderText("Success in saving new policy");
            SuccessAlert.showAndWait();
        }
        else
        {
            Alert FailAlert = new Alert(Alert.AlertType.ERROR);
            FailAlert.setTitle("Save Policy");
            FailAlert.setHeaderText("Fail to save new policy");
            FailAlert.showAndWait();
        }
    }


}