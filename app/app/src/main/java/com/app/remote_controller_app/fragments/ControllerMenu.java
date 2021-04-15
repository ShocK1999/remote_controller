package com.app.remote_controller_app.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.app.remote_controller_app.R;


public class ControllerMenu extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_controller_menu, container, false);

        Button btnEditMode = (Button) view.findViewById(R.id.button_editMode);
        btnEditMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ControllerMenu.this).navigate(R.id.action_controllerMenu_to_editMode);
            }
        });

        Button btnUsageMode = (Button) view.findViewById(R.id.button_usageMode);
        btnUsageMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ControllerMenu.this).navigate(R.id.action_controllerMenu_to_usageMode);
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.button_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}