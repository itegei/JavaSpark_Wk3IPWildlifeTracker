package org.javasparkips.wildlifetracker.handlers;

import org.javasparkips.wildlifetracker.dao.RangerDao;
import org.javasparkips.wildlifetracker.models.Ranger;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class RangerHandler {
    public static Object getAllRangers(Request req, Response res) {
        try {
            List<Ranger> rangers = RangerDao.getAllRangers();
            res.type("application/json");
            return rangers;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching rangers.";
        }
    }

    public static Object addRanger(Request req, Response res) {
        try {
            Ranger newRanger = new Ranger();
            newRanger.setRangerName(req.queryParams("rangerName"));
            newRanger.setBadgeNumber(req.queryParams("badgeNumber"));

            Ranger addedRanger = RangerDao.addRanger(newRanger);
            res.status(201); // Created
            res.type("application/json");
            return addedRanger;
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while adding ranger.";
        }
    }

    public static Object getRangerById(Request req, Response res) {
        try {
            int rangerId = Integer.parseInt(req.params(":id"));
            Ranger ranger = RangerDao.getRangerById(rangerId);
            if (ranger != null) {
                res.type("application/json");
                return ranger;
            } else {
                res.status(404); // Not Found
                return "Ranger with ID " + rangerId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid ranger ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while fetching ranger.";
        }
    }

    public static Object updateRanger(Request req, Response res) {
        try {
            int rangerId = Integer.parseInt(req.params(":id"));
            Ranger existingRanger = RangerDao.getRangerById(rangerId);
            if (existingRanger != null) {
                // Update the ranger with the new data
                existingRanger.setRangerName(req.queryParams("rangerName"));
                existingRanger.setBadgeNumber(req.queryParams("badgeNumber"));

                Ranger updatedRanger = RangerDao.updateRanger(existingRanger);
                res.type("application/json");
                return updatedRanger;
            } else {
                res.status(404); // Not Found
                return "Ranger with ID " + rangerId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid ranger ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while updating ranger.";
        }
    }

    public static Object deleteRanger(Request req, Response res) {
        try {
            int rangerId = Integer.parseInt(req.params(":id"));
            boolean deleted = RangerDao.deleteRanger(rangerId);
            if (deleted) {
                res.status(200); // OK
                return "Ranger with ID " + rangerId + " deleted successfully.";
            } else {
                res.status(404); // Not Found
                return "Ranger with ID " + rangerId + " not found.";
            }
        } catch (NumberFormatException e) {
            res.status(400); // Bad Request
            return "Invalid ranger ID.";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500); // Internal Server Error
            return "Error occurred while deleting ranger.";
        }
    }
}
