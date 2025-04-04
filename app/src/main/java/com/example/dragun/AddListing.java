package com.example.dragun;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragun.data.Posting;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.PostingService;
import com.example.dragun.services.SessionService;
import com.google.android.material.textfield.TextInputEditText;

public class AddListing extends AppCompatActivity {
    private View view;
    private TextInputEditText descriptionText, timeText, contactOptionsText;
    private Button confirmBtn;

    private ArrayAdapter<CharSequence> featuredSkillAdapter, skillCategoryAdapter, timeAdapter, skillLevelAdapter;
    private Spinner featuredSkillSpinner, skillCategorySpinner, timeSpinner, skillLevelSpinner;

    private ImageView backBtn;

    private String featuredSkill, skillCategory, timeType, skillLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_listing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);

            bindElements();
            setButtons();
            setSpinners();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        contactOptionsText = findViewById(R.id.contactOptionText);
        descriptionText = findViewById(R.id.descriptionText);
        backBtn = findViewById(R.id.backBtn);
        timeText = findViewById(R.id.timeText);
        confirmBtn = findViewById(R.id.confirmBtn);
        featuredSkillSpinner = findViewById(R.id.featuredSkillSpinner);
        skillCategorySpinner = findViewById(R.id.skillCategorySpinner);
        timeSpinner = findViewById(R.id.timeSpinner);
        skillLevelSpinner = findViewById(R.id.skillLevelSpinner);
    }

    private void setButtons() {
        confirmBtn.setOnClickListener(v -> {
            String description = Utils.getText(descriptionText);
            String contactOptions = Utils.getText(contactOptionsText);
            String timeStr = Utils.getText(timeText);

            if (description.isEmpty() || timeStr.isEmpty() || contactOptions.isEmpty()) {
                Utils.snackbar("All fields are required!", view);
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    int time = Integer.parseInt(timeStr);
                    String learningTime = String.format("%s %s", time, timeType);

                    PostingService.add(new Posting(SessionService.getCurrentUserId(), featuredSkill, skillCategory, description, learningTime, contactOptions, skillLevel));
                    Utils.longToast("Your posting has been created!", AddListing.this);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setTitle("Confirmation Dialog");
            builder.setMessage("Are you sure you want to add this listing? Make sure to check all details before confirming the creation of your post.");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        backBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancels the dialog.
                }
            });
            builder.setTitle("Confirmation Dialog");
            builder.setMessage("Are you sure you want to go back? This will delete the information that you've provided");

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void setSpinners() {
        timeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.learning_time,
                android.R.layout.simple_spinner_item
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeType = timeAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        skillLevelAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.skill_level,
                android.R.layout.simple_spinner_item
        );
        skillLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillLevelSpinner.setAdapter(skillLevelAdapter);

        skillLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skillLevel = skillLevelAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        skillCategoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.skill_categories,
                android.R.layout.simple_spinner_item
        );
        skillCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillCategorySpinner.setAdapter(skillCategoryAdapter);

        skillCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skillCategory = skillCategoryAdapter.getItem(position).toString();

                int skillId = R.array.tech_skill;
                switch (skillCategory) {
                    case "Technology and Coding":
                        skillId = R.array.tech_skill;
                        break;
                    case "Arts and Crafts":
                        skillId = R.array.arts_skill;
                        break;
                    case "Fitness and Wellness":
                        skillId = R.array.fitness_skill;
                        break;
                    case "Language and Communication":
                        skillId = R.array.language_skill;
                        break;
                    case "Business and Finance":
                        skillId = R.array.business_skill;
                        break;
                }

                featuredSkillAdapter= ArrayAdapter.createFromResource(
                        AddListing.this,
                        skillId,
                        android.R.layout.simple_spinner_item
                );
                featuredSkillSpinner.setAdapter(featuredSkillAdapter);
                featuredSkillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        featuredSkillAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tech_skill,
                android.R.layout.simple_spinner_item
        );
        featuredSkillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        featuredSkillSpinner.setAdapter(featuredSkillAdapter);

        featuredSkillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                featuredSkill = featuredSkillAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}