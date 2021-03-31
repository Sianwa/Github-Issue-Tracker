package com.example.githubissuetracker.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleIssueCommentsAdapter extends RecyclerView.Adapter<SingleIssueCommentsAdapter.CommentsViewHolder> {


    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
