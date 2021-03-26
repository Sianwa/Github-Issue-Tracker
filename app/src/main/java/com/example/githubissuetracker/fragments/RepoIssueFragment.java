package com.example.githubissuetracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubissuetracker.R;

public class RepoIssueFragment extends Fragment {

    public RepoIssueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_repo_issue, container, false);
        //fragment components
            //TODO : Make filter drawable clickable to open filter bottom sheet.

        return v;
    }
}