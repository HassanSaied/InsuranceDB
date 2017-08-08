package sample.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

public class Definitions {

    public static ObservableList<String> collectiveList = FXCollections.observableArrayList("Cache", "Check", "None");
    public static ObservableList<String> imageTypes = FXCollections.observableArrayList("Policy Image", "Claim Image", "Collective Image");
    public static ObservableList<String> currencyList = FXCollections.observableArrayList("EGP", "USD", "EUR");
    public static ObservableList<String> taxes = FXCollections.observableArrayList(new StringBuilder(BigDecimal.valueOf(0.2).multiply(BigDecimal.valueOf(100)).toString()).append('%').toString(),
            new StringBuilder(BigDecimal.valueOf(0.225).multiply(BigDecimal.valueOf(100)).toString()).append('%').toString());
    public static String password = "ahmed";
}
