package org.javasparkips.wildlifetracker.controllers;

import com.google.gson.Gson;
import org.javasparkips.wildlifetracker.dao.LocationDao;
import org.javasparkips.wildlifetracker.models.Location;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class LocationController {
    private static Gson gson = new Gson();

    public static Object getAllLocations(Request req, Response res) {
        try {
            List<Location> locations = LocationDao.getAllLocations();
            return gson.toJson(locations);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object addLocation(Request req, Response res) {
        try {
            Location location = gson.fromJson(req.body(), Location.class);
            Location addedLocation = LocationDao.addLocation(location);
            return gson.toJson(addedLocation);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object updateLocation(Request req, Response res) {
        try {
            Location location = gson.fromJson(req.body(), Location.class);
            Location updatedLocation = LocationDao.updateLocation(location);
            if (updatedLocation == null) {
                res.status(404);
                return "Location not found";
            }
            return gson.toJson(updatedLocation);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object deleteLocation(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params("id"));
            boolean deleted = LocationDao.deleteLocation(id);
            if (!deleted) {
                res.status(404);
                return "Location not found";
            }
            return "Location deleted successfully";
        } catch (NumberFormatException e) {
            res.status(400);
            return "Invalid location ID";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }
}
