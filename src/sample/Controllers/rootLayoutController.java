package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Main;

import java.io.IOException;

/**
 * Created by hassan on 7/8/17.
 */
public class rootLayoutController {
    @FXML private TabPane tabPane;

    public rootLayoutController(){

    }

    @FXML private void initialize(){
        BorderPane policyBorderPane = null;
        try{policyBorderPane = FXMLLoader.load(Main.class.getResource("Views/PolicyViewer.fxml"));
        }
        catch (IOException exception){
            System.err.println("Can't Load Policy View");
        }

        Tab policyViewerTab = new Tab("Policy Viewer");
        AnchorPane.setTopAnchor(policyBorderPane,0.0);
        AnchorPane.setRightAnchor(policyBorderPane,0.0);
        AnchorPane.setLeftAnchor(policyBorderPane,0.0);
        AnchorPane.setBottomAnchor(policyBorderPane,0.0);
        AnchorPane policyAnchorPane = new AnchorPane(policyBorderPane);
        policyViewerTab.setContent(policyAnchorPane);
        tabPane.getTabs().add(policyViewerTab);
    }
}
