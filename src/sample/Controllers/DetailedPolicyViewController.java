package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.Mappers.PolicyMapper;
import sample.model.Client;
import sample.model.Policy;
import sample.util.ClientConnector;
import sample.util.Definitions;
import sample.util.PolicyConnector;
import sample.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by hassan on 7/13/17.
 */
public class DetailedPolicyViewController {

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
    private DatePicker issuanceDataDatePicker;
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
    private ObservableList<String> policyStatus;
    private ObservableList<String> policyImagePath, claimImagePath, collectiveImagePath;
    private ObservableList<String> selectedImageList;
    private List<Client> clientList;

    public DetailedPolicyViewController() {

        currentPolicyMapper = new PolicyMapper(new Policy());
        this.insuranceTypes = FXCollections.observableArrayList();
        this.clients = FXCollections.observableArrayList();
        generateClient();
        generateInsuranceTypes();
        policyStatus = FXCollections.observableArrayList();
        policyStatus.addAll(PolicyConnector.getPolicyStatus());
        policyImagePath = FXCollections.observableArrayList();
        claimImagePath = FXCollections.observableArrayList();
        collectiveImagePath = FXCollections.observableArrayList();
    }

    private void generateClient() {
        clients.clear();
        clientList = ClientConnector.getClients();
        if (clientList != null) {
            for (Client client : clientList) {
                clients.add(client.getClientName());
            }
        }
    }

    private void generateInsuranceTypes() {
        insuranceTypes.clear();
        List<String> insuranceTypesList = PolicyConnector.getInsuranceTypes();
        if (insuranceTypesList != null)
            insuranceTypes.addAll(insuranceTypesList);
    }

    public void setPolicy(Policy policy) {
        if (policy == null)
            return;
        currentPolicyMapper.setPolicy(policy);
        policyImagePath.clear();
        claimImagePath.clear();
        collectiveImagePath.clear();
        if (policy.getPolicyImagePath() != null)
            policyImagePath.addAll(policy.getPolicyImagePath());
        if (policy.getClaimImagePath() != null)
            claimImagePath.addAll(policy.getClaimImagePath());
        if (policy.getCollectiveImagePath() != null)
            collectiveImagePath.add(policy.getCollectiveImagePath());

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
        issuanceDataDatePicker.valueProperty().bindBidirectional(currentPolicyMapper.issuanceDateProperty());
        clientComboBox.valueProperty().bindBidirectional(currentPolicyMapper.clientNameProperty());
        clientPhoneNumberTextField.textProperty().bindBidirectional(currentPolicyMapper.clientNumberProperty());
        grossPremiumTextField.textProperty().bindBidirectional(currentPolicyMapper.grossPremuimProperty());
        specialDiscountTextField.textProperty().bindBidirectional(currentPolicyMapper.specialDiscountProperty());
        taxesComboBox.valueProperty().bindBidirectional(currentPolicyMapper.taxesProperty());
        grossCommissionTextField.disableProperty().bind(commissionPasswordField.textProperty().isNotEqualTo(Definitions.password));
        sumInsuredTextField.textProperty().bindBidirectional(currentPolicyMapper.sumInsuredProperty());
        currencyComboBox.valueProperty().bindBidirectional(currentPolicyMapper.currencyProperty());
        collectiveComboBox.valueProperty().bindBidirectional(currentPolicyMapper.collectiveProperty());
        policyStatusComboBox.valueProperty().bindBidirectional(currentPolicyMapper.policyStatusProperty());
        imageBrowseButton.disableProperty().bind(imageTypeComboBox.getSelectionModel().selectedItemProperty().isNull());
        deleteImageButton.disableProperty().bind(imagePathListView.getSelectionModel().selectedItemProperty().isNull());
        editClientButton.disableProperty().bind(clientComboBox.getSelectionModel().selectedItemProperty().isNull());
        paidClaimsTextField.textProperty().bindBidirectional(currentPolicyMapper.paidClaimsProperty());
    }

    private void setComboBoxItems() {
        insuranceTypeComboBox.setItems(insuranceTypes);
        clientComboBox.setItems(clients);
        collectiveComboBox.setItems(Definitions.collectiveList);
        currencyComboBox.setItems(Definitions.currencyList);
        policyStatusComboBox.setItems(policyStatus);
        taxesComboBox.setItems(Definitions.taxes);
        imageTypeComboBox.setItems(Definitions.imageTypes);
    }

    private FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser;
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
        if (!isValid()) {
            Alert invalidDataAlert = new Alert(Alert.AlertType.ERROR);
            invalidDataAlert.setTitle("Save Policy");
            invalidDataAlert.setHeaderText("Error, you have entered invalid data");
            invalidDataAlert.showAndWait();
        }

        if (!collectiveImagePath.isEmpty())
            currentPolicyMapper.sync(claimImagePath, policyImagePath, collectiveImagePath.get(0));
        else
            currentPolicyMapper.sync(claimImagePath, policyImagePath, null);
        if (currentPolicyMapper.save()) {
            Alert SuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            SuccessAlert.setTitle("Save Policy");
            SuccessAlert.setHeaderText("Success in saving new policy");
            SuccessAlert.showAndWait();
        } else {
            Alert FailAlert = new Alert(Alert.AlertType.ERROR);
            FailAlert.setTitle("Save Policy");
            FailAlert.setHeaderText("Fail to save new policy");
            FailAlert.showAndWait();
        }
        Stage currentStage = (Stage) saveButton.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    protected void handleAddClientButton(MouseEvent event) {
        manageClient(null);
    }

    @FXML
    protected void handleEditClientButton(MouseEvent event) {
        manageClient(Utils.findClient(ClientConnector.clients, clientComboBox.getSelectionModel().getSelectedItem()));
    }

    @FXML
    protected void handleAddInsuranceTypeButton(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insurance Type Dialog");
        dialog.setHeaderText("Input new Insurance Type");
        dialog.setContentText("Insurance Type");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(insuranceType -> PolicyConnector.insertInsuranceType(insuranceType));
        generateInsuranceTypes();


    }

    private boolean isValid() {
        if (currentPolicyMapper.policyNumberProperty().getValue() == null || !currentPolicyMapper.policyNumberProperty().getValue().matches("[0-9]*"))
            return false;
        if (!Utils.isDouble(currentPolicyMapper.grossCommissionProperty().getValue()))
            return false;
        if (!Utils.isDouble(currentPolicyMapper.grossPremuimProperty().getValue()))
            return false;
        if (!Utils.isDouble(currentPolicyMapper.sumInsuredProperty().getValue()))
            return false;
        if (!Utils.isDouble(currentPolicyMapper.specialDiscountProperty().getValue()))
            return false;
        return true;
    }

    private void manageClient(Client client) {
        try {
            FXMLLoader addClientLoader = new FXMLLoader();
            addClientLoader.setLocation(Main.class.getResource("Views/detailedClientView.fxml"));
            Stage addClientStage = new Stage();
            BorderPane clientBorderPane = addClientLoader.load();
            addClientStage.setTitle("Client Dialog");
            ((DetailedClientViewController) (addClientLoader.getController())).setClient(client);
            addClientStage.initModality(Modality.WINDOW_MODAL);
            addClientStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(clientBorderPane);
            addClientStage.setScene(dialog);
            addClientStage.showAndWait();
        } catch (IOException exception) {
            System.err.println("Can't load new Client view");
            System.err.println(exception.getMessage());
        }
        generateClient();
        currentPolicyMapper.clientNameProperty().setValue(null);
        currentPolicyMapper.clientNumberProperty().setValue(null);
    }


}