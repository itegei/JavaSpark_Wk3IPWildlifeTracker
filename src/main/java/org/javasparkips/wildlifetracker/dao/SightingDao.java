package org.javasparkips.wildlifetracker.dao;

import org.javasparkips.wildlifetracker.DatabaseConnector;
import org.javasparkips.wildlifetracker.models.Sighting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SightingDao {
    public static Sighting addSighting(Sighting sighting) throws SQLException {
        String query = "INSERT INTO sighting (location_id, ranger_id, animal_id) VALUES (?, ?, ?) RETURNING id;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sighting.getLocationId());
            stmt.setInt(2, sighting.getRangerId());
            stmt.setInt(3, sighting.getAnimalId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                sighting.setId(id);
                return sighting;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static Sighting getSightingById(int id) throws SQLException {
        String query = "SELECT * FROM sighting WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int locationId = rs.getInt("location_id");
                int rangerId = rs.getInt("ranger_id");
                int animalId = rs.getInt("animal_id");

                Sighting sighting = new Sighting();
                sighting.setId(id);
                sighting.setLocationId(locationId);
                sighting.setRangerId(rangerId);
                sighting.setAnimalId(animalId);
                return sighting;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static List<Sighting> getAllSightings() throws SQLException {
        List<Sighting> sightings = new ArrayList<>();
        String query = "SELECT * FROM sighting;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int locationId = rs.getInt("location_id");
                int rangerId = rs.getInt("ranger_id");
                int animalId = rs.getInt("animal_id");

                Sighting sighting = new Sighting();
                sighting.setId(id);
                sighting.setLocationId(locationId);
                sighting.setRangerId(rangerId);
                sighting.setAnimalId(animalId);

                sightings.add(sighting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return sightings;
    }

    public static Sighting updateSighting(Sighting sighting) throws SQLException {
        String query = "UPDATE sighting SET location_id = ?, ranger_id = ?, animal_id = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sighting.getLocationId());
            stmt.setInt(2, sighting.getRangerId());
            stmt.setInt(3, sighting.getAnimalId());
            stmt.setInt(4, sighting.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return sighting;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static boolean deleteSighting(int id) throws SQLException {
        String query = "DELETE FROM sighting WHERE id = ?;";
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
