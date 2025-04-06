package com.example.dragun.services;

import com.example.dragun.data.User;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.ModelBank;

public class UserService {
    public static User get(int id) {
        return DatabaseHelper.getUsersBank().get(id);
    }

    public static void register(User user) {
        ModelBank<User> bank = DatabaseHelper.getUsersBank();
        bank.add(user);
    }

    // I have violated the DRY principle
    // Forgive me father for I have sinned...
    public static boolean isUsernameExists(String username) {
        for (User user : DatabaseHelper.getUsersBank().getAll()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmailExists(String email) {
        for (User user : DatabaseHelper.getUsersBank().getAll()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPhoneNumberExists(String phoneNumber) {
        for (User user : DatabaseHelper.getUsersBank().getAll()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
}
