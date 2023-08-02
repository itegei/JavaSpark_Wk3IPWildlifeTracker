package org.javasparkips.wildlifetracker.dao;

import org.javasparkips.wildlifetracker.DatabaseConnector;
import org.javasparkips.wildlifetracker.models.Ranger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RangerDao {
    public static Ranger addRanger(Ranger ranger) throws SQLException {
        String query = "INSERT INTO ranger (ranger_name, badge_number) VALUES (?, ?) RETURNING id;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ranger.getRangerName());
            stmt.setString(2, ranger.getBadgeNumber());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                ranger.setId(id);
                return ranger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static Ranger getRangerById(int id) throws SQLException {
        String query = "SELECT * FROM ranger WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String rangerName = rs.getString("ranger_name");
                String badgeNumber = rs.getString("badge_number");

                Ranger ranger = new Ranger();
                ranger.setId(id);
                ranger.setRangerName(rangerName);
                ranger.setBadgeNumber(badgeNumber);
                return ranger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static List<Ranger> getAllRangers() throws SQLException {
        List<Ranger> rangers = new ArrayList<>();
        String query = "SELECT * FROM ranger;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String rangerName = rs.getString("ranger_name");
                String badgeNumber = rs.getString("badge_number");

                Ranger ranger = new Ranger();
                ranger.setId(id);
                ranger.setRangerName(rangerName);
                ranger.setBadgeNumber(badgeNumber);

                rangers.add(ranger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rangers;
    }

    public static Ranger updateRanger(Ranger ranger) throws SQLException {
        String query = "UPDATE ranger SET ranger_name = ?, badge_number = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ranger.getRangerName());
            stmt.setString(2, ranger.getBadgeNumber());
            stmt.setInt(3, ranger.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return ranger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static boolean deleteRanger(int id) throws SQLException {
        String query = "DELETE FROM ranger WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
