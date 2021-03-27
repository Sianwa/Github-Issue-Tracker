package com.example.githubissuetracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.githubissuetracker.R;
import com.example.githubissuetracker.adapters.RepoIssuesAdapter;

public class RepoIssuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_issues);

        //components
        RecyclerView recyclerView = findViewById(R.id.MyrepoIssues);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(new RepoIssuesAdapter());

    }
}