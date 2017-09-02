package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
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
    private Button editEndorsementButton, deleteEndorsementButton;
    private String policyNumber;
    public ObservableList<EndorsementMapper> endorsementMappers;

    public EndorsementViewController() {
        endorsementMappers = FXCollections.observableArrayList();
    }

    private void setColumnContents() {
        endorsementNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().endorsementNumberProperty());
        issuanceDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().issuanceDateProperty());
        grossPremiumTableColumn.setCellValueFactory(cellData -> cellData.getValue().grossPremiumProperty());
        netPremiumTableColumn.setCellValueFactory(cellData -> cellData.getValue().netPremiumProperty());
        specialDiscountTableColumn.setCellValueFactory(cellData -> cellData.getValue().specialDiscountProperty());
        endorsementNumberTableColumn.setCellFactory(param -> {
            final TableCell<EndorsementMapper, String> cell = new TableCell<EndorsementMapper, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if(item==null || empty)
                        setText(null);
                    else setText(item);
                }

            };
            cell.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                if (event.getClickCount() == 2) {
                    FXMLLoader endorsementImageView = new FXMLLoader();
                    endorsementImageView.setLocation(Main.class.getResource("Views/endorsementImageViewer.fxml"));
                    try {
                        AnchorPane endorsementImageViewAnchorPane = endorsementImageView.load();
                        EndorsementMapper endorsementMapper =(EndorsementMapper) cell.getTableRow().getItem();
                        ((EndorsementImageViewController) endorsementImageView.getController()).setEndorsement(endorsementMapper.getEndorsement());
                        Stage newEndorsementStage = new Stage();
                        newEndorsementStage.setTitle("Endorsement Image");
                        newEndorsementStage.initModality(Modality.WINDOW_MODAL);
                        newEndorsementStage.initOwner(Main.primaryStage);
                        Scene dialog = new Scene(endorsementImageViewAnchorPane);
                        newEndorsementStage.setScene(dialog);
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();
                        newEndorsementStage.setWidth(bounds.getWidth());
                        newEndorsementStage.setHeight(bounds.getHeight());
                        newEndorsementStage.setMaximized(true);
                        newEndorsementStage.showAndWait();
                        newEndorsementStage.showAndWait();
                        generateEndorsementMappers();
                    } catch (IOException exception) {
                        System.err.println("Couldn't Load Endorsement Image View");
                        System.err.println("Exception " + exception.getMessage());
                    }
                }
            });
            return cell;
        });
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
            Stage detailedEndorsementViewStage = new Stage();
            detailedEndorsementViewStage.setTitle("New Endorsement");
            detailedEndorsementViewStage.initModality(Modality.WINDOW_MODAL);
            detailedEndorsementViewStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(detailedEndorsementViewBorderPane);
            detailedEndorsementViewStage.setScene(dialog);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            detailedEndorsementViewStage.setWidth(bounds.getWidth());
            detailedEndorsementViewStage.setHeight(bounds.getHeight());
            detailedEndorsementViewStage.setMaximized(true);
            detailedEndorsementViewStage.showAndWait();
            generateEndorsementMappers();
        } catch (IOException exception) {
            System.err.println("Couldn't Load detailed Endorsement View");
            System.err.println("Exception " + exception.getMessage());
        }
    }


    @FXML
    protected void handleEditButton(MouseEvent event) {
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

    @FXML
    protected void handleCloseButton(MouseEvent event) {
        Stage currentStage = (Stage) editEndorsementButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    protected void handleDeleteButton(MouseEvent event) {
        endorsementTableView.getSelectionModel().getSelectedItem().delete();
        generateEndorsementMappers();
    }


}
