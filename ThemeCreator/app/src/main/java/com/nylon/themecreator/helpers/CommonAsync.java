package com.nylon.themecreator.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.chrisplus.rootmanager.RootManager;
import com.chrisplus.rootmanager.container.Result;
import com.nylon.themecreator.activities.MainActivity;


public class CommonAsync extends AsyncTask<String, Void, Result> {

    @Override
    protected Result doInBackground(String... strings) {
        RootManager.getInstance().runCommand("su");
        RootManager.getInstance().runCommand("mount -o rw, remount -t yaffs2 /dev/block/mtdblock4 /system");
        RootManager.getInstance().runCommand("cp " + MainActivity.TARGET_BASE_PATH + "temp.zip /system/etc/gboard_theme/" + strings[0]);
        RootManager.getInstance().runCommand("chmod 664 /system/etc/gboard_theme/" + strings[0]);
        RootManager.getInstance().runCommand("mount -o ro, remount -t yaffs2 /dev/block/mtdblock4 /system");

        return RootManager.getInstance().runCommand("ls /assets/ | grep icon_enter.png");
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        Log.d("RootManager","Command executed, " + result.getResult() + " with message " + result.getMessage());
    }
}
