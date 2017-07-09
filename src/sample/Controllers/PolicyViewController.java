package sample.Controllers;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import sample.Mappers.PolicyMapper;
import sample.model.Policy;
import sample.util.PolicyConnector;

import java.util.List;


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
    private void initialize() {
        setColumnContents();
        policyMapperTableView.setItems(policyMappers);
        //setCellWidth();
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
        indoresmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().indoresmentNumberProperty());
        agentNameColumn.setCellValueFactory(cellData -> cellData.getValue().agentNameProperty());
        insuranceCompanyColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceCompanyProperty());
        insuranceTypeColumn.setCellValueFactory(cellData -> cellData.getValue().insuranceTypeProperty());
        beneficiaryColumn.setCellValueFactory(cellData -> cellData.getValue().beneficiaryProperty());
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        clientPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().clientNumberProperty());
        grossPremuimColumn.setCellValueFactory(cellData -> cellData.getValue().specialDiscountProperty());
        specialDiscountColumn.setCellValueFactory(cellData -> cellData.getValue().policyNumberProperty());
        netPremuimColumn.setCellValueFactory(cellData -> cellData.getValue().netPremiumProperty());
        expiryDateColumn.setCellValueFactory(cellData -> cellData.getValue().expiryDateProperty());
        sumInssuredColumn.setCellValueFactory(cellData -> cellData.getValue().sumInsuredProperty());
        currencyColumn.setCellValueFactory(cellData -> cellData.getValue().currencyProperty());
        collectiveColumn.setCellValueFactory(cellData -> cellData.getValue().collectiveProperty());
        policyStatusColumn.setCellValueFactory(cellData -> cellData.getValue().policyStatusProperty());
        paidClaimsColumn.setCellValueFactory(cellData -> cellData.getValue().paidClaimsProperty());
    }

    private void setCellWidth() {
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font("Arial", 12));

        for (int i = 0; i < 16; i++) {
            String text = policyMapperTableView.getColumns().get(i).getText();
            double textWidth = fontMetrics.computeStringWidth(text);
            policyMapperTableView.getColumns().get(i).setPrefWidth(textWidth + 10);
        }
    }

}
