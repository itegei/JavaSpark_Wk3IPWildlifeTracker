package org.javasparkips.wildlifetracker.handlers;

import org.javasparkips.wildlifetracker.dao.LocationDao;
import org.javasparkips.wildlifetracker.models.Location;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class LocationHandler {
    public static Object getAllLocations(Request req, Response res) {
        try {
            List<Location> locations = LocationDao.getAllLocations();
            res.type("application/json");
            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching locations.";
        }
    }

    public static Object addLocation(Request req, Response res) {
        try {
            Location newLocation = new Location();
            newLocation.setName(req.queryParams("name"));

            Location addedLocation = LocationDao.addLocation(newLocation);
            res.status(201); // Created
            res.type("application/json");
            return addedLocation;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while adding location.";
        }
    }

    public static Object getLocationById(Request req, Response res) {
        try {
            int locationId = Integer.parseInt(req.params(":id"));
            Location location = LocationDao.getLocationById(locationId);
            if (location != null) {
                res.type("application/json");
                return location;
            } else {
                res.status(404); // Not Found
                return "Location with ID " + locationId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid location ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching location.";
        }
    }

    public static Object updateLocation(Request req, Response res) {
        try {
            int locationId = Integer.parseInt(req.params(":id"));
            Location existingLocation = LocationDao.getLocationById(locationId);
            if (existingLocation != null) {
                // Update the location with the new data
                existingLocation.setName(req.queryParams("name"));

                Location updatedLocation = LocationDao.updateLocation(existingLocation);
                res.type("application/json");
                return updatedLocation;
            } else {
                res.status(404); // Not Found
                return "Location with ID " + locationId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid location ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while updating location.";
        }
    }

    public static Object deleteLocation(Request req, Response res) {
        try {
            int locationId = Integer.parseInt(req.params(":id"));
            boolean deleted = LocationDao.deleteLocation(locationId);
            if (deleted) {
                res.status(200); // OK
                return "Location with ID " + locationId + " deleted successfully.";
            } else {
                res.status(404); // Not Found
                return "Location with ID " + locationId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid location ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while deleting location.";
        }
    }
}
