package sample.Mappers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.model.Endorsement;
import sample.util.Utils;

import java.time.LocalDate;
import java.util.List;

public class EndorsementMapper {

    public StringProperty policyNumberProperty() {
        return policyNumber;
    }

    public StringProperty endorsementNumberProperty() {
        return endorsementNumber;
    }

    public StringProperty grossPremiumProperty() {
        return grossPremium;
    }

    public StringProperty netPremiumProperty() {
        return netPremium;
    }

    public StringProperty grossCommissionProperty() {
        return grossCommission;
    }

    public StringProperty netCommissionProperty() {
        return netCommission;
    }

    public StringProperty specialDiscountProperty() {
        return specialDiscount;
    }

    public StringProperty taxesProperty() {
        return taxes;
    }

    public ObjectProperty<LocalDate> issuanceDateProperty() {
        return issuanceDate;
    }

    private Endorsement endorsement;
    private final StringProperty policyNumber;
    private final StringProperty endorsementNumber;
    private final StringProperty grossPremium;
    private final StringProperty netPremium;
    private final StringProperty grossCommission;
    private final StringProperty netCommission;
    private final StringProperty specialDiscount;
    private final StringProperty taxes;
    private final ObjectProperty<LocalDate> issuanceDate;

    public EndorsementMapper(Endorsement endorsement) {
        this.endorsement = endorsement;
        policyNumber = new SimpleStringProperty(this.endorsement.getPolicyNumber());
        endorsementNumber = new SimpleStringProperty(this.endorsement.getEndorsementNumber());
        grossPremium = new SimpleStringProperty(Utils.getMappedString(this.endorsement.getGrossPremium()));
        netPremium = new SimpleStringProperty(Utils.getMappedString(this.endorsement.getNetPremium()));
        grossCommission = new SimpleStringProperty(Utils.getMappedString(this.endorsement.getGrossCommission()));
        netCommission = new SimpleStringProperty(Utils.getMappedString(this.endorsement.getNetCommission()));
        specialDiscount = new SimpleStringProperty(Utils.getMappedString(this.endorsement.getSpecialDiscount()));
        taxes = new SimpleStringProperty(Utils.getMappedString(this.endorsement.getTaxes()));
        issuanceDate = new SimpleObjectProperty<>(this.endorsement.getIssuanceDate() == null ? null : this.endorsement.getIssuanceDate());
    }

    public void setEndorsement(Endorsement endorsement) {
        this.endorsement = endorsement;
        policyNumber.setValue(endorsement.getPolicyNumber());
        endorsementNumber.setValue(this.endorsement.getEndorsementNumber());
        grossPremium.setValue(Utils.getMappedString(this.endorsement.getGrossPremium()));
        netPremium.setValue(Utils.getMappedString(this.endorsement.getNetPremium()));
        grossCommission.setValue(Utils.getMappedString(this.endorsement.getGrossCommission()));
        netCommission.setValue(Utils.getMappedString(this.endorsement.getNetCommission()));
        specialDiscount.setValue(Utils.getMappedString(this.endorsement.getSpecialDiscount()));
        taxes.setValue(Utils.getMappedString(this.endorsement.getTaxes()));
        issuanceDate.setValue(endorsement.getIssuanceDate());
    }

    public void sync(List<String> endorsementImagePath) {
        endorsement.setEndorsementNumber(endorsementNumber.getValue());
        endorsement.setGrossPremium(Utils.toBigDecimal(grossPremium.getValue()));
        endorsement.setGrossCommission(Utils.toBigDecimal(grossCommission.getValue()));
        endorsement.setNetPremium(Utils.toBigDecimal(netPremium.getValue()));
        endorsement.setNetCommission(Utils.toBigDecimal(netCommission.getValue()));
        endorsement.setSpecialDiscount(Utils.toBigDecimal(specialDiscount.getValue()));
        endorsement.setTaxes(Utils.stringToTaxes(taxes.getValue()));
        endorsement.setIssuanceDate(issuanceDate.getValue());
        endorsement.setImagePath(endorsementImagePath);
        endorsement.computeValues();

    }

    public boolean save() {
        return endorsement.save();
    }
    public boolean delete(){
        return endorsement.delete();
    }
    public Endorsement getEndorsement(){
        return endorsement;
    }


}
