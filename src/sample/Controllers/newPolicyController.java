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


    @FXML Button deleteImageButton;

    private PolicyMapper currentPolicyMapper = null;

    private ObservableList<String> insuranceTypes;
    private ObservableList<ClientMapper> clients;
    private ObservableList<String> collectiveList;
    private ObservableList<String> currencyList;
    private ObservableList<String> policyStatus;
    private ObservableList<String> taxes;
    private ObservableList<String> policyImagePath, claimImagePath, collectiveImagePath;
    private ObservableList<String> selectedImageList;
    private ObservableList<String> imageTypes;

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

    }

    @FXML
    private void initialize() {
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
       imageTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Policy Image"))
                selectedImageList = policyImagePath;
            else if (newValue.equals("Claim Image"))
                selectedImageList = claimImagePath;
            else selectedImageList = collectiveImagePath;
            imagePathListView.setItems(selectedImageList);
        });
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
        clientComboBox.valueProperty().bindBidirectional(currentPolicyMapper.clientMapperProperty());
        clientPhoneNumberTextField.textProperty().bindBidirectional(currentPolicyMapper.clientMapperProperty().get().clientPhoneNumberProperty());
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
    }

    private void setComboBoxItems() {
        insuranceTypeComboBox.setItems(insuranceTypes);
        clientComboBox.setItems(clients);
        collectiveComboBox.setItems(collectiveList);
        currencyComboBox.setItems(currencyList);
        policyStatusComboBox.setItems(policyStatus);
        taxesComboBox.setItems(taxes);
        imageTypeComboBox.setItems(imageTypes);

    }

    private List<File> getImages(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser.showOpenMultipleDialog(Main.primaryStage);

    }

    @FXML
    protected void handleImageBrowseButton(MouseEvent event) {
        List<File> images = getImages(imageTypeComboBox.getSelectionModel().getSelectedItem());
        if (images == null) return;
        if (imageTypeComboBox.getSelectionModel().getSelectedItem().equals("Collective Image") && images.size() != 1) {
            Alert tooMuchImagesAlert = new Alert(Alert.AlertType.ERROR);
            tooMuchImagesAlert.setTitle("Only one collective Image");
            tooMuchImagesAlert.setHeaderText("you can only choose one collective image");
            tooMuchImagesAlert.showAndWait();
        }
        for (File image : images) {
            selectedImageList.add(image.getAbsolutePath());
        }
    }

    @FXML protected void handleDeleteImageButton(MouseEvent event){
        selectedImageList.remove(imagePathListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    protected void handleCancelButton(MouseEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }


}