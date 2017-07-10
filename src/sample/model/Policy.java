package sample.model;

import sample.util.PolicyConnector;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by hassan on 6/29/17.
 */
public class Policy {

    public enum Currency {
        EGP,
        USD,
        EUR;

        @Override
        public String toString() {
            switch (this) {
                case EGP:
                    return "EGP";
                case USD:
                    return "USD";
                case EUR:
                    return "EUR";
            }
            return null;
        }
    }

    public enum Collective {
        Cache,
        Check,
        None;

        @Override
        public String toString() {
            switch (this) {

                case Cache:
                    return "Cache";
                case Check:
                    return "Check";
                case None:
                    return "None";
            }
            return null;
        }

    }

    private String agentName;
    private String insuranceCompany;
    private String insuranceType;
    private String beneficiary;
    private Client client;
    private String policyNumber;
    private BigDecimal grossPremium;
    private BigDecimal specialDiscount;
    private BigDecimal netPremium;
    private BigDecimal grossCommission;
    private BigDecimal taxes;
    private BigDecimal netCommission;
    private LocalDate expiryDate;
    private BigDecimal sumInsured;
    private Currency currency;
    private Collective collective;
    private String collectiveImagePath;
    private String policyStatus;
    private BigDecimal paidClaims;
    private String indoresmentNumber;
    private List<String> policyImagePath;
    private List<String> claimImagePath;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public BigDecimal getGrossPremium() {
        return grossPremium;
    }

    public void setGrossPremium(BigDecimal grossPremium) {
        this.grossPremium = grossPremium;
    }

    public BigDecimal getSpecialDiscount() {
        return specialDiscount;
    }

    public void setSpecialDiscount(BigDecimal specialDiscount) {
        this.specialDiscount = specialDiscount;
    }

    public BigDecimal getNetPremium() {
        return netPremium;
    }

    public void setNetPremium(BigDecimal netPremium) {
        this.netPremium = netPremium;
    }

    public BigDecimal getGrossCommission() {
        return grossCommission;
    }

    public void setGrossCommission(BigDecimal grossCommission) {
        this.grossCommission = grossCommission;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getNetCommission() {
        return netCommission;
    }

    public void setNetCommission(BigDecimal netCommission) {
        this.netCommission = netCommission;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(BigDecimal sumInsured) {
        this.sumInsured = sumInsured;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Collective getCollective() {
        return collective;
    }

    public void setCollective(Collective collective) {
        this.collective = collective;
    }

    public String getCollectiveImagePath() {
        return collectiveImagePath;
    }

    public void setCollectiveImagePath(String collectiveImagePath) {
        this.collectiveImagePath = collectiveImagePath;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public BigDecimal getPaidClaims() {
        return paidClaims;
    }

    public void setPaidClaims(BigDecimal paidClaims) {
        this.paidClaims = paidClaims;
    }

    public String getIndoresmentNumber() {
        return indoresmentNumber;
    }

    public void setIndoresmentNumber(String indoresmentNumber) {
        this.indoresmentNumber = indoresmentNumber;
    }

    public List<String> getPolicyImagePath() {
        return policyImagePath;
    }

    public void setPolicyImagePath(List<String> policyImagePath) {
        this.policyImagePath = policyImagePath;
    }

    public List<String> getClaimImagePath() {
        return claimImagePath;
    }

    public void setClaimImagePath(List<String> claimImagePath) {
        this.claimImagePath = claimImagePath;
    }

    private boolean updatable = false;

    public boolean save() {
        if (updatable)
            return PolicyConnector.updatePolicy(this);
        else {
            updatable = true;
            return PolicyConnector.insertPolicy(this);
        }
    }

    public boolean delete() {
        return PolicyConnector.deletePolicy(this);
    }
}
