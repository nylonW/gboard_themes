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

    public static String[] parseConfigOriginal(String color) {
        //delete css comments
        color = color.replaceAll("\\/\\*[\\s\\S]*?\\*\\/|([^:]|^)\\/\\/.*$","");
        String[] lines = color.split(System.getProperty("line.separator"));

        for(int i = 0; i < lines.length; i++) {
            if(!lines[i].startsWith("@def") || lines[i].contains("corner_radius")) {
                lines[i] = "";
            }
        }
        List<String> list = new ArrayList<>(Arrays.asList(lines));
        list.removeAll(Arrays.asList("", null));
        lines = list.toArray(new String[0]);

        return lines;
    }

    public static String[] parseHexColors(String[] lines) {
        String[] temp = new String[lines.length];
        for(int i = 0; i < lines.length; i++) {
            Log.d("HEXC", lines[i]);
            temp[i] = "#" + lines[i].substring(lines[i].indexOf("#") + 1, lines[i].indexOf(";"));
            temp[i] = temp[i].substring(0,7);
        }

        return temp;
    }

    public static String[] parseConfigFormatted(String[] original) {
        String[] temp = new String[original.length];
        for(int i = 0; i < original.length; i++) {
            temp[i] = original[i].replaceAll("@def ", "").replaceAll("_", " ").replaceAll("(?=#)(.*)(?<=;)","");
        }

        return temp;
    }
}
