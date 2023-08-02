package org.javasparkips.wildlifetracker.handlers;

import org.javasparkips.wildlifetracker.dao.AnimalDao;
import org.javasparkips.wildlifetracker.models.Animal;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class AnimalHandler {
    public static Object getAllAnimals(Request req, Response res) {
        try {
            List<Animal> animals = AnimalDao.getAllAnimals();
            res.type("application/json");
            return animals;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching animals.";
        }
    }

    public static Object addAnimal(Request req, Response res) {
        try {
            Animal newAnimal = new Animal();
            newAnimal.setName(req.queryParams("name"));
            newAnimal.setHealth(req.queryParams("health"));
            newAnimal.setAge(req.queryParams("age"));
            newAnimal.setType(req.queryParams("type"));

            Animal addedAnimal = AnimalDao.addAnimal(newAnimal);
            res.status(201); // Created
            res.type("application/json");
            return addedAnimal;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while adding animal.";
        }
    }

    public static Object getAnimalById(Request req, Response res) {
        try {
            int animalId = Integer.parseInt(req.params(":id"));
            Animal animal = AnimalDao.getAnimalById(animalId);
            if (animal != null) {
                res.type("application/json");
                return animal;
            } else {
                res.status(404); // Not Found
                return "Animal with ID " + animalId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid animal ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching animal.";
        }
    }

    public static Object updateAnimal(Request req, Response res) {
        try {
            int animalId = Integer.parseInt(req.params(":id"));
            Animal existingAnimal = AnimalDao.getAnimalById(animalId);
            if (existingAnimal != null) {
                // Update the animal with the new data
                existingAnimal.setName(req.queryParams("name"));
                existingAnimal.setHealth(req.queryParams("health"));
                existingAnimal.setAge(req.queryParams("age"));
                existingAnimal.setType(req.queryParams("type"));

                Animal updatedAnimal = AnimalDao.updateAnimal(existingAnimal);
                res.type("application/json");
                return updatedAnimal;
            } else {
                res.status(404); // Not Found
                return "Animal with ID " + animalId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid animal ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while updating animal.";
        }
    }

    public static Object deleteAnimal(Request req, Response res) {
        try {
            int animalId = Integer.parseInt(req.params(":id"));
            boolean deleted = AnimalDao.deleteAnimal(animalId);
            if (deleted) {
                res.status(200); // OK
                return "Animal with ID " + animalId + " deleted successfully.";
            } else {
                res.status(404); // Not Found
                return "Animal with ID " + animalId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid animal ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while deleting animal.";
        }
    }
}
