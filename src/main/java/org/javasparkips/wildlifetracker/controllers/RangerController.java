package org.javasparkips.wildlifetracker.controllers;

import com.google.gson.Gson;
import org.javasparkips.wildlifetracker.dao.RangerDao;
import org.javasparkips.wildlifetracker.models.Ranger;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class RangerController {
    private static Gson gson = new Gson();

    public static Object getAllRangers(Request req, Response res) {
        try {
            List<Ranger> rangers = RangerDao.getAllRangers();
            return gson.toJson(rangers);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object addRanger(Request req, Response res) {
        try {
            Ranger ranger = gson.fromJson(req.body(), Ranger.class);
            Ranger addedRanger = RangerDao.addRanger(ranger);
            return gson.toJson(addedRanger);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }

    public static Object updateRanger(Request req, Response res) {
        try {
            Ranger ranger = gson.fromJson(req.body(), Ranger.class);
            Ranger updatedRanger = RangerDao.updateRanger(ranger);
            if (updatedRanger == null) {
                res.status(404);
                return "Ranger not found";
            }
            return gson.toJson(updatedRanger);
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "Internal Server Error";
        }
    }
}


