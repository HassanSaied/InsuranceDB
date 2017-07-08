package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
        try{policyBorderPane = FXMLLoader.load(getClass().getResource("Views/PolicyViewer.fxml"));
        }
        catch (IOException exception){
            System.err.println("Can't Load Policy View");
        }

        Tab policyViewerTab = new Tab("Policy Viewer");
        policyViewerTab.setContent(policyBorderPane);
    }
}
