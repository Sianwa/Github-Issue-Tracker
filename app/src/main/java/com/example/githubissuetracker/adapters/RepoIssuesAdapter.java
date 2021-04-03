package com.example.githubissuetracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubissuetracker.R;
import com.example.githubissuetracker.activities.RepoIssuesActivity;
import com.example.githubissuetracker.activities.SingleIssueActivity;
import com.example.mygraphql.GetRepoIssuesQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RepoIssuesAdapter extends RecyclerView.Adapter<RepoIssuesAdapter.MyIssuesViewHolder>{
    List<GetRepoIssuesQuery.Edge> myRepoIssues;
    Context context;

    public RepoIssuesAdapter(List<GetRepoIssuesQuery.Edge> myRepoIssues, Context context) {
        this.myRepoIssues = myRepoIssues;
        this.context = context;
    }

    @NonNull
    @Override
    public MyIssuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issues_rows, parent, false);
        return new MyIssuesViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyIssuesViewHolder holder, int position) {
        GetRepoIssuesQuery.Edge query  = myRepoIssues.get(position);
        holder.issue_author.setText(query.node().author().login());
        holder.issue_title.setText(query.node().title());
        holder.issue_date.setText(query.node().state().rawValue());

        //on item click
        holder.parentLayout.setOnClickListener(v->{
            Intent SingleIssueIntent = new Intent(context, SingleIssueActivity.class);
            context.startActivity(SingleIssueIntent);
        });

    }

    @Override
    public int getItemCount() {
        return myRepoIssues.size();
    }

    public class MyIssuesViewHolder extends RecyclerView.ViewHolder {
        TextView issue_title, issue_author, issue_date;
        LinearLayout parentLayout;

        public MyIssuesViewHolder(@NonNull View itemView) {
            super(itemView);
            issue_title = itemView.findViewById(R.id.issueTitle);
            issue_author = itemView.findViewById(R.id.issueAuthor);
            issue_date = itemView.findViewById(R.id.issueDate);
            parentLayout = itemView.findViewById(R.id.issue_parent_layout);
        }
    }
}
