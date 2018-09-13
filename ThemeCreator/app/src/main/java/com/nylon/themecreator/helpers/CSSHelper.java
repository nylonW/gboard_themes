package com.nylon.themecreator.helpers;

import android.util.Log;

import com.nylon.themecreator.activities.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSSHelper {

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getColors() throws Exception {
        File fl = new File(MainActivity.TARGET_BASE_PATH + "file/style_sheet_oneplus.css");
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }

    public static void saveColors(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(MainActivity.TARGET_BASE_PATH +"file/", "style_sheet_oneplus.css")));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String[] parseConfig(String color) {
        //delete css comments
        color = color.replaceAll("\\/\\*[\\s\\S]*?\\*\\/|([^:]|^)\\/\\/.*$","");
        String[] lines = color.split(System.getProperty("line.separator"));

        for(int i = 0; i < lines.length; i++) {
            if(!lines[i].startsWith("@def")) {
                lines[i] = "";
            } else {
                lines[i] = lines[i].replaceAll("@def ","").replaceAll("_", " ");
            }
        }
        List<String> list = new ArrayList<>(Arrays.asList(lines));
        System.out.println(list);
        list.removeAll(Arrays.asList("", null));
        lines = list.toArray(new String[0]);
        return lines;
    }
}
