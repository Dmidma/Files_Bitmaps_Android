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



    private Context mContext;


    // Internal dir
    public static final int INTERNAL_DIR = 1;
    // Internal cache dir
    public static final int INTERNAL_CACHE_DIR = 2;


    public InternalFiles(Context context) {
        mContext = context;
    }



    public File getInternalDir(int dirType) {
        switch (dirType) {
            case INTERNAL_DIR:
                return mContext.getFilesDir();
            case INTERNAL_CACHE_DIR:
                return mContext.getCacheDir();
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
     * @param fileContent
     * @param operationMode Can be either Context.MODE_PRIVATE (only accessed by the calling app)
     *                      or Context.MODE_APPEND (append to file)
     * @throws IOException
     */
    public void writeToInternalFile(String filename, String fileContent, int operationMode) throws IOException {
        FileOutputStream fos = mContext.openFileOutput(filename, operationMode);
        fos.write(fileContent.getBytes());
        fos.close();
    }

    public String readFromInternalFile(String filename) throws IOException {
        FileInputStream fis = mContext.openFileInput(filename);
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
        return mContext.deleteFile(filename);
    }

    // get contents of internal directory
    public String[] getInternalDirContents() {
        return getInternalDir(INTERNAL_DIR).list();
    }

    public String[] getInternalCacheDirContents() {
        return getInternalDir(INTERNAL_CACHE_DIR).list();
    }

    public File getUniqueNameFile(String prefix, String suffix, int dirType) throws IOException {
        return File.createTempFile(prefix, suffix, getInternalDir(dirType));

    }

    public boolean deleteFile(File file) {
        return file.delete();
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
