package sample.model;

import sample.util.EndorsementConnector;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Endorsement {

    private String policyNumber;
    private String endorsementNumber;
    private LocalDate issuanceDate;
    private BigDecimal grossPremium;
    private BigDecimal specialDiscount;
    private BigDecimal netPremium;
    private BigDecimal grossCommission;
    private BigDecimal taxes;
    private BigDecimal netCommission;
    private List<String> imagePath;
    boolean updatable = false;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getEndorsementNumber() {
        return endorsementNumber;
    }

    public void setEndorsementNumber(String endorsementNumber) {
        this.endorsementNumber = endorsementNumber;
    }

    public LocalDate getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(LocalDate issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public Endorsement(String policyNumber) {
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

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }

    public boolean save() {
        if (updatable)
            return EndorsementConnector.updateEndorsement(this);
        else {
            if (!EndorsementConnector.insertEndorsement(this))
                return false;
            updatable = true;
            return true;
        }
    }

    public boolean delete(){
        return EndorsementConnector.deleteEndorsement(this);
    }
    public void computeValues(){
        if(grossCommission != null && taxes!=null)
            netCommission = grossCommission.subtract(taxes.multiply(grossCommission));
        if(grossPremium !=null && specialDiscount != null)
            netPremium = grossPremium.subtract(specialDiscount);
    }
}
