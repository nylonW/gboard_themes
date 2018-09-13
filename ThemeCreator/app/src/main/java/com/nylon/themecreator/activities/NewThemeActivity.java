package com.nylon.themecreator.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nylon.themecreator.adapters.ConfigAdapter;
import com.nylon.themecreator.helpers.CSSHelper;
import com.nylon.themecreator.helpers.CommonAsync;
import com.nylon.themecreator.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.Arrays;

import static com.nylon.themecreator.activities.MainActivity.TARGET_BASE_PATH;

public class NewThemeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String color;
    String[] colors, hexColors, original;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        try {
           color = CSSHelper.getColors();
        } catch (Exception e) {
            e.printStackTrace();
        }


        original = CSSHelper.parseConfigOriginal(color);
        hexColors = CSSHelper.parseHexColors(original);
        colors = CSSHelper.parseConfigFormatted(original);

        //CSSHelper.saveColors(color);
        //ZipUtil.pack(new File(TARGET_BASE_PATH + "file"), new File(TARGET_BASE_PATH + "temp.zip"));
        //new CommonAsync().execute("TEST22.zip");
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.config_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConfigAdapter(colors, original, hexColors, color);
        recyclerView.setAdapter(adapter);
    }
}
