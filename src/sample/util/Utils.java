package sample.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sample.model.Client;
import sample.model.Policy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by hassan on 6/30/17.
 */
public class Utils {

    @Contract("null -> null")
    public static Policy.Currency stringToCurrency(String currency){
        if(currency == null)
            return null;
        if(currency.equals("EGP"))
            return Policy.Currency.EGP;
        else if (currency.equals("EUR"))
            return Policy.Currency.EUR;
        else return Policy.Currency.USD;
    }

    @Contract("null -> null")
    public static Policy.Collective stringToCollective (String collective){
        if(collective == null)
            return null;
        if(collective.equals("Cache"))
            return Policy.Collective.Cache;
        else if (collective.equals("Check"))
            return Policy.Collective.Check;
        else return Policy.Collective.None;
    }

    @Contract("null -> !null")
    public static <T> String getMappedString(T property){
        if(property == null)
            return "";
        else return property.toString();
    }

    @NotNull
    public static String getMappedString(LocalDate date){
        if(date == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(date);
    }

    @Nullable
    public static Client findClient(List<Client> clientList, String clientName){

        for (Client client: clientList) {
            if(client.getClientPhoneNumber() != null)
            if(client.getClientName().equals(clientName))
                return client;

        }
        return null;

    }

    public static boolean isDouble(String testedString){
        if(testedString.isEmpty())
            return true;
        try{
            Double.parseDouble(testedString);
        }
        catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    @Nullable
    @Contract(pure = true)
    public static String emptyToNull(String testedString){
        if(testedString.isEmpty())
            return null;
        else return testedString;
    }

    @Nullable
    public static BigDecimal toBigDecimal (String convertibleString){
        if(convertibleString.isEmpty())
            return null;
        else return new BigDecimal(convertibleString);
    }

    @Contract(value = "null -> null", pure = true)
    public static String taxesToString(BigDecimal taxes){
        if(taxes == null){
            return null;
        }
        return taxes.equals(BigDecimal.valueOf(0.2))?"20%":"22.5%";
    }

}
