package cs5200project.dal;

import cs5200project.model.CharacterCurrency;
import java.sql.*;
import java.util.*;

public class CharacterCurrencyDao {
    protected Connection connection;

    public CharacterCurrencyDao(Connection connection) {
        this.connection = connection;
    }

    public List<CharacterCurrency> getCurrenciesByCharacterId(int characterID) throws SQLException {
        List<CharacterCurrency> currencies = new ArrayList<>();
        String query = "SELECT * FROM Character_Currency WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
