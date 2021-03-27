package com.example.githubissuetracker.fragments.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubissuetracker.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewIssueSheetDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.new_issue_bottom_sheet,container, false);
        Button addIssue = v.findViewById(R.id.add_issue);
        TextView titleTextView;

        addIssue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(),"Issue added", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}
