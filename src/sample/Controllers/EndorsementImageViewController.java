package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import sample.model.Endorsement;
import sample.util.Definitions;
import sample.util.Utils;

import static sample.util.Utils.loadImages;

public class EndorsementImageViewController {

    @FXML private VBox endorsementImageVBox;
    @FXML private Label policyNumberLabel,endorsementNumberLabel,grossCommissionLabel,taxesLabel,netCommissionLabel;
    @FXML private PasswordField passwordField;
    private Endorsement endorsement;

    public void setEndorsement(Endorsement endorsement) {
        this.endorsement = endorsement;
        loadImages(endorsementImageVBox, this.endorsement.getImagePath());
        policyNumberLabel.setText(endorsement.getPolicyNumber());
        endorsementNumberLabel.setText(endorsement.getEndorsementNumber());
        grossCommissionLabel.setText(Utils.getMappedString(endorsement.getGrossCommission()));
        taxesLabel.setText(Utils.taxesToString(endorsement.getTaxes()));
        netCommissionLabel.setText(Utils.getMappedString(endorsement.getNetCommission()));

    }

    @FXML
    private void initialize(){
        grossCommissionLabel.visibleProperty().bind(passwordField.textProperty().isEqualTo(Definitions.password));
        taxesLabel.visibleProperty().bind(passwordField.textProperty().isEqualTo(Definitions.password));
        netCommissionLabel.visibleProperty().bind(passwordField.textProperty().isEqualTo(Definitions.password));
    }
}
