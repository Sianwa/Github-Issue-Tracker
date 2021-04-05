package com.example.githubissuetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubissuetracker.R;
import com.example.mygraphql.GetIssueDetailsQuery;

import java.util.List;

public class SingleIssueCommentsAdapter extends RecyclerView.Adapter<SingleIssueCommentsAdapter.CommentsViewHolder> {
    List<GetIssueDetailsQuery.Edge> myIssueDetails;
    Context context;

    public SingleIssueCommentsAdapter(List<GetIssueDetailsQuery.Edge> myIssueDetails, Context context) {
        this.myIssueDetails = myIssueDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issue_comment_card, parent, false);

        return new CommentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        GetIssueDetailsQuery.Edge query = myIssueDetails.get(position);
        holder.commentAuthor.setText(query.node().author().login());
        holder.commentBody.setText(query.node().bodyText());

    }

    @Override
    public int getItemCount() {
        return myIssueDetails.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        TextView commentAuthor, commentBody;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthor = itemView.findViewById(R.id.commentAuthor);
            commentBody = itemView.findViewById(R.id.issueBody);
        }
    }
}
