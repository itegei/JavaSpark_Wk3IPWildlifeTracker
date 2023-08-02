package org.javasparkips.wildlifetracker.dao;

import org.javasparkips.wildlifetracker.DatabaseConnector;
import org.javasparkips.wildlifetracker.models.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDao {
    public static Location addLocation(Location location) throws SQLException {
        String query = "INSERT INTO location (name) VALUES (?) RETURNING id;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, location.getName());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                location.setId(id);
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static Location getLocationById(int id) throws SQLException {
        String query = "SELECT * FROM location WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");

                Location location = new Location();
                location.setId(id);
                location.setName(name);
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static List<Location> getAllLocations() throws SQLException {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM location;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Location location = new Location();
                location.setId(id);
                location.setName(name);

                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return locations;
    }

    public static Location updateLocation(Location location) throws SQLException {
        String query = "UPDATE location SET name = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, location.getName());
            stmt.setInt(2, location.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return location;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static boolean deleteLocation(int id) throws SQLException {
        String query = "DELETE FROM location WHERE id = ?;";
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
