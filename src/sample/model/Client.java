package sample.model;

import sample.util.ClientConnector;

/**
 * Created by hassan on 6/29/17.
 */
public class Client {

    private int clientID;
    private String clientPhoneNumber;
    private String clientName;

    public Client(String clientPhoneNumber, String clientName) {
        setClientName(clientName);
        setClientPhoneNumber(clientPhoneNumber);
    }

    public Client(int clientID){
        setClientID(clientID);
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public void save(){
        ClientConnector.updateClient(this);
    }

}
