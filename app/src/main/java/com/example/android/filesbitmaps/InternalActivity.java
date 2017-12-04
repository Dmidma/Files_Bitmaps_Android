package com.example.android.filesbitmaps;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.filesbitmaps.utils.InternalFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = InternalActivity.class.getSimpleName();

    private Context mContext;

    private Button mBtnReadFile;
    private Button mBtnCreateFile;
    private Button mBtnDeleteFile;

    private EditText mEtFileName;
    private EditText mEtFileContent;

    private TextView mTvFileName;
    private TextView mTvFileContent;
    private TextView mTvDirContent;

    private InternalFiles internalFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        mContext = InternalActivity.this;

        internalFiles = new InternalFiles(mContext);

        mBtnCreateFile = (Button) findViewById(R.id.btn_create_file);
        mBtnReadFile = (Button) findViewById(R.id.btn_read_file);
        mBtnDeleteFile = (Button) findViewById(R.id.btn_delete_file);

        mBtnCreateFile.setOnClickListener(this);
        mBtnReadFile.setOnClickListener(this);
        mBtnDeleteFile.setOnClickListener(this);

        mEtFileName = (EditText) findViewById(R.id.et_filname);
        mEtFileContent = (EditText) findViewById(R.id.et_file_content);

        mTvFileContent = (TextView) findViewById(R.id.tv_file_content);
        mTvFileName = (TextView) findViewById(R.id.tv_full_path);
        mTvDirContent = (TextView) findViewById(R.id.tv_dir_content);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_file:
                createFile();
                return;
            case R.id.btn_read_file:
                readFile();
                return;
            case R.id.btn_delete_file:
                deleteFile();
                return;
        }
    }

    private void createFile() {
        String fileName = mEtFileName.getText().toString();
        String fileContent = mEtFileContent.getText().toString();

        try {
            internalFiles.writeToInternalFile(fileName, fileContent, mContext.MODE_APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Unable to write to File");
        }


        Toast.makeText(mContext, "Done Creating", Toast.LENGTH_LONG).show();
    }


    private void readFile() {
        mTvFileContent.setText("");
        mTvFileName.setText("");

        String fileName = mEtFileName.getText().toString();

        try {
            String fileContent = internalFiles.readFromInternalFile(fileName);
            mTvFileContent.setText(fileContent);
            mTvFileName.setText(fileName + "|" + internalFiles.getFile(fileName, InternalFiles.INTERNAL_DIR).getAbsolutePath());
            Toast.makeText(mContext, "Done Reading", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            Log.d(TAG, "File does not exist");
            Toast.makeText(mContext, "File Does not exist", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Unable to read from File");
            Toast.makeText(mContext, "Opps an error", Toast.LENGTH_LONG).show();
        }


        // clear dir content
        mTvDirContent.setText("");
        File internalDir = internalFiles.getInternalDir(InternalFiles.INTERNAL_CACHE_DIR);
        for (String currContent: internalDir.list()) {
            mTvDirContent.append(currContent + "\n");
        }


    }

    private void deleteFile() {
        String fileName = mEtFileName.getText().toString();


        if (internalFiles.deleteFile(internalFiles.getFile(fileName, InternalFiles.INTERNAL_DIR))) {
            Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Not Deleted", Toast.LENGTH_LONG).show();
        }

        /*
        if (internalFiles.deleteInternalFile(fileName)) {
            Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Not Deleted", Toast.LENGTH_LONG).show();
        }
        */
    }
}
