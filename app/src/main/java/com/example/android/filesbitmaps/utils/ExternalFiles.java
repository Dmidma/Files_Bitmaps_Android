package com.example.android.filesbitmaps.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

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
     *  - private
     *  - public
     */


    public static final int EXTERNAL_PRIVATE_DIR = 1;
    public static final int EXTERNAL_PRIVATE_CACHE_DIR = 2;

    public static final int EXTERNAL_PUBLIC_DIR = 3;
    public static final int EXTERNAL_PUBLIC_CACHE_DIR = 4;



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


    public File getExternalDir(int dirType) {
        switch (dirType) {
            case EXTERNAL_PRIVATE_DIR:
                return null;
            case EXTERNAL_PRIVATE_CACHE_DIR:
                return null;
            case EXTERNAL_PUBLIC_DIR:
                return null;
            case EXTERNAL_PUBLIC_CACHE_DIR:
                return null;
            default:
                throw new IllegalArgumentException("Must use defined types of class");
        }
    }


    public void testFunction() {

    }



}
