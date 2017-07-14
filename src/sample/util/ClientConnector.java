package sample.util;

import org.jetbrains.annotations.Nullable;
import sample.model.Client;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

/**
 * Created by hassan on 7/1/17.
 */
public class ClientConnector {


    public static Client getClient(int clientID) {

        if(clientID == 0)
            return null;
        String selectSQL = "SELECT * FROM Clients WHERE clientID = ?;";
        Client client = new Client(clientID);
        try(PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(selectSQL)){
            statement.setInt(1,clientID);
            try(ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                client.setClientName(resultSet.getString(2));
                client.setClientPhoneNumber(resultSet.getString(3));
            }
            catch (SQLException exception){
                System.out.println("Can't get Client");
                System.out.println("Exception "+exception.getMessage());
                return null;
            }
        }
        catch (SQLException exception){
            System.out.println("Can't prepare Client select statement");
            System.out.println("Exception "+exception.getMessage());
            return null;
        }
        return client;
    }


    @Nullable
    public static List<Client> getClients(){

        List<Client> clients = new Vector<Client>();
        try(Statement statement = DatabaseConnector.getDatabaseConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Clients;");
            while (resultSet.next()){
                Client currentClient = new Client(resultSet.getInt(1));
                currentClient.setClientName(resultSet.getString(2));
                currentClient.setClientPhoneNumber(resultSet.getString(3));
            }

        }
        catch (SQLException exception){
            System.out.println("Can't get All Clients");
            System.out.println("Exception "+exception.getMessage());
            return null;
        }
        return clients;
    }

    public static void updateClient(Client client){
        //language=MySQL
        String updateSQL =  "UPDATE Clients SET InsuranceDB.Clients.clientName = '?'"+
                            ", InsuranceDB.Clients.clientNumber = '?'"+
                            " WHERE InsuranceDB.Clients.clientID = ?;";
        try(PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(updateSQL)){
            statement.setString(1,client.getClientName());
            statement.setString(2,client.getClientPhoneNumber());
            statement.setInt(3,client.getClientID());
            try{
                statement.executeUpdate();
            }
            catch (SQLException exception){
                System.out.println("Can't update Client");
                System.out.println("Exception "+exception.getMessage());
            }

        }
        catch (SQLException exception){
            System.out.println("Can't prepare update Client statement");
            System.out.println("Exception "+exception.getMessage());
        }
    }

}
