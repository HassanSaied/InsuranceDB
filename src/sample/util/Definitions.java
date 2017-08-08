package sample.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Definitions {

    public static ObservableList<String> collectiveList = FXCollections.observableArrayList("Cache", "Check", "None");
    public static ObservableList<String> imageTypes = FXCollections.observableArrayList("Policy Image", "Claim Image", "Collective Image");
    public static ObservableList<String> currencyList = FXCollections.observableArrayList("EGP", "USD", "EUR");
    public static ObservableList<String> taxes = FXCollections.observableArrayList("20%", "22.5%");
    public static String password = "ahmed";
}
