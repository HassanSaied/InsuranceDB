package sample.Mappers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.model.Client;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

/**
 * Created by hassan on 7/13/17.
 */
public class ClientMapper {

    private final StringProperty clientName;
    private final StringProperty clientPhoneNumber;


    public StringProperty clientNameProperty() {
        return clientName;
    }


    public StringProperty clientPhoneNumberProperty() {
        return clientPhoneNumber;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    private Client client;

    public ClientMapper(Client client){
        if(client == null){
            clientName = new SimpleStringProperty(null);
            clientPhoneNumber = new SimpleStringProperty(null);
        }
        else {
            this.client = client;
            clientName = new SimpleStringProperty(this.client.getClientName());
            clientPhoneNumber = new SimpleStringProperty(this.client.getClientPhoneNumber());
        }
    }

    public Client getClient (){
        return this.client;
    }


    @Override
    public String toString() {
        return clientNameProperty().getValue();
    }



}
