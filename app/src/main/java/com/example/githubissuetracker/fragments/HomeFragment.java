package com.example.githubissuetracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.githubissuetracker.R;
import com.example.githubissuetracker.adapters.RepoAdapter;
import com.example.githubissuetracker.network.ApolloConnector;
import com.example.mygraphql.GetMyReposQuery;

import org.jetbrains.annotations.NotNull;


public class HomeFragment extends Fragment {
    RepoAdapter repoAdapter;
    TextView myUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //fragment components
        myUser = view.findViewById(R.id.welcomeUser);
        RecyclerView recyclerView = view.findViewById(R.id.myRepos);


        //get repositories
        ApolloConnector.setupClient().query(new GetMyReposQuery())
                .enqueue(new ApolloCall.Callback<GetMyReposQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetMyReposQuery.Data> response) {
                        Log.e("Apollo", "Datas: " + response.getData().viewer().repositories().nodes().get(0).name());

                        //update UI on UI thread
                        getActivity().runOnUiThread(() -> {
                            myUser.setText("Hello " + response.getData().viewer().name());
                            repoAdapter = new RepoAdapter(response.getData().viewer().repositories().nodes(), getContext());
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            recyclerView.setAdapter(repoAdapter);
                            repoAdapter.notifyDataSetChanged();
                        });

                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);
                    }
                });
        return view;
    }


}