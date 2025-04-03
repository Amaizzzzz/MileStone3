package cs5200project.dal;

import cs5200project.model.Currency;
import java.sql.*;
import java.util.*;

public class CurrencyDao {
    // Dao classes should not be instantiated.
    // Pass Connection object as parameter in each method
    // Each method should be static
    private CurrencyDao() {
        // Private constructor to prevent instantiation
    }
    
    public static Currency create(Connection cxn, String currencyName, Integer cap, Integer weeklyCap) throws SQLException {
        String query = "INSERT INTO Currency (currencyName, cap, weeklyCap) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = cxn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, currencyName);
            
            if (cap != null) {
                stmt.setInt(2, cap);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            if (weeklyCap != null) {
                stmt.setInt(3, weeklyCap);
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            return new Currency(Utils.getAutoIncrementKey(stmt), currencyName, cap, weeklyCap);
        }
    }
    
    public static Currency getCurrencyById(Connection cxn, int currencyID) throws SQLException {
        String query = "SELECT * FROM Currency WHERE currencyID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setInt(1, currencyID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Integer cap = rs.getObject("cap") != null ? rs.getInt("cap") : null;
                    Integer weeklyCap = rs.getObject("weeklyCap") != null ? rs.getInt("weeklyCap") : null;
                    
                    return new Currency(
                        rs.getInt("currencyID"),
                        rs.getString("currencyName"),
                        cap,
                        weeklyCap
                    );
                }
            }
        }
        return null;
    }
    
    // Maybe not needed
    public static List<Currency> getAllCurrencies(Connection cxn) throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM Currency";
        try (PreparedStatement stmt = cxn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer cap = rs.getObject("cap") != null ? rs.getInt("cap") : null;
                Integer weeklyCap = rs.getObject("weeklyCap") != null ? rs.getInt("weeklyCap") : null;
                
                currencies.add(new Currency(
                    rs.getInt("currencyID"),
                    rs.getString("currencyName"),
                    cap,
                    weeklyCap
                ));
            }
        }
        return currencies;
    }
    
    public static Currency getCurrencyByName(Connection cxn, String currencyName) throws SQLException {
        String query = "SELECT * FROM Currency WHERE currencyName = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setString(1, currencyName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Integer cap = rs.getObject("cap") != null ? rs.getInt("cap") : null;
                    Integer weeklyCap = rs.getObject("weeklyCap") != null ? rs.getInt("weeklyCap") : null;
                    
                    return new Currency(
                        rs.getInt("currencyID"),
                        rs.getString("currencyName"),
                        cap,
                        weeklyCap
                    );
                }
            }
        }
        return null;
    }
    
    public static Currency updateCurrency(Connection cxn, int currencyID, String currencyName, 
                                         Integer cap, Integer weeklyCap) throws SQLException {
        String query = "UPDATE Currency SET currencyName = ?, cap = ?, weeklyCap = ? WHERE currencyID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setString(1, currencyName);
            
            if (cap != null) {
                stmt.setInt(2, cap);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            if (weeklyCap != null) {
                stmt.setInt(3, weeklyCap);
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            stmt.setInt(4, currencyID);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating currency failed, no rows affected.");
            }
            
            return new Currency(currencyID, currencyName, cap, weeklyCap);
        }
    }
    
    public static void delete(Connection cxn, int currencyID) throws SQLException {
        String query = "DELETE FROM Currency WHERE currencyID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setInt(1, currencyID);
            stmt.executeUpdate();
        }
    }
} 