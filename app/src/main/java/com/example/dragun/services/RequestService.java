package com.example.dragun.services;

import com.example.dragun.data.Request;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.ModelBank;

import java.util.ArrayList;
import java.util.List;

public class RequestService {
    public static List<Request> getRequests() {
        ModelBank<Request> bank = DatabaseHelper.getRequestBank();
        List<Request> results = new ArrayList<>();
        for (Request request : bank.getAll()) {
            if (request.getMentorId() == SessionService.getCurrentUserId()) {
                results.add(request);
            }
        }
        return results;
    }

    public static void add(Request request) {
        ModelBank<Request> bank = DatabaseHelper.getRequestBank();
        bank.add(request);
    }

    public static void delete(int id) {
        ModelBank<Request> bank = DatabaseHelper.getRequestBank();
        bank.delete(id);
    }

    public static boolean isRequestExist() {
        return true;
    }
}
