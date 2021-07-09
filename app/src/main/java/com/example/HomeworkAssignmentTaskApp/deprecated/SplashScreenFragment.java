package com.example.HomeworkAssignmentTaskApp.deprecated;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.HomeworkAssignmentTaskApp.R;

public class SplashScreenFragment extends Fragment {

    public SplashScreenFragment() {
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
        View root = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        Button openApp = root.findViewById(R.id.buttonOpenApp);
        openApp.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_open_app));

        return root;
    }
}