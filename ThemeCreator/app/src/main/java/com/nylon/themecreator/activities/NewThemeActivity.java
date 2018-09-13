package com.nylon.themecreator.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nylon.themecreator.helpers.CSSHelper;
import com.nylon.themecreator.helpers.CommonAsync;
import com.nylon.themecreator.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

import static com.nylon.themecreator.activities.MainActivity.TARGET_BASE_PATH;

public class NewThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        String color = null;
        try {
           color = CSSHelper.getColors();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("GTheme", color);
        color = color.replace("@def color_base #fafafa;", "@def color_base #000000;");
        CSSHelper.saveColors(color);
        ZipUtil.pack(new File(TARGET_BASE_PATH + "file"), new File(TARGET_BASE_PATH + "temp.zip"));
        new CommonAsync().execute("TEST22.zip");
    }
}
