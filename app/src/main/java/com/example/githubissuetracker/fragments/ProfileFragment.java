package com.example.githubissuetracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.githubissuetracker.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        
        //fragment componets;
        ListView profileListView;
        String[] profileOptionsItems = {"Settings", "Help", "Privacy Policy", "Terms of Use", "Report a technical Problem", "Sign out"};
        
        profileListView = v.findViewById(R.id.profileOptions);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.profile_list_item, R.id.textview,profileOptionsItems);
        profileListView.setAdapter(arrayAdapter);
        return v;
    }
}