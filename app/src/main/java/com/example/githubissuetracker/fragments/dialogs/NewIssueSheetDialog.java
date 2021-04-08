package com.example.githubissuetracker.fragments.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.githubissuetracker.R;
import com.example.githubissuetracker.network.ApolloConnector;
import com.example.mygraphql.AddIssueCommentMutation;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class NewIssueSheetDialog extends BottomSheetDialogFragment {

    String issue_id;
    String issue_comment;
    Bundle myBundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.new_issue_bottom_sheet,container, false);
        Button addIssue = v.findViewById(R.id.add_issue);
        TextInputLayout textInputLayout = v.findViewById(R.id.outlinedTextArea);

        myBundle = new Bundle();
        myBundle = getArguments();
        issue_id = myBundle.getString("issue_id");

        addIssue.setOnClickListener(v1 -> {
            issue_comment = textInputLayout.getEditText().getText().toString();
            ApolloConnector.setupClient().mutate(new AddIssueCommentMutation(issue_id, issue_comment))
                    .enqueue(new ApolloCall.Callback<AddIssueCommentMutation.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<AddIssueCommentMutation.Data> response) {
                            Log.e("Apollo", "Comment Added " + issue_comment);
                            dismiss();
                            //Toast.makeText(getActivity(), "Comment Added", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            Log.e("Apollo", "Error", e);
                        }
                    });
        });
        return v;
    }
}
