package com.example.githubissuetracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class RepoIssuesActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RepoIssuesAdapter repoIssuesAdapter;
    String repo_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_issues);

        //get data from previous fragment
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        repo_name = bundle.getString("repo_name");
        Log.e("Apollo", "Repository: " + repo_name);

        //components
        recyclerView = findViewById(R.id.MyrepoIssues);
        TextInputLayout searchText = findViewById(R.id.search_edit_frame);
        TextInputEditText searchEditText = findViewById(R.id.search_editText);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        //refresh page on new changes
        swipeRefreshLayout.setOnRefreshListener(this::fetchRefresh);

        //open filter bottom sheet
        searchText.setEndIconOnClickListener(v -> {
            BottomSheetDialog bottomSheet = new BottomSheetDialog();
            bottomSheet.show(getSupportFragmentManager(),
                    "ModalBottomSheet");
        });
        //search
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());

            }
        });

        //get repository issues
        ApolloConnector.setupClient().query(new GetRepoIssuesQuery(repo_name))
                .enqueue(new ApolloCall.Callback<GetRepoIssuesQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetRepoIssuesQuery.Data> response) {
                Log.e("Apollo", "Data received" );

                //update ui on UI thread
                RepoIssuesActivity.this.runOnUiThread(() -> {
                    repoIssuesAdapter = new RepoIssuesAdapter(response.getData().viewer().repository().issues().edges(),getBaseContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    recyclerView.setAdapter(repoIssuesAdapter);

                    swipeRefreshLayout.setRefreshing(false);

                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e("Apollo", "Error", e);

            }
        });
    }

    private void filterList(String filterText) {
        repoIssuesAdapter.getFilter().filter(filterText);
    }

    private void fetchRefresh() {
        //get repository issues
        ApolloConnector.setupClient().query(new GetRepoIssuesQuery(repo_name))
                .enqueue(new ApolloCall.Callback<GetRepoIssuesQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetRepoIssuesQuery.Data> response) {
                        Log.e("Apollo", "Data received");

                        //update ui on UI thread
                        RepoIssuesActivity.this.runOnUiThread(() -> {

                            repoIssuesAdapter = new RepoIssuesAdapter(response.getData().viewer().repository().issues().edges(),getBaseContext());
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView.setAdapter(repoIssuesAdapter);

                            swipeRefreshLayout.setRefreshing(false);

                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);

                    }
                });
    }

    @Override
    public void onButtonClick(String filterText) {
        Toast.makeText(this,"My filter : " + filterText, Toast.LENGTH_LONG).show();
        repoIssuesAdapter.ft(filterText);
    }


}