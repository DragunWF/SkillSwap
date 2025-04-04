package com.example.dragun;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragun.adapters.RequestsAdapter;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.Utils;
import com.example.dragun.services.RequestService;

public class RequestSessionList extends AppCompatActivity {
    private ImageView backBtn;

    private RecyclerView requestsRecycler;
    private RequestsAdapter requestsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_session_list);
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
        backBtn = findViewById(R.id.backBtn);
        requestsRecycler = findViewById(R.id.requestsRecycler);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> finish());
    }

    private void setRecycler() {
        requestsRecycler.setHasFixedSize(false);

        requestsAdapter = new RequestsAdapter(RequestService.getRequests(), this);
        requestsRecycler.setAdapter(requestsAdapter);

        layoutManager = new LinearLayoutManager(this);
        requestsRecycler.setLayoutManager(layoutManager);
    }
}