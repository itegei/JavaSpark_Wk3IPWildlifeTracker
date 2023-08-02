package org.javasparkips.wildlifetracker;

import org.javasparkips.wildlifetracker.handlers.AnimalHandler;
import org.javasparkips.wildlifetracker.handlers.SightingHandler;
import org.javasparkips.wildlifetracker.handlers.RangerHandler;
import org.javasparkips.wildlifetracker.handlers.LocationHandler;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class WildlifeTrackerApp {
    // Add a public static method to retrieve the database connection
//    public static Connection getDBConnection() throws SQLException {
//        return DatabaseConnector.getConnection();
//   }
    public static void main(String[] args) {
        // Set the static files location
        staticFileLocation("/public");

        // Set up the Handlebars template engine
        HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine("/templates");

        // Route for the index page
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            // Get all animals, sightings, rangers, and locations from the database
            model.put("animal", AnimalHandler.getAllAnimals(req, res));
            model.put("sighting", SightingHandler.getAllSightings(req, res));
            model.put("ranger", RangerHandler.getAllRangers(req, res));
            model.put("location", LocationHandler.getAllLocations(req, res));

            // Log the template rendering
            System.out.println("Rendering template...");

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        // Set up routes for Animal
        get("/animal", (req, res) -> AnimalHandler.getAllAnimals(req, res));
        // Add a route to handle form submission for adding a new animal
        post("/animal", (req, res) -> AnimalHandler.addAnimal(req, res));

        // Set up routes for Sighting
        get("/sighting", (req, res) -> SightingHandler.getAllSightings(req, res));
        // Add a route to handle form submission for adding a new sighting
        post("/sighting", (req, res) -> SightingHandler.addSighting(req, res));

        // Set up routes for Ranger
        get("/ranger", (req, res) -> RangerHandler.getAllRangers(req, res));
        // Add a route to handle form submission for adding a new ranger
        post("/ranger", (req, res) -> RangerHandler.addRanger(req, res));

        // Set up routes for Location
        get("/location", (req, res) -> LocationHandler.getAllLocations(req, res));
        // Add a route to handle form submission for adding a new location
        post("/location", (req, res) -> LocationHandler.addLocation(req, res));

    }
}
