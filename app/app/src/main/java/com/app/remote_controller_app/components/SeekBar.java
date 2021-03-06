package com.app.remote_controller_app.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.app.remote_controller_app.MainActivity;
import com.app.remote_controller_app.R;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SeekBar extends Component implements OutputComponent {

    int maxRange;
    int minRange;
    int currentValue;


    @JsonCreator
    public SeekBar(@JsonProperty("name") String name, @JsonProperty("id") String id,
                    @JsonProperty("sizeX") int sizeX, @JsonProperty("sizeY") int sizeY,
                    @JsonProperty("posX") int posX, @JsonProperty("posY") int posY,
                    @JsonProperty("maxRange") int maxRange, @JsonProperty("minRange") int minRange,
                    @JsonProperty("currentValue") int currentValue, @JsonProperty("layer") float layer){
        super(name, id, sizeX, sizeY, posX, posY, layer);
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.currentValue = currentValue;
    }

    public SeekBar(String name, String id) {
        super(name, id, 100, 30, 100, 100, 0);
        this.minRange = 0;
        this.maxRange = 100;
        this.currentValue = 50;
    }

    @Override
    public View getEditView(Context context, Fragment fragment) {
        android.widget.SeekBar seekBar = new android.widget.SeekBar(context);
        ConstraintLayout layout = new ConstraintLayout(context);
        setAndroidView(layout, context);
        setMove(layout, context);
        seekBar.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        seekBar.setEnabled(false);
        layout.addView(seekBar);

        SeekBar ths = this;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).setCurrentSelectedComponent(ths);
                NavHostFragment.findNavController(fragment).navigate(R.id.action_editMode_to_sliderOptions);
            }
        });

        return layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getUsageView(Context context) {
        android.widget.SeekBar seekBar = new android.widget.SeekBar(context);
        setAndroidView(seekBar, context);
        seekBar.setMax(maxRange);
        seekBar.setMin(minRange);

        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                currentValue = progress;

            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                send();
            }
        });
        return seekBar;
    }

    @Override
    public void send() {
        bluetoothService.send(String.valueOf(currentValue));
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }
}
