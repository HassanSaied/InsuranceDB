package sample.util;

import org.jetbrains.annotations.Nullable;
import sample.model.Client;

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

    public static List<Client> clients;

    @Nullable
    public static Client getClient(int clientID) {
        if(clientID == 0)
            return null;        //TODO: throw exception here, but really will never reach it xD
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
        client.setUpdatable();
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
                currentClient.setUpdatable();
                clients.add(currentClient);
            }

        }
        catch (SQLException exception){
            System.out.println("Can't get All Clients");
            System.out.println("Exception "+exception.getMessage());
            return null;
        }
        ClientConnector.clients = clients;
        return clients;
    }

    public static boolean insertClient(Client client){

        String insertStatement = "INSERT INTO Clients " +
                "(InsuranceDB.Clients.clientName, InsuranceDB.Clients.clientNumber)" +
                " VALUES(?,?)";
        try(PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(insertStatement)){
            statement.setString(1,client.getClientName());
            statement.setString(2,client.getClientPhoneNumber());
            try{
                statement.executeUpdate();
            }
            catch (SQLException exception){
                System.out.println("Can't Insert Client");
                System.out.println("Exception "+exception.getMessage());
                return false;
            }
        }
        catch (SQLException exception){
            System.out.println("Can't prepare insert Client statement");
            System.out.println("Exception "+exception.getMessage());
            return false;
        }

        return true;

    }

    public static boolean updateClient(Client client){
        //language=MySQL
        String updateSQL =  "UPDATE Clients SET InsuranceDB.Clients.clientName = ?"+
                            ", InsuranceDB.Clients.clientNumber = ?"+
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
                return false;
            }

        }
        catch (SQLException exception){
            System.out.println("Can't prepare update Client statement");
            System.out.println("Exception "+exception.getMessage());
            return false;
        }
        return true;
    }

}
