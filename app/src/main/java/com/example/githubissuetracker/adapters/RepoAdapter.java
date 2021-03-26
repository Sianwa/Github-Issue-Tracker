package com.example.githubissuetracker.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyRepoViewHolder>{

    @NonNull
    @Override
    public MyRepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRepoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyRepoViewHolder extends RecyclerView.ViewHolder{

        public MyRepoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
