package sample.Mappers;

import javafx.beans.property.*;
import sample.model.Client;
import sample.model.Policy;
import sample.util.Utils;

import java.time.LocalDate;

/**
 * Created by hassan on 7/8/17.
 */
public class PolicyMapper {

    public Policy getPolicy() {
        return policy;
    }

    private Policy policy;
    private final StringProperty agentName;
    private final StringProperty insuranceCompany;
    private final StringProperty insuranceType;
    private final StringProperty beneficiary;
    private final ObjectProperty<ClientMapper> clientMapper;
    private final StringProperty policyNumber;
    private final StringProperty grossPremuim;
    private final StringProperty specialDiscount;
    private final StringProperty netPremium;
    private final StringProperty grossCommission;
    private final StringProperty taxes;
    private final StringProperty netCommission;
    private final StringProperty sumInsured;
    private final StringProperty currency;
    private final StringProperty collective;
    private final StringProperty policyStatus;
    private final StringProperty paidClaims;
    private final StringProperty indoresmentNumber;


    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    private final ObjectProperty<LocalDate> expiryDate;

    public ObjectProperty<ClientMapper> clientMapperProperty()

    {
        return clientMapper;
    }


    public StringProperty agentNameProperty() {
        return agentName;
    }


    public StringProperty insuranceCompanyProperty() {
        return insuranceCompany;
    }


    public StringProperty insuranceTypeProperty() {
        return insuranceType;
    }


    public StringProperty beneficiaryProperty() {
        return beneficiary;
    }


    public StringProperty policyNumberProperty() {
        return policyNumber;
    }


    public StringProperty grossPremuimProperty() {
        return grossPremuim;
    }


    public StringProperty specialDiscountProperty() {
        return specialDiscount;
    }


    public StringProperty netPremiumProperty() {
        return netPremium;
    }

    public StringProperty grossCommissionProperty() {
        return grossCommission;
    }

    public StringProperty taxesProperty() {
        return taxes;
    }


    public StringProperty netCommissionProperty() {
        return netCommission;
    }


    public StringProperty sumInsuredProperty() {
        return sumInsured;
    }


    public StringProperty currencyProperty() {
        return currency;
    }


    public StringProperty collectiveProperty() {
        return collective;
    }


    public StringProperty policyStatusProperty() {
        return policyStatus;
    }


    public StringProperty paidClaimsProperty() {
        return paidClaims;
    }

    public StringProperty indoresmentNumberProperty() {
        return indoresmentNumber;
    }

    public PolicyMapper(Policy policy) {
        this.policy = policy;
        clientMapper = new SimpleObjectProperty<ClientMapper>(new ClientMapper(policy.getClient())){
            @Override
            public void set(ClientMapper newValue) {
                this.getValue().clientNameProperty().setValue(newValue.clientNameProperty().getValue());
                this.getValue().clientPhoneNumberProperty().setValue(newValue.clientPhoneNumberProperty().getValue());
            }
        };
        agentName = new SimpleStringProperty(Utils.getMappedString(this.policy.getAgentName()));
        insuranceCompany = new SimpleStringProperty(Utils.getMappedString(this.policy.getInsuranceCompany()));
        insuranceType = new SimpleStringProperty(Utils.getMappedString(this.policy.getInsuranceType()));
        beneficiary = new SimpleStringProperty(Utils.getMappedString(this.policy.getBeneficiary()));
        this.policyNumber = new SimpleStringProperty(this.policy.getPolicyNumber());
        grossPremuim = new SimpleStringProperty(String.valueOf(Utils.getMappedString(this.policy.getGrossPremium())));
        specialDiscount = new SimpleStringProperty(String.valueOf(Utils.getMappedString(this.policy.getSpecialDiscount())));
        netPremium = new SimpleStringProperty(Utils.getMappedString(this.policy.getNetPremium()));
        grossCommission = new SimpleStringProperty(Utils.getMappedString(this.policy.getGrossCommission()));
        taxes = new SimpleStringProperty(Utils.getMappedString(this.policy.getNetCommission()));
        netCommission = new SimpleStringProperty(Utils.getMappedString(this.policy.getNetCommission()));
        sumInsured = new SimpleStringProperty(Utils.getMappedString(this.policy.getSumInsured()));
        currency = new SimpleStringProperty(Utils.getMappedString(this.policy.getCurrency()));
        collective = new SimpleStringProperty(Utils.getMappedString(this.policy.getCollective()));
        policyStatus = new SimpleStringProperty(Utils.getMappedString(this.policy.getPolicyStatus()));
        paidClaims = new SimpleStringProperty(Utils.getMappedString(this.policy.getPaidClaims()));
        indoresmentNumber = new SimpleStringProperty(Utils.getMappedString(this.policy.getIndoresmentNumber()));
        expiryDate = new SimpleObjectProperty<LocalDate>(this.policy.getExpiryDate() == null ? null : this.policy.getExpiryDate());
    }


}
