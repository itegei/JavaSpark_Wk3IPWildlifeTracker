package org.javasparkips.wildlifetracker.controllers;

import com.google.gson.Gson;
import org.javasparkips.wildlifetracker.dao.AnimalDao;
import org.javasparkips.wildlifetracker.models.Animal;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class AnimalController {
    private static Gson gson = new Gson();

    public static Object getAllAnimals(Request req, Response res) {
        try {
            List<Animal> animals = AnimalDao.getAllAnimals();
            return gson.toJson(animals);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object addAnimal(Request req, Response res) {
        try {
            Animal animal = gson.fromJson(req.body(), Animal.class);
            Animal addedAnimal = AnimalDao.addAnimal(animal);
            return gson.toJson(addedAnimal);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object updateAnimal(Request req, Response res) {
        try {
            Animal animal = gson.fromJson(req.body(), Animal.class);
            Animal updatedAnimal = AnimalDao.updateAnimal(animal);
            if (updatedAnimal == null) {
                res.status(404);
                return "Animal not found";
            }
            return gson.toJson(updatedAnimal);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object deleteAnimal(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params("id"));
            boolean deleted = AnimalDao.deleteAnimal(id);
            if (!deleted) {
                res.status(404);
                return "Animal not found";
            }
            return "Animal deleted successfully";
        } catch (NumberFormatException e) {
            res.status(400);
            return "Invalid animal ID";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }
}
