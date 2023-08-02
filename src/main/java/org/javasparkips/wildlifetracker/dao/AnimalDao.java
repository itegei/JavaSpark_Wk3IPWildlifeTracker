package org.javasparkips.wildlifetracker.dao;

import org.javasparkips.wildlifetracker.DatabaseConnector;
import org.javasparkips.wildlifetracker.models.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDao {
    public static Animal addAnimal(Animal animal) throws SQLException {
        String query = "INSERT INTO animals (name, health, age, type) VALUES (?, ?, ?, ?) RETURNING id;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, animal.getName());
            stmt.setString(2, animal.getHealth());
            stmt.setString(3, animal.getAge());
            stmt.setString(4, animal.getType());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                animal.setId(id);
                return animal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static Animal getAnimalById(int id) throws SQLException {
        String query = "SELECT * FROM animal WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String health = rs.getString("health");
                String age = rs.getString("age");
                String type = rs.getString("type");

                Animal animal = new Animal();
                animal.setId(id);
                animal.setName(name);
                animal.setHealth(health);
                animal.setAge(age);
                animal.setType(type);
                return animal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static List<Animal> getAllAnimals() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM animal;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String health = rs.getString("health");
                String age = rs.getString("age");
                String type = rs.getString("type");

                Animal animal = new Animal();
                animal.setId(id);
                animal.setName(name);
                animal.setHealth(health);
                animal.setAge(age);
                animal.setType(type);

                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return animals;
    }

    public static Animal updateAnimal(Animal animal) throws SQLException {
        String query = "UPDATE animal SET name = ?, health = ?, age = ?, type = ? WHERE id = ?;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, animal.getName());
            stmt.setString(2, animal.getHealth());
            stmt.setString(3, animal.getAge());
            stmt.setString(4, animal.getType());
            stmt.setInt(5, animal.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return animal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static boolean deleteAnimal(int id) throws SQLException {
        String query = "DELETE FROM animal WHERE id = ?;";
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
