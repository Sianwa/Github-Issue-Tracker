package com.example.githubissuetracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubissuetracker.R;
import com.example.githubissuetracker.activities.RepoIssuesActivity;
import com.example.githubissuetracker.activities.SingleIssueActivity;
import com.example.mygraphql.GetRepoIssuesQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RepoIssuesAdapter extends RecyclerView.Adapter<RepoIssuesAdapter.MyIssuesViewHolder> implements Filterable {
    List<GetRepoIssuesQuery.Edge> myRepoIssues;
    List<GetRepoIssuesQuery.Edge> myFilteredRepoIssues;
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
            SingleIssueIntent.putExtra("id", query.node().number());
            SingleIssueIntent.putExtra("repo_name", query.node().repository().name());
            SingleIssueIntent.putExtra("issue_id", query.node().id());
            context.startActivity(SingleIssueIntent);
        });

    }

    @Override
    public int getItemCount() {
        int listSize = myRepoIssues.size();
        if(listSize == 0){
            Toast.makeText(context, "No records to show!", Toast.LENGTH_SHORT).show();
        }
        return listSize;

    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                     myRepoIssues = myRepoIssues;
                }else{
                    List<GetRepoIssuesQuery.Edge> filteredList = new ArrayList<>();
                    for(GetRepoIssuesQuery.Edge row : myRepoIssues){
                        if(row.node().title().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
                    myRepoIssues = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = myRepoIssues;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    myRepoIssues = ((List<GetRepoIssuesQuery.Edge>) results.values);
                    notifyDataSetChanged();
            }
        };
    }

    public void ft(String filterOption){
        if(filterOption.isEmpty()){
            Log.e("Apollo", "Filter cannot be empty" + filterOption);
        }else{
            List<GetRepoIssuesQuery.Edge> filteredList = new ArrayList<>();
            for(GetRepoIssuesQuery.Edge row : myRepoIssues){
                if(row.node().state().rawValue().equals(filterOption)){
                    filteredList.add(row);
                }
            }
            myRepoIssues = filteredList;
            notifyDataSetChanged();
        }

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
