package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.Mappers.EndorsementMapper;
import sample.model.Endorsement;
import sample.util.Definitions;

import java.io.File;
import java.util.List;

public class DetailedEndorsementViewController {

    @FXML
    private TextField policyNumberTextField;
    @FXML
    private TextField endorsementNumberTextField;
    @FXML
    private TextField grossCommissionTextField;
    @FXML
    private TextField grossPremiumTextField;
    @FXML
    private TextField specialDiscountTextField;
    @FXML
    private ComboBox<String> taxesComboBox;
    @FXML
    private DatePicker issuanceDateDatePicker;
    @FXML
    private PasswordField commissionPasswordField;
    @FXML
    private ListView<String> endorsementImageListView;
    @FXML private TextField netPremiumTextField;
    private EndorsementMapper currentEndorsementMapper;
    private ObservableList<String> endorsementImagePath;


    public DetailedEndorsementViewController() {
        endorsementImagePath = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {


    }

    private void setBindings() {
        policyNumberTextField.textProperty().bindBidirectional(currentEndorsementMapper.policyNumberProperty());
        endorsementNumberTextField.textProperty().bindBidirectional(currentEndorsementMapper.endorsementNumberProperty());
        grossCommissionTextField.textProperty().bindBidirectional(currentEndorsementMapper.grossCommissionProperty());
        grossPremiumTextField.textProperty().bindBidirectional(currentEndorsementMapper.grossPremiumProperty());
        specialDiscountTextField.textProperty().bindBidirectional(currentEndorsementMapper.specialDiscountProperty());
        taxesComboBox.valueProperty().bindBidirectional(currentEndorsementMapper.taxesProperty());
        netPremiumTextField.textProperty().bindBidirectional(currentEndorsementMapper.netPremiumProperty());
        issuanceDateDatePicker.valueProperty().bindBidirectional(currentEndorsementMapper.issuanceDateProperty());
        grossCommissionTextField.disableProperty().bind(commissionPasswordField.textProperty().isNotEqualTo(Definitions.password));
    }

    private void setItems() {
        endorsementImageListView.setItems(endorsementImagePath);
        taxesComboBox.setItems(Definitions.taxes);
    }

    public void setPolicyNumber(String policyNumber) {
        currentEndorsementMapper = new EndorsementMapper(new Endorsement(policyNumber));
        setBindings();
        setItems();
    }

    public void setEndorsement(Endorsement endorsement) {
        currentEndorsementMapper.setEndorsement(endorsement);
        endorsementImagePath.addAll(endorsement.getImagePath());
    }

    @FXML
    protected void handleSaveButton(MouseEvent event) {
        currentEndorsementMapper.sync(endorsementImagePath);
        currentEndorsementMapper.save();
        Stage currentStage = (Stage) endorsementNumberTextField.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    protected void handleCancelButton(MouseEvent event) {
        Stage currentStage = (Stage) endorsementNumberTextField.getScene().getWindow();
        currentStage.close();
    }
    @FXML protected void handleBrowseButton(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Endorsement Images");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        List<File> images= fileChooser.showOpenMultipleDialog(Main.primaryStage);
        if(images == null)
            return;
        for(File file : images){
            endorsementImagePath.add(file.getAbsolutePath());
        }
    }

}
