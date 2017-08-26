package sample.Mappers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.model.Policy;
import sample.util.ClientConnector;
import sample.util.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    private final StringProperty clientName;
    private final StringProperty clientNumber;
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
    private final ObjectProperty<LocalDate> issuanceDate;

    public ObjectProperty<LocalDate> issuanceDateProperty() {
        return issuanceDate;
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


    public StringProperty clientNameProperty() {
        return clientName;
    }

    public StringProperty clientNumberProperty() {
        return clientNumber;
    }

    public PolicyMapper(Policy policy) {
        this.policy = policy;
        if (this.policy.getClient() == null) {
            clientName = new SimpleStringProperty(null);
            clientNumber = new SimpleStringProperty(null);
        } else {
            clientName = new SimpleStringProperty(this.policy.getClient().getClientName());
            clientNumber = new SimpleStringProperty(this.policy.getClient().getClientPhoneNumber());
        }
        agentName = new SimpleStringProperty(Utils.getMappedString(this.policy.getAgentName()));
        insuranceCompany = new SimpleStringProperty(Utils.getMappedString(this.policy.getInsuranceCompany()));
        insuranceType = new SimpleStringProperty(Utils.getMappedString(this.policy.getInsuranceType()));
        beneficiary = new SimpleStringProperty(Utils.getMappedString(this.policy.getBeneficiary()));
        this.policyNumber = new SimpleStringProperty(this.policy.getPolicyNumber());
        grossPremuim = new SimpleStringProperty(String.valueOf(Utils.getMappedString(this.policy.getGrossPremium())));
        specialDiscount = new SimpleStringProperty(String.valueOf(Utils.getMappedString(this.policy.getSpecialDiscount())));
        netPremium = new SimpleStringProperty(Utils.getMappedString(this.policy.getNetPremium()));
        grossCommission = new SimpleStringProperty(Utils.getMappedString(this.policy.getGrossCommission()));
        taxes = new SimpleStringProperty(Utils.taxesToString(this.policy.getTaxes()));
        netCommission = new SimpleStringProperty(Utils.getMappedString(this.policy.getNetCommission()));
        sumInsured = new SimpleStringProperty(Utils.getMappedString(this.policy.getSumInsured()));
        currency = new SimpleStringProperty(Utils.getMappedString(this.policy.getCurrency()));
        collective = new SimpleStringProperty(Utils.getMappedString(this.policy.getCollective()));
        policyStatus = new SimpleStringProperty(Utils.getMappedString(this.policy.getPolicyStatus()));
        paidClaims = new SimpleStringProperty(Utils.getMappedString(this.policy.getPaidClaims()));
        indoresmentNumber = new SimpleStringProperty(Utils.getMappedString(this.policy.getIndoresmentNumber()));
        issuanceDate = new SimpleObjectProperty<LocalDate>(this.policy.getIssuanceDate() == null ? null : this.policy.getIssuanceDate());
        expiryDate = new SimpleObjectProperty<LocalDate>(this.policy.getExpiryDate() == null ? null : this.policy.getExpiryDate());
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
        this.policyNumberProperty().setValue(policy.getPolicyNumber());
        if (this.policy.getClient() == null) {
            clientNumber.setValue(null);
            clientName.setValue(null);
        } else {
            this.clientNumber.setValue(policy.getClient().getClientPhoneNumber());
            this.clientName.setValue(policy.getClient().getClientName());
        }
        this.insuranceTypeProperty().setValue(policy.getInsuranceType());
        this.agentName.setValue(policy.getAgentName());
        insuranceCompany.setValue(policy.getInsuranceCompany());
        beneficiary.setValue(policy.getBeneficiary());
        grossPremuim.setValue(Utils.getMappedString(policy.getGrossPremium()));
        grossCommission.setValue(Utils.getMappedString(this.policy.getGrossCommission()));
        specialDiscount.setValue(Utils.getMappedString(this.policy.getSpecialDiscount()));
        netPremium.setValue(Utils.getMappedString(this.policy.getNetPremium()));
        taxes.setValue(Utils.taxesToString(this.policy.getTaxes()));
        netCommission.setValue(Utils.getMappedString(this.policy.getNetCommission()));
        sumInsured.setValue(Utils.getMappedString(this.policy.getSumInsured()));
        currency.setValue(Utils.getMappedString(this.policy.getCurrency()));
        collective.setValue(Utils.getMappedString(this.policy.getCollective()));
        policyStatus.setValue(Utils.getMappedString(this.policy.getPolicyStatus()));
        paidClaims.setValue(Utils.getMappedString(this.policy.getPaidClaims()));
        indoresmentNumber.setValue(Utils.emptyToNull(this.policy.getIndoresmentNumber()));
        expiryDate.setValue(policy.getExpiryDate());
        issuanceDate.setValue(policy.getIssuanceDate());
    }

    public void sync(List<String> claimImagePath, List<String> policyImagePath, String collectiveImagePath) {
        policy.setPolicyNumber(policyNumber.getValue());
        policy.setAgentName(Utils.emptyToNull(agentName.getValue()));
        policy.setBeneficiary(Utils.emptyToNull(beneficiary.getValue()));
        if (clientName.getValue() != null && !clientName.getValue().isEmpty())
            policy.setClient(Utils.findClient(ClientConnector.clients, clientName.getValue()));
        else policy.setClient(null);
        policy.setCollective(Utils.stringToCollective(Utils.emptyToNull(collective.getValue())));
        policy.setCurrency(Utils.stringToCurrency(Utils.emptyToNull(currency.getValue())));
        policy.setGrossCommission(Utils.toBigDecimal(grossCommission.getValue()));
        policy.setGrossPremium(Utils.toBigDecimal(grossPremuim.getValue()));
        if (Utils.emptyToNull(taxes.getValue()) == null)
            policy.setTaxes(null);
        else
            policy.setTaxes(Utils.toBigDecimal(taxes.getValue().trim().replace("%", "")).divide(BigDecimal.valueOf(100.0)));
        policy.setInsuranceCompany(Utils.emptyToNull(insuranceCompany.getValue()));
        policy.setInsuranceType(Utils.emptyToNull(insuranceType.getValue()));
        policy.setExpiryDate(expiryDate.getValue());
        policy.setIssuanceDate(issuanceDate.getValue());
        policy.setPaidClaims(Utils.toBigDecimal(paidClaims.getValue()));
        policy.setSumInsured(Utils.toBigDecimal(sumInsured.getValue()));
        policy.setPolicyStatus(Utils.emptyToNull(policyStatus.getValue()));
        policy.setSpecialDiscount(Utils.toBigDecimal(specialDiscount.getValue()));
        policy.setClaimImagePath(claimImagePath);
        policy.setPolicyImagePath(policyImagePath);
        policy.setCollectiveImagePath(collectiveImagePath);
        policy.setIndoresmentNumber(Utils.emptyToNull(indoresmentNumber.getValue()));
        policy.calculateValues();
    }

    public boolean save() {
        return policy.save();
    }


}
