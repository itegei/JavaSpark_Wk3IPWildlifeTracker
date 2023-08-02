package org.javasparkips.wildlifetracker.controllers;

import com.google.gson.Gson;
import org.javasparkips.wildlifetracker.dao.SightingDao;
import org.javasparkips.wildlifetracker.models.Sighting;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class SightingController {
    private static Gson gson = new Gson();

    public static Object getAllSightings(Request req, Response res) {
        try {
            List<Sighting> sightings = SightingDao.getAllSightings();
            return gson.toJson(sightings);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object addSighting(Request req, Response res) {
        try {
            Sighting sighting = gson.fromJson(req.body(), Sighting.class);
            Sighting addedSighting = SightingDao.addSighting(sighting);
            return gson.toJson(addedSighting);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    } 

    public static Object updateSighting(Request req, Response res) {
        try {
            Sighting sighting = gson.fromJson(req.body(), Sighting.class);
            Sighting updatedSighting = SightingDao.updateSighting(sighting);
            if (updatedSighting == null) {
                res.status(404);
                return "Sighting not found";
            }
            return gson.toJson(updatedSighting);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object deleteSighting(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params("id"));
            boolean deleted = SightingDao.deleteSighting(id);
            if (!deleted) {
                res.status(404);
                return "Sighting not found";
            }
            return "Sighting deleted successfully";
        } catch (NumberFormatException e) {
            res.status(400);
            return "Invalid sighting ID";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }
}
