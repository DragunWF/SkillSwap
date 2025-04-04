package com.example.dragun.helpers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dragun.R;
import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public static void longToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void snackbar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static String getText(EditText text) {
        return String.valueOf(text.getText());
    }

    public static boolean isValidEmail(String message) {
        return message.contains("@");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() < 10) {
            return false;
        }
        return true;
    }

    public static int getImageResource(String skillCategory) {
        switch (skillCategory) {
            case "Arts and Crafts":
                return R.drawable.arts_crafts;
            case "Fitness and Wellness":
                return R.drawable.fitness_wellness;
            case "Language and Communication":
                return R.drawable.language_comm;
            case "Business and Finance":
                return R.drawable.business_finance;
            case "Technology and Coding":
                return R.drawable.tech_coding;
            default:
                return R.drawable.calligraphy;
        }
    }
}
