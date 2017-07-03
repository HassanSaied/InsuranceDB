package sample.util;

import sample.model.Policy;

/**
 * Created by hassan on 6/30/17.
 */
public class Utils {

    public static Policy.Currency stringToCurrency(String currency){

        if(currency.equals("EGP"))
            return Policy.Currency.EGP;
        else if (currency.equals("EUR"))
            return Policy.Currency.EUR;
        else return Policy.Currency.USD;
    }

    public static Policy.Collective stringToCollective (String collective){
        if(collective.equals("Cache"))
            return Policy.Collective.Cache;
        else if (collective.equals("Check"))
            return Policy.Collective.Check;
        else return Policy.Collective.None;
    }
}
