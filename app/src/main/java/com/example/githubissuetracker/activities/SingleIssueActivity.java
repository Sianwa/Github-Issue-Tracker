package com.example.githubissuetracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.githubissuetracker.R;
import com.example.githubissuetracker.adapters.SingleIssueCommentsAdapter;
import com.example.githubissuetracker.fragments.dialogs.NewIssueSheetDialog;
import com.example.githubissuetracker.network.ApolloConnector;
import com.example.mygraphql.AddIssueCommentMutation;
import com.example.mygraphql.CloseThisIssueMutation;
import com.example.mygraphql.GetIssueDetailsQuery;
import com.example.mygraphql.ReOpenThisIssueMutation;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;

public class SingleIssueActivity extends AppCompatActivity {
    SingleIssueCommentsAdapter commentsAdapter;
    String repo_name ;
    int id ;
    String issue_id ;
    RecyclerView commentsRecyclerview;
    SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_issue);

        //get data from previous activity
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        repo_name = bundle.getString("repo_name");
        id = bundle.getInt("id");
        issue_id = bundle.getString("issue_id");


        //componets
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        commentsRecyclerview = findViewById(R.id.issues_comments);
        swipeContainer = findViewById(R.id.swipeContainer);

        //refresh page on new comment added
        swipeContainer.setOnRefreshListener(() -> fetchCommentsAsync());

        TextView issue_Title = findViewById(R.id.issueTitle);
        TextView issue_Author = findViewById(R.id.issueAuthor);
        TextView issue_State = findViewById(R.id.issueState);
        Chip label1 = findViewById(R.id.chip1);
        Chip label2 = findViewById(R.id.chip2);

        //get issue details
        ApolloConnector.setupClient().query(new GetIssueDetailsQuery(id, repo_name))
                .enqueue(new ApolloCall.Callback<GetIssueDetailsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetIssueDetailsQuery.Data> response) {
                        Log.e("Apollo", "Data " + response.getData().viewer().repository().issue().title());
                        String titleResponse, authorResponse, stateResponse;

                        titleResponse = response.getData().viewer().repository().issue().title();
                        authorResponse = response.getData().viewer().repository().issue().author().login();
                        stateResponse = response.getData().viewer().repository().issue().state().rawValue();
                        int labelSize = response.getData().viewer().repository().issue().labels().nodes().size();

                        SingleIssueActivity.this.runOnUiThread(() -> {
                            issue_Title.setText(titleResponse);
                            issue_Author.setText(authorResponse);
                            issue_State.setText(stateResponse);

                            if(labelSize == 1) {
                                label1.setText(response.getData().viewer().repository().issue().labels().nodes().get(0).name());
                                label2.setVisibility(View.GONE);

                            }else if (labelSize == 2){
                                label1.setText(response.getData().viewer().repository().issue().labels().nodes().get(0).name());
                                label2.setText(response.getData().viewer().repository().issue().labels().nodes().get(1).name());
                            }
                            else if(labelSize == 0){
                                label1.setVisibility(View.GONE);
                                label2.setVisibility(View.GONE);
                            }

                            //handling comments
                            commentsAdapter = new SingleIssueCommentsAdapter(response.getData().viewer().repository().issue().comments().edges(), getBaseContext());
                            commentsRecyclerview.setHasFixedSize(true);
                            commentsRecyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            commentsRecyclerview.setAdapter(commentsAdapter);
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);

                    }
                });

    }

    private void fetchCommentsAsync() {
        ApolloConnector.setupClient().query(new GetIssueDetailsQuery(id, repo_name))
                .enqueue(new ApolloCall.Callback<GetIssueDetailsQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetIssueDetailsQuery.Data> response) {
                        Log.e("Apollo", "Data " + response.getData().viewer().repository().issue().title());

                        SingleIssueActivity.this.runOnUiThread(() -> {
                            //handling comments
                            commentsAdapter = new SingleIssueCommentsAdapter(response.getData().viewer().repository().issue().comments().edges(), getBaseContext());
                            commentsRecyclerview.setHasFixedSize(true);
                            commentsRecyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            commentsRecyclerview.setAdapter(commentsAdapter);

                            swipeContainer.setRefreshing(false);
                        });
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.issue_options, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.closeIssue:
                mutationCloseIssue();
                //Toast.makeText(this,"Close Issue",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reopenIssue:
                mutationReOpenIssue();
                //Toast.makeText(this,"Reopen Issue",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.addComment:
                NewIssueSheetDialog dialog = new NewIssueSheetDialog();
                //data for the bottomsheet
                Bundle newBundle = new Bundle();
                newBundle.putString("issue_id", issue_id);

                dialog.setArguments(newBundle);
                dialog.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mutationReOpenIssue() {
        ApolloConnector.setupClient().mutate(new ReOpenThisIssueMutation(issue_id))
                .enqueue(new ApolloCall.Callback<ReOpenThisIssueMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<ReOpenThisIssueMutation.Data> response) {
                        Log.e("Apollo", "Comment Added " + response);
                        //Toast.makeText(getActivity(), "Comment Added", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);
                    }
                });
    }


    private void mutationCloseIssue() {
        ApolloConnector.setupClient().mutate(new CloseThisIssueMutation(issue_id))
                .enqueue(new ApolloCall.Callback<CloseThisIssueMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<CloseThisIssueMutation.Data> response) {
                        Log.e("Apollo", "Comment Added " + response);
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);
                    }
                });
    }

}