package com.example.android.filesbitmaps.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dmidma on 12/5/17.
 */

public class ExternalFiles {


    /**
     * To use this Utils class you need
     * WRITE_EXTERNAL_STORAGE
     * READ_EXTERNAL_STORAGE
     *
     * You are a big person, you know which one is needed in your case.
     *
     * Keep in mind that you can create two types of file in external storage:
     *  - private: Available to other apps (Must only have value to your app). Deleted
     *  when the app is uninstalled.
     *  - public: Available to other apps (Public usage). Won't be deleted when the app
     *  is uninstalled.
     */

    // no need for whichDir for the following 2
    public static final int EXTERNAL_PRIVATE_DIR = 1;
    public static final int EXTERNAL_PRIVATE_CACHE_DIR = 2;

    public static final int EXTERNAL_PUBLIC_DIR = 3;
    public static final int EXTERNAL_GENERAL_PUBLIC = 4;

    public static final int PERMISSION_REQUEST_CODE = 1234;


    private Context mContext;


    public ExternalFiles(Context context) {
        mContext = context;
    }

    /**
     * Always use the following two functions!!!!
     */

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * In the onRequestPermissionsResult of the activity you are using this class.
     *
     * switch(requestCode) {
     *     case ExternalFiles.PERMISSION_REQUEST_CODE:
     *      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
     *          // call to something
     *      }
     *      break;
     *
     * }
     */



    public File getExternalDir(int dirType, String whichDir) throws IllegalArgumentException {
        switch (dirType) {
            case EXTERNAL_PRIVATE_DIR:
                if ("".equals(whichDir) || whichDir.equals(null))
                    throw new IllegalArgumentException("Must specify Dir Type using Environment.xxx");
                return mContext.getExternalFilesDir(whichDir);
            case EXTERNAL_PRIVATE_CACHE_DIR:
                return mContext.getExternalCacheDir();
            case EXTERNAL_PUBLIC_DIR:
                if ("".equals(whichDir) || whichDir.equals(null))
                    throw new IllegalArgumentException("Must specify Dir Type using Environment.xxx");
                return Environment.getExternalStoragePublicDirectory(whichDir);
            case EXTERNAL_GENERAL_PUBLIC:
                return Environment.getExternalStorageDirectory();
            default:
                throw new IllegalArgumentException("Must use defined types of class");
        }
    }

    // This function will return the sub dir in the public dir.
    // return null when this does not succeed
    public File getPublicDir(int dirType, String whichDir, String subDir)  {

        if (dirType != EXTERNAL_PUBLIC_DIR && dirType != EXTERNAL_GENERAL_PUBLIC) {
            return null;
        }

        if ("".equals(subDir) || subDir == null) {
            return null;
        }

        File storageDir = new File(getExternalDir(dirType, whichDir) + File.separator + subDir);

        boolean success = true;
        if (!storageDir.exists()) {
            // mkdirs will create any nonexistent parent dir
            success = storageDir.mkdirs();
        }
        if (!success) {
            return null;
        }

        return storageDir;
    }


    public File getFile(String fileName, int dirType, String whichDir) {
        return new File(getExternalDir(dirType, whichDir), fileName);
    }

    public File getFile(String fileName, File storageDir) {
        return new File(storageDir, fileName);
    }

    public String[] getDirContent(File file) {
        if (file.isDirectory()) {
            return file.list();
        }
        return null;
    }


    public void writeToFile(File file, String fileContent) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileContent.getBytes());
        fos.close();
    }

    public String readFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        isr.close();
        fis.close();
        return sb.toString();
    }

    // return false if the permission is not given
    // return true if the permission was already given
    public static boolean checkPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }



    public File getUniqueNameFile(String prefix, String suffix, File storageDir) throws IOException {
        return File.createTempFile(prefix, suffix, storageDir);

    }


    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
    }




}
