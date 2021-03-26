package com.example.githubissuetracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.githubissuetracker.R;
import com.example.githubissuetracker.fragments.HomeFragment;
import com.example.githubissuetracker.fragments.IssueFragment;
import com.example.githubissuetracker.fragments.NotificationFragment;
import com.example.githubissuetracker.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //components
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);


        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.homeFrag:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.notificationFrag:
                    selectedFragment = new NotificationFragment();
                    break;
                case R.id.profileFrag:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container,selectedFragment)
                                        .commit();
            return true;
        }
    };
}