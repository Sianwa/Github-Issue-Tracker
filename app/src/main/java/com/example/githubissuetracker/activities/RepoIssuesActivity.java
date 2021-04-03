package com.example.githubissuetracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.githubissuetracker.R;
import com.example.githubissuetracker.adapters.RepoIssuesAdapter;
import com.example.githubissuetracker.fragments.dialogs.BottomSheetDialog;
import com.example.githubissuetracker.network.ApolloConnector;
import com.example.mygraphql.GetRepoIssuesQuery;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class RepoIssuesActivity extends AppCompatActivity {
    RepoIssuesAdapter repoIssuesAdapter;
    final String repo_name = "Github-Issue-Tracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_issues);

        //components
        RecyclerView recyclerView = findViewById(R.id.MyrepoIssues);
        TextInputLayout searchText = findViewById(R.id.search_bar);

        //open filter bottom sheet
        searchText.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
        });

        //get repository issues
        ApolloConnector.setupClient().query(new GetRepoIssuesQuery(repo_name))
                .enqueue(new ApolloCall.Callback<GetRepoIssuesQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetRepoIssuesQuery.Data> response) {
                Log.e("Apollo", "Data " + response.getData().viewer().repository().issues().edges());

                //update ui on UI thread
                RepoIssuesActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        repoIssuesAdapter = new RepoIssuesAdapter(response.getData().viewer().repository().issues().edges(),getBaseContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        recyclerView.setAdapter(repoIssuesAdapter);
                        repoIssuesAdapter.notifyDataSetChanged();

                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e("Apollo", "Error", e);

            }
        });
    }
}