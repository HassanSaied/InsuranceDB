package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.Mappers.EndorsementMapper;
import sample.model.Endorsement;
import sample.util.EndorsementConnector;

import javax.swing.border.Border;
import java.io.IOException;
import java.time.LocalDate;

public class EndorsementViewController {

    @FXML
    private TableView<EndorsementMapper> endorsementTableView;
    @FXML
    private TableColumn<EndorsementMapper, String> endorsementNumberTableColumn;
    @FXML
    private TableColumn<EndorsementMapper, LocalDate> issuanceDateTableColumn;
    @FXML
    private TableColumn<EndorsementMapper, String> grossPremiumTableColumn;
    @FXML
    private TableColumn<EndorsementMapper, String> netPremiumTableColumn;
    @FXML
    private TableColumn<EndorsementMapper, String> specialDiscountTableColumn;
    @FXML
    private Button editEndorsementButton,deleteEndorsementButton;
    private String policyNumber;
    public ObservableList<EndorsementMapper> endorsementMappers;

    public EndorsementViewController(){
        endorsementMappers = FXCollections.observableArrayList();
    }
    private void setColumnContents() {
        endorsementNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().endorsementNumberProperty());
        issuanceDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().issuanceDateProperty());
        grossPremiumTableColumn.setCellValueFactory(cellData -> cellData.getValue().grossPremiumProperty());
        netPremiumTableColumn.setCellValueFactory(cellData -> cellData.getValue().netPremiumProperty());
        specialDiscountTableColumn.setCellValueFactory(cellData -> cellData.getValue().specialDiscountProperty());
    }

    @FXML
    private void initialize() {
        setColumnContents();
        endorsementTableView.setItems(endorsementMappers);
        editEndorsementButton.disableProperty().bind(endorsementTableView.getSelectionModel().selectedIndexProperty().lessThan(0));
        deleteEndorsementButton.disableProperty().bind(endorsementTableView.getSelectionModel().selectedIndexProperty().lessThan(0));
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        generateEndorsementMappers();
    }

    private void generateEndorsementMappers() {
        endorsementMappers.clear();
        for (Endorsement endorsement : EndorsementConnector.getEndorsements(policyNumber)) {
            endorsementMappers.add(new EndorsementMapper(endorsement));
        }
    }

    @FXML
    protected void handleNewButton(MouseEvent event) {
        FXMLLoader detailedEndorsementViewLoader = new FXMLLoader();
        detailedEndorsementViewLoader.setLocation(Main.class.getResource("Views/detailedEndorsementView.fxml"));
        try {
            BorderPane detailedEndorsementViewBorderPane = detailedEndorsementViewLoader.load();
            ((DetailedEndorsementViewController) detailedEndorsementViewLoader.getController()).setPolicyNumber(policyNumber);
            Stage newEndorsementStage = new Stage();
            newEndorsementStage.setTitle("New Endorsement");
            newEndorsementStage.initModality(Modality.WINDOW_MODAL);
            newEndorsementStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(detailedEndorsementViewBorderPane);
            newEndorsementStage.setScene(dialog);
            newEndorsementStage.showAndWait();
            generateEndorsementMappers();
        } catch (IOException exception) {
            System.err.println("Couldn't Load detailed Endorsement View");
            System.err.println("Exception " + exception.getMessage());
        }
    }


    @FXML protected void handleEditButton(MouseEvent event){
        FXMLLoader detailedEndorsementViewLoader = new FXMLLoader();
        detailedEndorsementViewLoader.setLocation(Main.class.getResource("Views/detailedEndorsementView.fxml"));
        try {
            BorderPane detailedEndorsementViewBorderPane = detailedEndorsementViewLoader.load();
            ((DetailedEndorsementViewController) detailedEndorsementViewLoader.getController()).setPolicyNumber(policyNumber);
            ((DetailedEndorsementViewController) detailedEndorsementViewLoader.getController()).setEndorsement(endorsementTableView.getSelectionModel().getSelectedItem().getEndorsement());
            Stage newEndorsementStage = new Stage();
            newEndorsementStage.setTitle("New Endorsement");
            newEndorsementStage.initModality(Modality.WINDOW_MODAL);
            newEndorsementStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(detailedEndorsementViewBorderPane);
            newEndorsementStage.setScene(dialog);
            newEndorsementStage.showAndWait();
            generateEndorsementMappers();
        } catch (IOException exception) {
            System.err.println("Couldn't Load detailed Endorsement View");
            System.err.println("Exception " + exception.getMessage());
        }

    }
    @FXML protected void handleCloseButton(MouseEvent event){
        Stage currentStage = (Stage)editEndorsementButton.getScene().getWindow();
        currentStage.close();
    }
    @FXML protected void handleDeleteButton(MouseEvent event){
        endorsementTableView.getSelectionModel().getSelectedItem().delete();
        generateEndorsementMappers();
    }


}
