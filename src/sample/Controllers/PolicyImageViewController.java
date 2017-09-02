package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sample.model.Policy;
import sample.util.Definitions;
import sample.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static sample.util.Utils.addImageToVBox;

public class PolicyImageViewController {


    @FXML
    private VBox policyImageVBox, claimImageVBox, collectiveImageVBox;
    @FXML private Label policyNumberLabel,agentNameLabel,grossCommissionLabel,taxesLabel,netCommissionLabel;
    @FXML private PasswordField passwordField;
    private Policy policy;

    public PolicyImageViewController() {

    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
        Utils.loadImages(policyImageVBox, this.policy.getPolicyImagePath());
        Utils.loadImages(claimImageVBox, this.policy.getClaimImagePath());
        loadImages(collectiveImageVBox, this.policy.getCollectiveImagePath());
        policyNumberLabel.setText(policy.getPolicyNumber());
        agentNameLabel.setText(policy.getAgentName());
        grossCommissionLabel.setText(Utils.getMappedString(policy.getGrossCommission()));
        taxesLabel.setText(Utils.taxesToString(policy.getTaxes()));
        netCommissionLabel.setText(Utils.getMappedString(policy.getNetCommission()));

    }

    @FXML
    private void initialize(){
        grossCommissionLabel.visibleProperty().bind(passwordField.textProperty().isEqualTo(Definitions.password));
        taxesLabel.visibleProperty().bind(passwordField.textProperty().isEqualTo(Definitions.password));
        netCommissionLabel.visibleProperty().bind(passwordField.textProperty().isEqualTo(Definitions.password));
    }



    private void loadImages(VBox vBox, String imagePath) {
        if (imagePath != null) {
            try {
                addImageToVBox(imagePath,vBox);
            } catch (IOException exception) {
                System.err.println("Can't load Image");
                System.err.println("Exception " + exception.getMessage());
            }
        }
    }



}
