package com.example.githubissuetracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.githubissuetracker.R;
import com.example.githubissuetracker.adapters.SingleIssueCommentsAdapter;

public class SingleIssueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_issue);

        //componets
        RecyclerView commentsRecyclerview = findViewById(R.id.issues_comments);
        commentsRecyclerview.setHasFixedSize(true);
        commentsRecyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        commentsRecyclerview.setAdapter(new SingleIssueCommentsAdapter());
    }
}