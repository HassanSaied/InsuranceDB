package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.Mappers.PolicyMapper;
import sample.model.Policy;
import sample.util.PolicyConnector;
import sample.util.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;


/**
 * Created by hassan on 7/6/17.
 */
public class PolicyViewController {

    private ObservableList<PolicyMapper> policyMappers;
    @FXML
    private TableView<PolicyMapper> policyMapperTableView;
    @FXML
    private TableColumn<PolicyMapper, String> policyNumberColumn;
    @FXML
    private TableColumn<PolicyMapper, Boolean> hasEndorsementsColumn;
    @FXML
    private TableColumn<PolicyMapper, String> agentNameColumn;
    @FXML
    private TableColumn<PolicyMapper, String> insuranceCompanyColumn;
    @FXML
    private TableColumn<PolicyMapper, String> insuranceTypeColumn;
    @FXML
    private TableColumn<PolicyMapper, String> beneficiaryColumn;
    @FXML
    private TableColumn<PolicyMapper, String> clientNameColumn;
    @FXML
    private TableColumn<PolicyMapper, String> clientPhoneNumberColumn;
    @FXML
    private TableColumn<PolicyMapper, String> grossPremuimColumn;
    @FXML
    private TableColumn<PolicyMapper, String> specialDiscountColumn;
    @FXML
    private TableColumn<PolicyMapper, String> netPremiumColumn;
    @FXML
    private TableColumn<PolicyMapper, LocalDate> expiryDateColumn;
    @FXML
    private TableColumn<PolicyMapper, LocalDate> issuanceDateColumn;
    @FXML
    private TableColumn<PolicyMapper, String> sumInssuredColumn;
    @FXML
    private TableColumn<PolicyMapper, String> currencyColumn;
    @FXML
    private TableColumn<PolicyMapper, String> collectiveColumn;
    @FXML
    private TableColumn<PolicyMapper, String> policyStatusColumn;
    @FXML
    private TableColumn<PolicyMapper, String> paidClaimsColumn;
    @FXML
    private Button newPolicyButton;
    @FXML
    private Button editPolicyButton;
    @FXML
    private Button deletePolicyButton;

