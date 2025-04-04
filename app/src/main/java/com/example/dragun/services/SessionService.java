package com.example.dragun.services;

import com.example.dragun.data.User;
import com.example.dragun.helpers.DatabaseHelper;

public class SessionService {
    private static int currentUserId = 0;

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static boolean login(String username, String password) {
        for (User user : DatabaseHelper.getUsersBank().getAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUserId = user.getId();
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        currentUserId = 0;
    }
}
