package com.example.android.filesbitmaps.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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


    public File getExternalDir(int dirType, String whichDir) throws IllegalArgumentException {
        switch (dirType) {
            case EXTERNAL_PRIVATE_DIR:
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


    public File getFile(String fileName, int dirType, String whichDir) {
        return new File(getExternalDir(dirType, whichDir), fileName);
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

}
