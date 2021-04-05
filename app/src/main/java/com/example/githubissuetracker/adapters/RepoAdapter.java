package com.example.githubissuetracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubissuetracker.R;
import com.example.githubissuetracker.activities.RepoIssuesActivity;
import com.example.mygraphql.GetMyReposQuery;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyRepoViewHolder>{
    List<GetMyReposQuery.Node> myRepoData;
    Context context;


    public RepoAdapter(List<GetMyReposQuery.Node> myRepoData, Context context) {
        this.myRepoData = myRepoData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyRepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_rows, parent, false);

        return new MyRepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRepoViewHolder holder, int position) {
        GetMyReposQuery.Node query = myRepoData.get(position);
        holder.projectText.setText(query.name());
        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, RepoIssuesActivity.class);
            intent.putExtra("repo_name",query.name());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myRepoData.size();

    }

    public static class MyRepoViewHolder extends RecyclerView.ViewHolder{
        TextView projectText;
        LinearLayout parentLayout;

        public MyRepoViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            projectText = itemView.findViewById(R.id.projectName);
        }
    }
}
