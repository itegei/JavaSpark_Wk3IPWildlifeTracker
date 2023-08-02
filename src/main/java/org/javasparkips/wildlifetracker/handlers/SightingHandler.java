package org.javasparkips.wildlifetracker.handlers;

import org.javasparkips.wildlifetracker.dao.SightingDao;
import org.javasparkips.wildlifetracker.models.Sighting;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class SightingHandler {
    public static Object getAllSightings(Request req, Response res) {
        try {
            List<Sighting> sightings = SightingDao.getAllSightings();
            res.type("application/json");
            return sightings;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching sightings.";
        }
    }

    public static Object addSighting(Request req, Response res) {
        try {
            Sighting newSighting = new Sighting();
            newSighting.setLocationId(Integer.parseInt(req.queryParams("locationId")));
            newSighting.setRangerId(Integer.parseInt(req.queryParams("rangerId")));
            newSighting.setAnimalId(Integer.parseInt(req.queryParams("animalId")));

            Sighting addedSighting = SightingDao.addSighting(newSighting);
            res.status(201); // Created
            res.type("application/json");
            return addedSighting;
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid input for locationId, rangerId, or animalId.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while adding sighting.";
        }
    }

    public static Object getSightingById(Request req, Response res) {
        try {
            int sightingId = Integer.parseInt(req.params(":id"));
            Sighting sighting = SightingDao.getSightingById(sightingId);
            if (sighting != null) {
                res.type("application/json");
                return sighting;
            } else {
                res.status(404); // Not Found
                return "Sighting with ID " + sightingId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid sighting ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching sighting.";
        }
    }

    public static Object updateSighting(Request req, Response res) {
        try {
            int sightingId = Integer.parseInt(req.params(":id"));
            Sighting existingSighting = SightingDao.getSightingById(sightingId);
            if (existingSighting != null) {
                // Update the sighting with the new data
                existingSighting.setLocationId(Integer.parseInt(req.queryParams("locationId")));
                existingSighting.setRangerId(Integer.parseInt(req.queryParams("rangerId")));
                existingSighting.setAnimalId(Integer.parseInt(req.queryParams("animalId")));

                Sighting updatedSighting = SightingDao.updateSighting(existingSighting);
                res.type("application/json");
                return updatedSighting;
            } else {
                res.status(404); // Not Found
                return "Sighting with ID " + sightingId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid sighting ID or input for locationId, rangerId, or animalId.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while updating sighting.";
        }
    }

    public static Object deleteSighting(Request req, Response res) {
        try {
            int sightingId = Integer.parseInt(req.params(":id"));
            boolean deleted = SightingDao.deleteSighting(sightingId);
            if (deleted) {
                res.status(200); // OK
                return "Sighting with ID " + sightingId + " deleted successfully.";
            } else {
                res.status(404); // Not Found
                return "Sighting with ID " + sightingId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid sighting ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while deleting sighting.";
        }
    }
}
