package sample.util;

import org.jetbrains.annotations.Nullable;
import sample.model.Endorsement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class EndorsementConnector {

    @Nullable
    public static List<Endorsement> getEndorsements(String policyNumber) {
        List<Endorsement> endorsements = new Vector<Endorsement>();
        String selectSQL = "SELECT * FROM endorsements WHERE policyNumber = ?;";
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
                    currentEndorsement.setImagePath(getEndoresmentImagePath(currentEndorsement));
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
        return endorsements;
    }

    @Nullable
    private static List<String> getEndoresmentImagePath(Endorsement endorsement) {
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
            System.err.println("Couldn't prepare get endoresment Image Path statement ");
            System.err.println("Exception" + exception.getMessage());
            return null;

        }
        return endoresmentImagePath;
    }

    //public static insertIndoresment
}
