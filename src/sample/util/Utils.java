package sample.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sample.model.Client;
import sample.model.Policy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by hassan on 6/30/17.
 */
public class Utils {

    public static Policy.Currency stringToCurrency(String currency){
        if(currency == null)
            return null;
        if(currency.equals("EGP"))
            return Policy.Currency.EGP;
        else if (currency.equals("EUR"))
            return Policy.Currency.EUR;
        else return Policy.Currency.USD;
    }

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

    public static Client findClient(List<Client> clientList,String clientName,String clientNumber){

        for (Client client: clientList) {
            if(client.getClientName().equals(clientName) && client.getClientPhoneNumber().equals(clientNumber))
                return client;

        }
        return null;

    }

}
