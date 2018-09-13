package com.nylon.themecreator.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.nylon.themecreator.R;

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ConfigViewHolder> {
    private String[] configs;

    class ConfigViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button colorPickerButton;

        public ConfigViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.config_title);
            colorPickerButton = v.findViewById(R.id.color_button);
        }
    }

    public ConfigAdapter(String[] color) {
        configs = color;
    }

    @NonNull
    @Override
    public ConfigViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.config_cell, viewGroup, false);
        return new ConfigViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigViewHolder configViewHolder, int i) {
        configViewHolder.textView.setText(configs[configViewHolder.getAdapterPosition()]);
    }


    @Override
    public int getItemCount() {
        return configs.length;
    }
}
