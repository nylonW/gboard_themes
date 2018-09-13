package com.nylon.themecreator.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nylon.themecreator.R;
import com.nylon.themecreator.helpers.CSSHelper;
import com.nylon.themecreator.helpers.CommonAsync;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

import static com.nylon.themecreator.activities.MainActivity.TARGET_BASE_PATH;

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ConfigViewHolder> {
    private String[] configs, readableConfig, hexColors;
    private String colorString;

    class ConfigViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button colorPickerButton;
        ImageView imageView;

        ConfigViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.config_title);
            colorPickerButton = v.findViewById(R.id.color_button);
            imageView = v.findViewById(R.id.color_view);
        }
    }

    public ConfigAdapter(String[] color, String[] original, String[] hexColors, String s) {
        this.configs = original;
        this.readableConfig = color;
        this.hexColors = hexColors;
        this.colorString = s;
    }

    @NonNull
    @Override
    public ConfigViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.config_cell, viewGroup, false);
        return new ConfigViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ConfigViewHolder configViewHolder, final int i) {
        configViewHolder.textView.setText(readableConfig[configViewHolder.getAdapterPosition()]);
        configViewHolder.imageView.setImageDrawable(new ColorDrawable(Color.parseColor(hexColors[configViewHolder.getAdapterPosition()])));
        configViewHolder.colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorChooserDialog dialog = new ColorChooserDialog(configViewHolder.colorPickerButton.getContext());
                dialog.setTitle("Choose color");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        configViewHolder.imageView.setImageDrawable(new ColorDrawable(color));
                        String hexColor = String.format("#%06X", (0xFFFFFF & color));
                        colorString = colorString.replaceAll(configs[i], configs[i].replaceAll("(?=#)(.*)(?<=;)",hexColor));
                        CSSHelper.saveColors(colorString);
                        ZipUtil.pack(new File(TARGET_BASE_PATH + "file"), new File(TARGET_BASE_PATH + "temp.zip"));
                        new CommonAsync().execute("finalTest.zip");

                    }
                });
                //customize the dialog however you want
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return configs.length;
    }
}
