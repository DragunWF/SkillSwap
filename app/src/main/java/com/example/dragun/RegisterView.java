package com.example.dragun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dragun.data.User;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterView extends AppCompatActivity {
    private View view;
    private TextInputEditText usernameText, passwordText, confirmPasswordText, emailText, phoneNumberText, descriptionText;
    private Button confirmBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);

            bindElements();
            setButtons();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        confirmBtn = findViewById(R.id.confirmBtn);
        backBtn = findViewById(R.id.backBtn);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        emailText = findViewById(R.id.emailText);
        phoneNumberText = findViewById(R.id.phoneNumberText);
        descriptionText = findViewById(R.id.descriptionText);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        confirmBtn.setOnClickListener(v -> {
            String username = Utils.getText(usernameText);
            String password = Utils.getText(passwordText);
            String confirmPassword = Utils.getText(confirmPasswordText);
            String email = Utils.getText(emailText);
            String phoneNumber = Utils.getText(phoneNumberText);
            String description = Utils.getText(descriptionText);

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || description.isEmpty() || phoneNumber.isEmpty()) {
                Utils.snackbar("All fields are required!", view);
                return;
            }
            if (!password.equals(confirmPassword)) {
                Utils.snackbar("Password and confirm password fields do not match!", view);
                return;
            }
            if (UserService.isUsernameExists(username)) {
                Utils.snackbar("Username already exists!", view);
                return;
            }
            if (!Utils.isValidEmail(email)) {
                Utils.snackbar("Email format is invalid!", view);
                return;
            }
            if (UserService.isEmailExists(email)) {
                Utils.snackbar("Email is already being used!", view);
                return;
            }
            if (!Utils.isValidPhoneNumber(phoneNumber)) {
                Utils.snackbar("Phone number format is invalid!", view);
                return;
            }
            if (UserService.isPhoneNumberExists(phoneNumber)) {
                Utils.snackbar("Phone number is already being used!", view);
                return;
            }

            UserService.register(new User(username, password, email, phoneNumber, description));
            startActivity(new Intent(this, LoginView.class));
        });
    }
}