package sample.Mappers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.model.Policy;

/**
 * Created by hassan on 7/8/17.
 */
public class PolicyMapper {

    private Policy policy;
    private final StringProperty agentName;
    private final StringProperty insuranceCompany;
    private final StringProperty insuranceType;
    private final StringProperty beneficiary;
    private final StringProperty clientName;
    private final StringProperty clientNumber;
    private final StringProperty policyNumber;
    private final StringProperty grossPremuim;
    private final StringProperty specialDiscount;
    private final StringProperty netPremium;
    private final StringProperty grossCommission;
    private final StringProperty taxes;
    private final StringProperty netCommission;
    private final StringProperty expiryDate;
    private final StringProperty sumInsured;
    private final StringProperty currency;
    private final StringProperty collective;
    private final StringProperty policyStatus;
    private final StringProperty paidClaims;
    private final StringProperty indoresmentNumber;


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


    public StringProperty clientNameProperty() {
        return clientName;
    }


    public StringProperty clientNumberProperty() {
        return clientNumber;
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


    public StringProperty expiryDateProperty() {
        return expiryDate;
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
        agentName = new SimpleStringProperty(this.policy.getAgentName());
        insuranceCompany = new SimpleStringProperty(this.policy.getInsuranceCompany());
        insuranceType = new SimpleStringProperty(this.policy.getInsuranceType());
        beneficiary = new SimpleStringProperty(this.policy.getBeneficiary());
        clientName = new SimpleStringProperty(this.policy.getClient().getClientName());
        clientNumber = new SimpleStringProperty(this.policy.getClient().getClientPhoneNumber());
        this.policyNumber = new SimpleStringProperty(String.valueOf(this.policy.getPolicyNumber()));
        grossPremuim = new SimpleStringProperty(String.valueOf(this.policy.getGrossPremium()));
        specialDiscount = new SimpleStringProperty((this.policy.getSpecialDiscount().toString()));
        netPremium = new SimpleStringProperty(this.policy.getNetPremium().toString());
        grossCommission = new SimpleStringProperty(this.policy.getGrossCommission().toString());
        taxes = new SimpleStringProperty(this.policy.getNetCommission().toString());
        netCommission = new SimpleStringProperty(this.policy.getNetCommission().toString());
        expiryDate = new SimpleStringProperty(this.policy.getExpiryDate().toString());
        sumInsured = new SimpleStringProperty(this.policy.getSumInsured().toString());
        currency = new SimpleStringProperty(this.policy.getCurrency().toString());
        collective = new SimpleStringProperty(this.policy.getCollective().toString());
        this.policyStatus = new SimpleStringProperty(this.policy.getPolicyStatus());
        paidClaims = new SimpleStringProperty(this.policy.getPaidClaims().toString());
        indoresmentNumber = new SimpleStringProperty(String.valueOf(this.policy.getIndoresmentNumber()));
    }


}
