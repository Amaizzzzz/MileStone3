package cs5200project.dal;

import cs5200project.model.CharacterCurrency;
import java.sql.*;
import java.util.*;

public class CharacterCurrencyDao {
    // Dao classes should not be instantiated.
    // Pass Connection object as parameter in each method
    // Each method should be static
    private CharacterCurrencyDao() {
        // Private constructor to prevent instantiation
    }

    public static CharacterCurrency create(Connection cxn, Character character, Currency currency, int currentAmount, boolean isCurrent) throws SQLException {
        final String insertCharacterCurrency = """
            INSERT INTO Character_Currency(characterID, currencyID, currentAmount, isCurrent)
            VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement insertStmt = cxn.prepareStatement(insertCharacterCurrency)) {
            insertStmt.setInt(1, character.getCharacterID());
            insertStmt.setInt(2, currency.getCurrencyID());
            insertStmt.setInt(3, currentAmount);
            insertStmt.setBoolean(4, isCurrent);
            insertStmt.executeUpdate();

            return new CharacterCurrency(character, currency, currentAmount, isCurrent);
        }
    }

    public static List<CharacterCurrency> getCurrenciesByCharacterId(Connection cxn, int characterID) throws SQLException {
        List<CharacterCurrency> currencies = new ArrayList<>();
        String query = "SELECT * FROM Character_Currency WHERE characterID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setInt(1, characterID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    currencies.add(new CharacterCurrency(
                        rs.getInt("characterID"),
                        rs.getInt("currencyID"),
                        rs.getInt("currentAmount"),
                        rs.getBoolean("isCurrent")
                    ));
                }
            }
        }
        return currencies;
    }
}
