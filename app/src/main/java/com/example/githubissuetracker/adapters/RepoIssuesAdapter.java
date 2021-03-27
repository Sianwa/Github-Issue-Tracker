package com.example.githubissuetracker.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RepoIssuesAdapter extends RecyclerView.Adapter<RepoIssuesAdapter.MyIssuesViewHolder>{

    @NonNull
    @Override
    public MyIssuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyIssuesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyIssuesViewHolder extends RecyclerView.ViewHolder {
        public MyIssuesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
