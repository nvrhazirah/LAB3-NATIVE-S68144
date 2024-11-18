package com.example.fragmentcontoh1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load MyFragment dynamically if it hasn't been loaded yet
        if (savedInstanceState == null) {
            MyFragment myFragment = new MyFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, myFragment);
            transaction.commit();
        }

        // Check if Fragment2 is already added to avoid replacing it multiple times
        if (getSupportFragmentManager().findFragmentById(R.id.fragment2) == null) {
            Fragment2 fragment2 = new Fragment2();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment2, fragment2);  // Assuming you have a container for Fragment2
            transaction.commit();
        }
    }

    // Method to send data to Fragment2
    public void sendDataToFragment2(String data) {
        // Ensure that Fragment2 is already loaded
        Fragment2 fragment2 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        if (fragment2 != null) {
            fragment2.updateData(data);  // Update Fragment2 with new data
        }
    }
}
