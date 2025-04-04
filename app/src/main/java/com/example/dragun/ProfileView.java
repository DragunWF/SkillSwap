package com.example.dragun;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dragun.data.User;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.SessionService;
import com.example.dragun.services.UserService;

public class ProfileView extends AppCompatActivity {
    private TextView usernameText, descriptionText;
    private Button listingBtn, requestsBtn, logoutBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);

            bindElements();
            setButtons();
            setDetails();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        listingBtn = findViewById(R.id.listingBtn);
        requestsBtn = findViewById(R.id.requestsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        backBtn = findViewById(R.id.backBtn);
        usernameText = findViewById(R.id.usernameText);
        descriptionText = findViewById(R.id.descriptionText);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        logoutBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SessionService.logout();
                    Utils.longToast("You have been logged out!", ProfileView.this);
                    startActivity(new Intent(ProfileView.this, SplashScreen.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setTitle("Confirmation Dialog");
            builder.setMessage("Are you sure you want to log out?");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        listingBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MyListingView.class));
        });
        requestsBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, RequestSessionList.class));
        });
    }

    private void setDetails() {
        User user = UserService.get(SessionService.getCurrentUserId());

        usernameText.setText(user.getUsername());
        descriptionText.setText(user.getDescription());
    }
}