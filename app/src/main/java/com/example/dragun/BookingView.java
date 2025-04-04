package com.example.dragun;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragun.adapters.PostingAdapter;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.PostingService;

public class BookingView extends AppCompatActivity {
    private View view;
    private ImageView backBtn;

    private RecyclerView bookmarkRecycler;
    private PostingAdapter postingAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        postingAdapter.updateDataSet(PostingService.getBookmarks());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_view);
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
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        backBtn = findViewById(R.id.backBtn);
        bookmarkRecycler = findViewById(R.id.bookmarkRecycler);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void setRecycler() {
        bookmarkRecycler.setHasFixedSize(false);

        postingAdapter = new PostingAdapter(PostingService.getBookmarks(), this);
        bookmarkRecycler.setAdapter(postingAdapter);

        layoutManager = new LinearLayoutManager(this);
        bookmarkRecycler.setLayoutManager(layoutManager);
    }
}