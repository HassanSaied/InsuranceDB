package sample.Controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.Mappers.PolicyMapper;
import sample.model.Policy;
import sample.util.PolicyConnector;
import javafx.scene.input.*;

import java.io.IOException;
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
    private TableColumn<PolicyMapper, String> indoresmentNumberColumn;
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
    private TableColumn<PolicyMapper, String> netPremuimColumn;
    @FXML
    private TableColumn<PolicyMapper, String> expiryDateColumn;
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

    @FXML
    private void initialize() {
        setColumnContents();
        policyMapperTableView.setItems(policyMappers);
    }


    public PolicyViewController() {
        generatePolicyMappers();
    }

    private void generatePolicyMappers() {
        if (policyMappers != null)
            policyMappers.clear();
        policyMappers = null;
        policyMappers = FXCollections.observableArrayList();
        List<Policy> policies = PolicyConnector.getPolicies();
        for (Policy policy : policies) {
            policyMappers.add(new PolicyMapper(policy));
        }
    }

    private void setColumnContents() {
        policyNumberColumn.setCellValueFactory(cellData -> cellData.getValue().policyNumberProperty());
        setCellEventHandler(policyNumberColumn, this::handlePolicyNumberClickEvent);
        indoresmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().indoresmentNumberProperty());
        setCellEventHandler(indoresmentNumberColumn, this::handleIndoresmentNumberClickEvent);
        agentNameColumn.setCellValueFactory(cellData -> cellData.getValue().agentNameProperty());
        insuranceCompanyColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceCompanyProperty());
        insuranceTypeColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceTypeProperty());
        beneficiaryColumn.setCellValueFactory(cellData -> cellData.getValue().beneficiaryProperty());
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        clientPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().clientNumberProperty());
        grossPremuimColumn.setCellValueFactory(cellData -> cellData.getValue().grossPremuimProperty());
        specialDiscountColumn.setCellValueFactory(cellData -> cellData.getValue().specialDiscountProperty());
        netPremuimColumn.setCellValueFactory(cellData -> cellData.getValue().netPremiumProperty());
        expiryDateColumn.setCellValueFactory(cellData -> cellData.getValue().expiryDateProperty());
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
        System.out.println(policy.getPolicyNumber());

    }

    private void handleIndoresmentNumberClickEvent(Policy policy) {
        System.out.println("123");

    }
    @FXML protected void handleNewButtonMoussePress(MouseEvent event){
        try{
            BorderPane newPolicyBorderPane = FXMLLoader.load(Main.class.getResource("Views/newPolicyView.fxml"));
            Stage newPolicyDialogStage = new Stage();
            newPolicyDialogStage.setTitle("Add new policy");
            newPolicyDialogStage.initModality(Modality.WINDOW_MODAL);
            newPolicyDialogStage.initOwner(Main.primaryStage);
            Scene dialog = new Scene(newPolicyBorderPane);
            newPolicyDialogStage.setScene(dialog);
            newPolicyDialogStage.showAndWait();
            return;
        }
        catch (IOException exception){
            System.err.println("Can't load new policy view");
        }

    }

}