    private static TableCell<PolicyMapper, LocalDate> call(TableColumn<PolicyMapper, LocalDate> param) {
        final TableCell<PolicyMapper, LocalDate> cell = new TableCell<PolicyMapper, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                if (empty || item == null)
                    setText(null);
                else setText(Utils.getMappedString(item));
            }
        };
        return cell;
    }

    @FXML
    private void initialize() {
        setColumnContents();
        policyMapperTableView.setItems(policyMappers);
        deletePolicyButton.disableProperty().bind(policyMapperTableView.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        editPolicyButton.disableProperty().bind(policyMapperTableView.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
    }


    public PolicyViewController() {

        policyMappers = FXCollections.observableArrayList();
        generatePolicyMappers();
    }

    private void generatePolicyMappers() {
        policyMappers.clear();
        List<Policy> policies = PolicyConnector.getPolicies();
        for (Policy policy : policies) {
            policyMappers.add(new PolicyMapper(policy));
        }
    }

    private void setColumnContents() {
        policyNumberColumn.setCellValueFactory(cellData -> cellData.getValue().policyNumberProperty());
        setCellEventHandler(policyNumberColumn, this::handlePolicyNumberClickEvent);
        hasEndorsementsColumn.setCellValueFactory(cellData -> cellData.getValue().hasEndorsementProperty());
        hasEndorsementsColumn.setCellFactory(param -> {
            final TableCell<PolicyMapper, Boolean> cell = new TableCell<PolicyMapper, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    if (empty || item == null)
                        setText(null);
                    else if (item)
                        setText("Yes");
                    else setText("No");
                }

            };
            cell.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                if (event.getClickCount() == 2) {
                    PolicyMapper mapper = (PolicyMapper) cell.getTableRow().getItem();
                    handleEndorsementNumberClickEvent(mapper.getPolicy());
                }
            });
            return cell;
        });
        agentNameColumn.setCellValueFactory(cellData -> cellData.getValue().agentNameProperty());
        insuranceCompanyColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceCompanyProperty());
        insuranceTypeColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceTypeProperty());
        beneficiaryColumn.setCellValueFactory(cellData -> cellData.getValue().beneficiaryProperty());
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        clientPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().clientNumberProperty());
        grossPremuimColumn.setCellValueFactory(cellData -> cellData.getValue().grossPremuimProperty());
        specialDiscountColumn.setCellValueFactory(cellData -> cellData.getValue().specialDiscountProperty());
        netPremiumColumn.setCellValueFactory(cellData -> cellData.getValue().netPremiumProperty());
        issuanceDateColumn.setCellValueFactory(cellData -> cellData.getValue().issuanceDateProperty());
        issuanceDateColumn.setCellFactory(PolicyViewController::call);
        expiryDateColumn.setCellValueFactory(cellData -> cellData.getValue().expiryDateProperty());
        expiryDateColumn.setCellFactory(PolicyViewController::call);
        sumInssuredColumn.setCellValueFactory(cellData -> cellData.getValue().sumInsuredProperty());
        currencyColumn.setCellValueFactory(cellData -> cellData.getValue().currencyProperty());
        collectiveColumn.setCellValueFactory(cellData -> cellData.getValue().collectiveProperty());
        policyStatusColumn.setCellValueFactory(cellData -> cellData.getValue().policyStatusProperty());
        paidClaimsColumn.setCellValueFactory(cellData -> cellData.getValue().paidClaimsProperty());
    }

    private void setCellEventHandler(TableColumn<PolicyMapper, String> column, Consumer<Policy> policyHandler) {
        column.setCellFactory(new Callback<TableColumn<PolicyMapper, String>, TableCell<PolicyMapper, String>>() {
            @Override
            public TableCell<PolicyMapper, String> call(TableColumn<PolicyMapper, String> param) {
                final TableCell<PolicyMapper, String> cell = new TableCell<PolicyMapper, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setText(null);
                        else setText(item);
                    }
                };
                cell.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                    if (event.getClickCount() == 2) {
                        PolicyMapper mapper = (PolicyMapper) cell.getTableRow().getItem();
                        policyHandler.accept(mapper.getPolicy());
                    }
                });
                return cell;
            }
        });
    }

    private void handlePolicyNumberClickEvent(Policy policy) {
        managePolicy(policyMapperTableView.getSelectionModel().getSelectedItem().getPolicy(), true, "Views/policyImageViewer.fxml", "View policy");


    }

    private void handleEndorsementNumberClickEvent(Policy policy) {
        try {
            FXMLLoader endorsementViewLoader = new FXMLLoader();
            endorsementViewLoader.setLocation(Main.class.getResource("Views/endorsementView.fxml"));
            BorderPane endorsementBorderPane = endorsementViewLoader.load();
            ((EndorsementViewController) endorsementViewLoader.getController()).setPolicyNumber(policy.getPolicyNumber());
            Stage endorsementStage = new Stage();
            endorsementStage.setTitle("Endorsements");
            endorsementStage.initModality(Modality.WINDOW_MODAL);
            endorsementStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(endorsementBorderPane);
            endorsementStage.setScene(dialog);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            endorsementStage.setWidth(bounds.getWidth());
            endorsementStage.setHeight(bounds.getHeight());
            endorsementStage.setMaximized(true);
            endorsementStage.showAndWait();
            generatePolicyMappers();
        } catch (IOException exception) {
            System.err.println("Can't load Endorsement view");
            System.err.println("Exception " + exception.getMessage());

        }
    }

    @FXML
    protected void handleNewButtonMoussePress(MouseEvent event) {
        managePolicy(null, false, "Views/detailedPolicyView.fxml", "Add new policy");
    }

    @FXML
    protected void handleDeleteButtonMousePress(MouseEvent event) {
        policyMapperTableView.getSelectionModel().getSelectedItem().getPolicy().delete();
        generatePolicyMappers();

    }

    @FXML
    protected void handleEditPolicyButton(MouseEvent event) {
        managePolicy(policyMapperTableView.getSelectionModel().getSelectedItem().getPolicy(), false, "Views/detailedPolicyView.fxml", "Edit policy");
    }

    private void managePolicy(Policy policy, boolean image, String location, String title) {
        try {
            FXMLLoader newPolicyLoader = new FXMLLoader();
            newPolicyLoader.setLocation(Main.class.getResource(location));
            BorderPane newPolicyBorderPane = newPolicyLoader.load();
            if (!image) {
                ((DetailedPolicyViewController) (newPolicyLoader.getController())).setPolicy(policy);
            } else {
                ((PolicyImageViewController) (newPolicyLoader.getController())).setPolicy(policy);
            }
            Stage newPolicyDialogStage = new Stage();
            newPolicyDialogStage.setTitle(title);
            newPolicyDialogStage.initModality(Modality.WINDOW_MODAL);
            newPolicyDialogStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(newPolicyBorderPane);
            newPolicyDialogStage.setScene(dialog);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            newPolicyDialogStage.setWidth(bounds.getWidth());
            newPolicyDialogStage.setHeight(bounds.getHeight());
            newPolicyDialogStage.setMaximized(true);
            newPolicyDialogStage.showAndWait();
            generatePolicyMappers();
        } catch (IOException exception) {
            System.err.println("Can't load new policy view");
        }
    }

}
