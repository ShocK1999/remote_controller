package com.app.remote_controller_app.fragments.component_options;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.remote_controller_app.MainActivity;
import com.app.remote_controller_app.R;


public class LEDOptions extends Fragment {

    com.app.remote_controller_app.components.LED thisComponent;

    EditText editText_id;
    EditText editText_sizeX;
    EditText editText_sizeY;
    EditText editText_posX;
    EditText editText_posY;
    Button button_save;
    Button button_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisComponent = (com.app.remote_controller_app.components.LED) ((MainActivity) getActivity()).getCurrentSelectedComponent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_led_options, container, false);

        inflateViews(view);

        button_save = (Button) view.findViewById(R.id.button_LED_save);
        button_save.setOnClickListener(listenerSaveButton);

        button_delete = (Button) view.findViewById(R.id.button_LED_delete);
        button_delete.setOnClickListener(listenerDeleteButton);

        return view;
    }

    // Metoda odpowiedzialna za uzupełnienie wszystkich widoków //
    private void inflateViews(View view) {
        // Plain Text ID //
        editText_id = view.findViewById(R.id.editText_LED_id);
        editText_id.setText(thisComponent.getId());

        // Plain Text Size X //
        editText_sizeX = view.findViewById(R.id.editTextNumber_LED_sizeX);

        // Plain Text Size Y //
        editText_sizeY = view.findViewById(R.id.editTextNumber_LED_sizeY);

        // Plain Text Position X //
        editText_posX = view.findViewById(R.id.editTextNumber_LED_positionX);

        // Plain Text Position Y //
        editText_posY = view.findViewById(R.id.editTextNumber_LED_positionY);
    }

    // Metoda dla przycisku "Zapisz" //
    View.OnClickListener listenerSaveButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).onBackPressed();
        }
    };

    // Metoda dla przycisku "Usuń" //
    View.OnClickListener listenerDeleteButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Metoda do usuwania LED  <--- TUTAJ DOPISAC //

            ((MainActivity) getActivity()).onBackPressed();
            Toast.makeText(getActivity(), getString(R.string.label_deleted), Toast.LENGTH_SHORT).show();
        }
    };
}