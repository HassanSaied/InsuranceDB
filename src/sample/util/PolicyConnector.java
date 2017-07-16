package sample.util;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;
import sample.model.Policy;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by hassan on 6/29/17.
 */
public class PolicyConnector {
    public static List<Policy> getPolicies() {
        List<Policy> policies = new Vector<Policy>();
        try (
                Statement statement = DatabaseConnector.getDatabaseConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Policy ;");) {
            while (resultSet.next()) {
                Policy currentPolicy = new Policy();
                currentPolicy.setAgentName(resultSet.getString(1));
                currentPolicy.setInsuranceCompany(resultSet.getString(2));
                currentPolicy.setInsuranceType(resultSet.getString(3));
                currentPolicy.setBeneficiary(resultSet.getString(4));
                currentPolicy.setClient(ClientConnector.getClient(resultSet.getInt(5)));
                currentPolicy.setPolicyNumber(resultSet.getString(6));
                currentPolicy.setGrossPremium(resultSet.getBigDecimal(7));
                currentPolicy.setSpecialDiscount(resultSet.getBigDecimal(8));
                currentPolicy.setNetPremium(resultSet.getBigDecimal(9));
                currentPolicy.setGrossCommission(resultSet.getBigDecimal(10));
                currentPolicy.setNetCommission(resultSet.getBigDecimal(11));
                currentPolicy.setTaxes(resultSet.getBigDecimal(12));
                currentPolicy.setExpiryDate(resultSet.getDate(13)==null?null:resultSet.getDate(13).toLocalDate());
                currentPolicy.setSumInsured(resultSet.getBigDecimal(14));
                currentPolicy.setCurrency(Utils.stringToCurrency(resultSet.getString(15)));
                currentPolicy.setCollective(Utils.stringToCollective(resultSet.getString(16)));
                currentPolicy.setCollectiveImagePath(resultSet.getString(17));
                currentPolicy.setPolicyStatus(resultSet.getString(18));
                currentPolicy.setPaidClaims(resultSet.getBigDecimal(19));
                currentPolicy.setIndoresmentNumber(resultSet.getString(20));
                currentPolicy.setClaimImagePath(getClaimImagesPath(currentPolicy.getPolicyNumber()));
                currentPolicy.setPolicyImagePath(getPolicyImagesPath(currentPolicy.getPolicyNumber()));
                policies.add(currentPolicy);
            }


        } catch (SQLException exception) {
            System.err.println("Can't get Policies");
            System.err.println("Exception " + exception.getMessage());

        }

        return policies;
    }


