package com.example.android.filesbitmaps.utils;


import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InternalFiles {

    /**
     * To use Internal Storage we don't need any permission.
     */



    private Context mcontext;


    // Internal dir
    public static final int INTERNAL_DIR = 1;
    // Internal cache dir
    public static final int INTERNAL_CACHE_DIR = 2;


    public InternalFiles(Context context) {
        mcontext = context;
    }



    public File getInternalDir(int dirType) {
        switch (dirType) {
            case INTERNAL_DIR:
                return mcontext.getFilesDir();
            case INTERNAL_CACHE_DIR:
                return mcontext.getCacheDir();
            default:
                throw new IllegalArgumentException("Must use defined types of class");
        }
    }


    public File getFile(String fileName, int dirType) {
        return new File(getInternalDir(dirType), fileName);
    }

    /**
     *
     * @param filename
     * @param filecontent
     * @param operationMode Can be either Context.MODE_PRIVATE (only accessed by the calling app)
     *                      or Context.MODE_APPEND (append to file)
     * @throws IOException
     */
    public void writeToInternalFile(String filename, String filecontent, int operationMode) throws IOException {
        FileOutputStream fos = mcontext.openFileOutput(filename, operationMode);
        fos.write(filecontent.getBytes());
        fos.close();
    }

    public String readFromInternalFile(String filename) throws IOException {
        FileInputStream fis = mcontext.openFileInput(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        fis.close();
        return sb.toString();
    }



    public boolean deleteInternalFile(String filename) {
        return mcontext.deleteFile(filename);
    }


    public boolean deleteFile(File file) {
        return file.delete();
    }

}
