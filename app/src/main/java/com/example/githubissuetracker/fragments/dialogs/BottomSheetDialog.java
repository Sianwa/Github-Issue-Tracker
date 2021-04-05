package com.example.githubissuetracker.fragments.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubissuetracker.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    BottomSheetListener mListener;
    final String repo_name = "Github-Issue-Tracker";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.filter_bottom_sheet,container, false);
        Button openFilter = v.findViewById(R.id.open_issue);
        Button closeFilter = v.findViewById(R.id.closed_issue);

        //TODO:Replace with switch statement instead

        closeFilter.setOnClickListener(v1 -> {
            mListener.onButtonClick("CLOSED");
            dismiss();
        });

        openFilter.setOnClickListener(v12 ->{
            mListener.onButtonClick("OPEN");
            dismiss();
        });

        return v;
    }

    public interface BottomSheetListener{
        void onButtonClick(String filterText);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
