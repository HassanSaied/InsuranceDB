package sample.util;

import sample.model.Endorsement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class EndorsementConnector {

    public static List<Endorsement> endorsements;

    public static List<Endorsement> getEndorsements(String policyNumber) {
        List<Endorsement> endorsements = new Vector<Endorsement>();
        String selectSQL = "SELECT * FROM Endorsement WHERE policyNumber = ?;";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(selectSQL)) {
            statement.setString(1, policyNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Endorsement currentEndorsement = new Endorsement();
                    currentEndorsement.setPolicyNumber(resultSet.getString(1));
                    currentEndorsement.setEndorsementNumber(resultSet.getString(2));
                    currentEndorsement.setIssuanceDate(resultSet.getDate(3) == null ? null : resultSet.getDate(3).toLocalDate());
                    currentEndorsement.setGrossPremium(resultSet.getBigDecimal(4));
                    currentEndorsement.setSpecialDiscount(resultSet.getBigDecimal(5));
                    currentEndorsement.setNetPremium(resultSet.getBigDecimal(6));
                    currentEndorsement.setGrossCommission(resultSet.getBigDecimal(7));
                    currentEndorsement.setTaxes(resultSet.getBigDecimal(8));
                    currentEndorsement.setNetCommission(resultSet.getBigDecimal(9));
                    currentEndorsement.setImagePath(getEndorsementImagePath(currentEndorsement));
                    endorsements.add(currentEndorsement);

                }
            } catch (SQLException exception) {
                System.err.println("Couldn't get endorsements with PolicyNumber " + policyNumber);
                System.err.println("Exception" + exception.getMessage());
                return null;
            }

        } catch (SQLException exception) {
            System.err.println("Couldn't prepare statement to get endoresements with PolicyNumber " + policyNumber);
            System.err.println("Exception" + exception.getMessage());
            return null;
        }
        EndorsementConnector.endorsements = endorsements;
        return endorsements;
    }

    private static List<String> getEndorsementImagePath(Endorsement endorsement) {
        List<String> endoresmentImagePath = new Vector<String>();
        String selectSQL = "SELECT imagePath " +
                "FROM endoresmentImagePath " +
                "WHERE policyNumber = ? AND endoresmentNumber = ? ;";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(selectSQL)) {
            statement.setString(1, endorsement.getPolicyNumber());
            statement.setString(2, endorsement.getEndorsementNumber());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    endoresmentImagePath.add(resultSet.getString(1));
                }
            } catch (SQLException exception) {
                System.err.println("Couldn't get endorsement Image Path");
                System.err.println("Exception" + exception.getMessage());
                return null;

            }
        } catch (SQLException exception) {
            System.err.println("Couldn't prepare get endorsement Image Path statement ");
            System.err.println("Exception" + exception.getMessage());
            return null;

        }
        return endoresmentImagePath;
    }

    public static boolean insertEndorsement(Endorsement endorsement) {

        String insertSQL = "INSERT INTO Endorsement(issuanceDate, grossPremium, " +
                "specialDiscount, netPremium, grossCommission, taxes, netCommission,policyNumber, " +
                "endorsementNumber) VALUES (?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(insertSQL)) {
            fillStatement(statement,endorsement);
            try{
                statement.executeUpdate();
                if(!insertEndorsementImagePath(endorsement))
                    return false;
            }
            catch (SQLException exception) {
                System.err.println("Couldn't Insert endorsement");
                System.err.println("Exception" + exception.getMessage());
                return false;
            }

        } catch (SQLException exception) {
            System.err.println("Couldn't prepare Insert endorsement path");
            System.err.println("Exception" + exception.getMessage());
            return false;

        }

        return true;
    }

    private static void fillStatement(PreparedStatement statement, Endorsement endorsement) throws SQLException {
        int columnCounter = 0;
        statement.setDate(++columnCounter, endorsement.getIssuanceDate() == null ? null : Date.valueOf(endorsement.getIssuanceDate()));
        statement.setBigDecimal(++columnCounter,endorsement.getGrossPremium());
        statement.setBigDecimal(++columnCounter,endorsement.getSpecialDiscount());
        statement.setBigDecimal(++columnCounter,endorsement.getNetPremium());
        statement.setBigDecimal(++columnCounter,endorsement.getGrossCommission());
        statement.setBigDecimal(++columnCounter,endorsement.getTaxes());
        statement.setBigDecimal(++columnCounter,endorsement.getNetCommission());
        statement.setString(++columnCounter,endorsement.getPolicyNumber());
        statement.setString(++columnCounter,endorsement.getEndorsementNumber());
    }
    private static boolean insertEndorsementImagePath(Endorsement endorsement){
        String insertSQL = "INSERT INTO EndorsementImagePath (policyNumber, endorsementNumber, imagePath) VALUES (?,?,?);";
        try(PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(insertSQL)){
            for(String endorsementImagePath : endorsement.getImagePath()) {
                statement.setString(1, endorsement.getPolicyNumber());
                statement.setString(2, endorsement.getEndorsementNumber());
                statement.setString(3, endorsementImagePath);
                statement.executeUpdate();
            }
        }
        catch (SQLException exception){
            System.err.println("Couldn't Insert endorsement Image Path");
            System.err.println("Exception" + exception.getMessage());
            return false;

        }
        return true;
    }

    public static boolean updateEndorsement(Endorsement endorsement){
        String updateSQL = "UPDATE Endorsement SET issuanceDate = ?, grossPremium = ?,specialDiscount = ?, netPremium = ?,grossCommission = ? , taxes = ?, " +
                "netCommission = ? WHERE policyNumber = ? AND endorsementNumber = ?;";
        try(PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(updateSQL)){
            fillStatement(statement,endorsement);
            try{
                statement.executeUpdate();
                if(!insertEndorsementImagePath(endorsement))
                    return false;
            }
            catch (SQLException exception){
                System.err.println("Couldn't Update endorsement");
                System.err.println("Exception" + exception.getMessage());
                return false;
            }

        }
        catch (SQLException exception){
            System.err.println("Couldn't prepare Update endorsement statement");
            System.err.println("Exception" + exception.getMessage());
            return false;

        }
        return true;
    }
    public static boolean deleteEndorsement(Endorsement endorsement){
        String deleteSQL = "DELETE FROM Endorsement WHERE policyNumber = ? AND endorsementNumber = ?;";
        try(PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(deleteSQL)){
            statement.setString(1,endorsement.getPolicyNumber());
            statement.setString(2,endorsement.getEndorsementNumber());
            try{
                statement.executeUpdate();
            }
            catch (SQLException exception){
                System.err.println("Couldn't Delete endorsement");
                System.err.println("Exception" + exception.getMessage());
                return false;

            }
        }
        catch (SQLException exception) {
            System.err.println("Couldn't prepare Delete endorsement statement");
            System.err.println("Exception" + exception.getMessage());
            return false;
        }
        return true;
    }

}