    private static List<String> getClaimImagesPath(String policyNumber) {
        List<String> claimImagesPath = new Vector<String>();

        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement("SELECT claimImagePath FROM ClaimImagePath WHERE policyNumber = ? ;")) {
            statement.setString(1, policyNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()){
                    claimImagesPath.add(resultSet.getString(1));
                }

            } catch (SQLException exception) {
                System.err.println("Can't get result set containing the claim Images");
                System.err.println("Exception " + exception.getMessage());
            }
        } catch (SQLException exception) {
            System.err.println("Can't prepare statement for getting the claim Images");
            System.err.println("Exception " + exception.getMessage());
        }
        return claimImagesPath;
    }


    private static List<String> getPolicyImagesPath(String policyNumber) {

        List<String> policyImagesPath = new Vector<String>();
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement("SELECT policyImagePath FROM PolicyImagePath WHERE policyNumber = ? ;");) {
            statement.setString(1, policyNumber);


            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    policyImagesPath.add(resultSet.getString(1));
                }
            } catch (SQLException exception) {
                System.err.println("Can't get result set containing  policyImagesPath");
                System.err.println("Exception " + exception.getMessage());
            }
        } catch (SQLException exception) {
            System.err.println("Can't prepare the statement to get policyImagesPath");
            System.err.println("Exception " + exception.getMessage());
        }
        return policyImagesPath;
    }


    public static boolean updatePolicy(Policy policy) {

        String updateSQL = "UPDATE Policy " +
                "SET agentName = ? ," + "insuranceCompany = ?," + " insuranceType = ? ," + " beneficiary = ? ," +
                " clientID = ? ," + " grossPremuim = ? ," + " specialDiscount = ? ," + " netPremuim = ? ," +
                " grossCommission = ? ," + " taxes = ? ," + " netCommission = ? ," + " expiryDate = ? ," +
                " sumInssured = ? ," + " currency = ? ," + " collective = ? ," + " collectiveImagePath = ?," +
                " policyStatus = ?," + " paidClaims = ? ," + " indoresmentNumber = ? " + "WHERE policyNumber = ?; ";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(updateSQL)) {
            statement.setString(fillStatement(statement, policy), policy.getPolicyNumber());
            try {
                statement.executeUpdate();
            } catch (SQLException exception) {
                System.err.println("Can't Update Policy");
                System.err.println("Exception " + exception.getMessage());
                return false;

            }
            if (!updateClaimImagesPath(policy) || !updatePolicyImagesPath(policy))
                return false;

        } catch (SQLException exception) {
            System.err.println("Can't Prepare Update Policy Statement");
            System.err.println("Exception " + exception.getMessage());
            return false;
        }
        return true;

    }

    private static boolean updatePolicyImagesPath(Policy policy) {
        String updateSQL = "UPDATE PolicyImagePath SET  InsuranceDB.PolicyImagePath.policyImagePath = ? WHERE InsuranceDB.PolicyImagePath.policyNumber = ?;";

        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(updateSQL)) {
            try {
                for (String policyImagePath : policy.getPolicyImagePath()) {
                    statement.setString(1, policyImagePath);
                    statement.setString(2, policy.getPolicyNumber());
                    statement.executeUpdate();
                }
            } catch (SQLException exception) {
                System.err.println("Can't Update Policy Images Path");
                System.err.println("Exception " + exception.getMessage());
                return false;

            }

        } catch (SQLException exception) {
            System.err.println("Can't Prepare Update Policy Images Path Statement");
            System.err.println("Exception " + exception.getMessage());
            return false;

        }
        return true;

    }

    private static boolean updateClaimImagesPath(Policy policy) {
        String updateSQL = "UPDATE ClaimImagePath SET InsuranceDB.ClaimImagePath.claimImagePath = ? WHERE InsuranceDB.ClaimImagePath.policyNumber = ?;";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(updateSQL)) {
            try {
                for (String claimImagePath : policy.getPolicyImagePath()) {
                    statement.setString(1, claimImagePath);
                    statement.setString(2, policy.getPolicyNumber());
                    statement.executeUpdate();
                }
            } catch (SQLException exception) {
                System.err.println("Can't Update Claim Images Path");
                System.err.println("Exception " + exception.getMessage());
                return false;

            }

        } catch (SQLException exception) {
            System.err.println("Can't Prepare Update Claim Images Path Statement");
            System.err.println("Exception " + exception.getMessage());
            return false;

        }
        return true;

    }

    public static boolean insertPolicy(Policy policy) {

        @Language("MySQL") String insertSQL = "INSERT INTO Policy (agentName,insuranceCompany,insuranceType,beneficiary" +
                "  ,clientID,grossPremuim,specialDiscount,netPremuim" +
                "  ,grossCommission,taxes,netCommission,expiryDate" +
                "  ,sumInssured,currency,collective,collectiveImagePath" +
                "  ,policyStatus,paidClaims,indoresmentNumber,policyNumber)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(insertSQL)) {
            statement.setString(fillStatement(statement, policy), policy.getPolicyNumber());
            try {
                statement.executeUpdate();
            } catch (SQLException exception) {
                System.err.println("Can't Insert Policy");
                System.err.println("Exception " + exception.getMessage());
                return false;

            }
            if (!insertPolicyImagesPath(policy) || !insertClaimImagesPath(policy))
                return false;
        } catch (SQLException exception) {
            System.err.println("Can't Prepare Insert Policy Statement");
            System.err.println("Exception " + exception.getMessage());
            return false;
        }

        return true;
    }

    private static boolean insertPolicyImagesPath(Policy policy) {
        String insertSQL = "INSERT INTO PolicyImagePath (policyImagePath, policyNumber) VALUES  (?,?);";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(insertSQL)) {
            try {
                for (String policyImagePath : policy.getPolicyImagePath()) {
                    statement.setString(1, policyImagePath);
                    statement.setString(2, policy.getPolicyNumber());
                    statement.executeUpdate();
                }
            } catch (SQLException exception) {
                System.err.println("Can't  Insert Policy Images Path ");
                System.err.println("Exception " + exception.getMessage());
                return false;
            }
        } catch (SQLException exception) {
            System.err.println("Can't Prepare Insert Policy Images Path Statement");
            System.err.println("Exception " + exception.getMessage());
            return false;
        }
        return true;
    }


    private static boolean insertClaimImagesPath(Policy policy) {
        String insertSQL = "INSERT INTO ClaimImagePath (claimImagePath, policyNumber) VALUES (?,?);";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(insertSQL)) {
            try {
                for (String policyImagePath : policy.getPolicyImagePath()) {
                    statement.setString(1, policyImagePath);
                    statement.setString(2, policy.getPolicyNumber());
                    statement.executeUpdate();
                }
            } catch (SQLException exception) {
                System.err.println("Can't Prepare Insert Claim Images Path");
                System.err.println("Exception " + exception.getMessage());
                return false;
            }
        } catch (SQLException exception) {
            System.err.println("Can't Prepare Insert Claim Images Path Statement");
            System.err.println("Exception " + exception.getMessage());
            return false;
        }
        return true;
    }

    private static int fillStatement(PreparedStatement statement, Policy policy) throws SQLException {
        int columnCounter = 0;
        statement.setString(++columnCounter, "\""+policy.getAgentName()+"\"");
        statement.setString(++columnCounter, policy.getInsuranceCompany());
        statement.setString(++columnCounter, policy.getInsuranceType());
        statement.setString(++columnCounter, policy.getBeneficiary());
        statement.setInt(++columnCounter, policy.getClient().getClientID());
        statement.setBigDecimal(++columnCounter, policy.getGrossPremium());
        statement.setBigDecimal(++columnCounter, policy.getSpecialDiscount());
        statement.setBigDecimal(++columnCounter, policy.getNetPremium());
        statement.setBigDecimal(++columnCounter, policy.getGrossCommission());
        statement.setBigDecimal(++columnCounter, policy.getTaxes());
        statement.setBigDecimal(++columnCounter, policy.getNetCommission());
        statement.setDate(++columnCounter, Date.valueOf(policy.getExpiryDate()));
        statement.setBigDecimal(++columnCounter, policy.getSumInsured());
        statement.setString(++columnCounter, policy.getCurrency().toString());
        statement.setString(++columnCounter, policy.getCollective().toString());
        statement.setString(++columnCounter, policy.getCollectiveImagePath());
        statement.setString(++columnCounter, policy.getPolicyStatus());
        statement.setBigDecimal(++columnCounter, policy.getPaidClaims());
        statement.setString(++columnCounter, policy.getIndoresmentNumber());
        return columnCounter;
    }


    public static boolean deletePolicy(Policy policy) {

        //language=MySQL
        String deleteSQL = "DELETE FROM Policy WHERE policyNumber = ?;";
        try (PreparedStatement statement = DatabaseConnector.getDatabaseConnection().prepareStatement(deleteSQL)) {
            statement.setString(1, policy.getPolicyNumber());
            try {
                statement.executeUpdate();
            } catch (SQLException exception) {
                System.err.println("Can't delete policy");
                System.err.println("Exception " + exception.getMessage());
                return false;
            }
        } catch (SQLException exception) {
            System.err.println("Can't prepare delete policy statement");
            System.err.println("Exception " + exception.getMessage());
            return false;
        }
        return true;

    }

    @Nullable
    public static List<String> getInsuranceTypes (){
        List<String> insuranceTypes = new Vector<String>();
        String selectSQL = "SELECT * FROM InsuranceType;";
        try(Statement statement = DatabaseConnector.getDatabaseConnection().createStatement()){
            try(ResultSet resultSet = statement.executeQuery(selectSQL)) {
                while (resultSet.next()){
                    insuranceTypes.add(resultSet.getString(1));
                }

            }
            catch (SQLException exception){
                System.err.println("Can't execute the insurance types statement");
                System.err.println("Exception "+exception.getMessage());
                return null;
            }

        }
        catch (SQLException exception){
            System.err.println("Can't create the insurance types statement");
            System.err.println("Exception "+exception.getMessage());
            return null;
        }
        return insuranceTypes;
    }
}
