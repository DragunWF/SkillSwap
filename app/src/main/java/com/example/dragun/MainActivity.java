package com.example.dragun;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragun.adapters.PostingAdapter;
import com.example.dragun.data.Posting;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.SessionService;
import com.example.dragun.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private View view;
    private SearchView searchBar;
    private Button addListingBtn;
    private Spinner categorySpinner;

    private RecyclerView postingsRecycler;
    private PostingAdapter postingAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView bookmarkIconBtn, profileIconBtn;

    @Override
    protected void onResume() {
        super.onResume();
        postingAdapter.updateDataSet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);

            bindElements();
            setButtons();
            setRecycler();
            setSearch();
            setSpinners();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        addListingBtn = findViewById(R.id.addListingBtn);
        searchBar = findViewById(R.id.searchBar);
        categorySpinner = findViewById(R.id.categorySpinner);
        postingsRecycler = findViewById(R.id.postingsRecycler);
        bookmarkIconBtn = findViewById(R.id.bookmarkIconBtn);
        profileIconBtn = findViewById(R.id.profileIconBtn);
    }

    private void setButtons() {
        addListingBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, AddListing.class));
        });
        bookmarkIconBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, BookingView.class));
        });
        profileIconBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileView.class));
        });
    }

    private void setRecycler() {
        postingsRecycler.setHasFixedSize(false);

        postingAdapter = new PostingAdapter(DatabaseHelper.getPostingBank().getAll(), this);
        postingsRecycler.setAdapter(postingAdapter);

        layoutManager = new LinearLayoutManager(this);
        postingsRecycler.setLayoutManager(layoutManager);
    }

    private void setSearch() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                update(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                update(newText);
                return false;
            }

            public void update(String query) {
                List<Posting> postings = DatabaseHelper.getPostingBank().getAll();
                List<Posting> results = new ArrayList<>();

                query = query.toLowerCase();
                for (Posting posting : postings) {
                    String skill = posting.getFeaturedSkill().toLowerCase();
                    if (skill.contains(query)) {
                        results.add(posting);
                    }
                }

                postingAdapter.updateDataSet(results);
            }
        });
    }

    private void setSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.skill_categories_main,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = adapter.getItem(position).toString();
                List<Posting> results = new ArrayList<>();

                if (selectedCategory.equals("Any")) {
                    postingAdapter.updateDataSet();
                    return;
                }

                for (Posting posting : DatabaseHelper.getPostingBank().getAll()) {
                    if (posting.getSkillCategory().equals(selectedCategory)) {
                        results.add(posting);
                    }
                }

                postingAdapter.updateDataSet(results);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}