package com.example.fragmentcontoh1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    private TextView textView; // Declare TextView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2, container, false);

        // Initialize the TextView
        textView = view.findViewById(R.id.textView);

        return view;
    }

    // Method to update TextView with data
    public void updateData(String data) {
        if (textView != null) {
            textView.setText(data);  // Set the new data to the TextView
        }
    }
}
