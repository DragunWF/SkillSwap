package com.example.dragun;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dragun.data.Posting;
import com.example.dragun.data.Request;
import com.example.dragun.data.User;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.PostingService;
import com.example.dragun.services.RequestService;
import com.example.dragun.services.SessionService;
import com.example.dragun.services.UserService;

public class ViewListing extends AppCompatActivity {
    public static final String POSTING_ID = "postingId";

    private View view;
    private TextView mentorText, featuredSkillText, skillCategoryText,
                     estimatedLearningTimeText, contactOptionsText, skillLevelText;
    private ImageView backBtn, bookmarkIconBtn, profileIconBtn, skillImage;
    private Button requestBtn;

    private int currentPostingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_listing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);
            currentPostingId = getIntent().getIntExtra( POSTING_ID, 1);

            bindElements();
            setButtons();
            setDetails();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        mentorText = findViewById(R.id.mentorText);
        featuredSkillText = findViewById(R.id.featuredSkillText);
        skillCategoryText = findViewById(R.id.skillCategoryText);
        estimatedLearningTimeText = findViewById(R.id.estimatedLearningTimeText);
        contactOptionsText = findViewById(R.id.contactOptionText);
        skillLevelText = findViewById(R.id.skillLevelText);
        backBtn = findViewById(R.id.backBtn);
        bookmarkIconBtn = findViewById(R.id.bookmarkIconBtn);
        profileIconBtn = findViewById(R.id.profileIconBtn);
        skillImage = findViewById(R.id.skillImage);
        requestBtn = findViewById(R.id.requestBtn);

    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> finish());
        bookmarkIconBtn.setOnClickListener(v -> {
            boolean isAdded = PostingService.toggleBookmark(PostingService.get(currentPostingId));
            if (isAdded) {
                Utils.snackbar("Listing has been added to bookmarks!", view);
            } else {
                Utils.snackbar("Listing has been removed from your bookmarks list!", view);
            }
        });
        profileIconBtn.setOnClickListener(v -> {
            Posting posting = PostingService.get(currentPostingId);
            User mentor = UserService.get(posting.getUserId());

            StringBuilder message = new StringBuilder();
            message.append("Username: " + mentor.getUsername() + "\n");
            message.append("Email: " + mentor.getEmail() + "\n");
            message.append("Phone Number: " + mentor.getPhoneNumber() + "\n");
            message.append("Description: " + mentor.getDescription() + "\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(message.toString())
                    .setTitle("Mentor Profile");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        requestBtn.setOnClickListener(v -> {
            Posting posting = PostingService.get(currentPostingId);
            if (posting.getUserId() == SessionService.getCurrentUserId()) {
                Utils.snackbar("You cannot send a request to your own posting", view);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("In-Person", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sendRequest(posting, "In-Person");
                    Utils.snackbar("Request has been sent for an in-person session", view);
                }
            });
            builder.setNegativeButton("Virtual", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sendRequest(posting, "Virtual");
                    Utils.snackbar("Request has been sent for a virtual session", view);
                }
            });
            builder.setTitle("Confirmation Dialog");
            builder.setMessage("Are you sure you want to send a learning session request to this posting?");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void sendRequest(Posting posting, String sessionMode) {
        User currentUser = UserService.get(SessionService.getCurrentUserId());
        RequestService.add(new Request(
                posting.getUserId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                sessionMode,
                posting.getSkillCategory(),
                posting.getFeaturedSkill()
        ));
    }

    private void setDetails() {
        Posting posting = PostingService.get(currentPostingId);
        User mentor = UserService.get(posting.getUserId());
        skillImage.setImageResource(Utils.getImageResource(posting.getSkillCategory()));

        mentorText.setText(mentor.getUsername());
        featuredSkillText.setText(posting.getFeaturedSkill());
        skillCategoryText.setText(posting.getSkillCategory());
        estimatedLearningTimeText.setText(posting.getEstimatedLearningTime());
        contactOptionsText.setText(posting.getContactOptions());
        skillLevelText.setText(posting.getSkillLevel());
    }
}