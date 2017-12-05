package com.example.android.filesbitmaps;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.filesbitmaps.utils.InternalFiles;

import java.io.File;
import java.io.IOException;

public class InternalCacheActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = InternalCacheActivity.class.getSimpleName();

    private Context mContext;

    private Button mBtnReadFile;
    private Button mBtnCreateFile;
    private Button mBtnDeleteFile;

    private EditText mEtFileName;
    private EditText mEtFileContent;
    private EditText mEtFileSuffix;

    private TextView mTvFileName;
    private TextView mTvFileContent;
    private TextView mTvDirContent;

    private InternalFiles internalFiles;

    private File mFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_cache);


        mContext = InternalCacheActivity.this;

        internalFiles = new InternalFiles(mContext);

        mBtnCreateFile = (Button) findViewById(R.id.btn_create_file_cache);
        mBtnReadFile = (Button) findViewById(R.id.btn_read_file_cache);
        mBtnDeleteFile = (Button) findViewById(R.id.btn_delete_file_cache);

        mBtnCreateFile.setOnClickListener(this);
        mBtnReadFile.setOnClickListener(this);
        mBtnDeleteFile.setOnClickListener(this);

        mEtFileName = (EditText) findViewById(R.id.et_filname_cache);
        mEtFileContent = (EditText) findViewById(R.id.et_file_content_cache);
        mEtFileSuffix = (EditText) findViewById(R.id.et_suffix_cache);

        mTvFileContent = (TextView) findViewById(R.id.tv_file_content_cache);
        mTvFileName = (TextView) findViewById(R.id.tv_full_path_cache);
        mTvDirContent = (TextView) findViewById(R.id.tv_dir_content_cache);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_file_cache:
                createFile();
                return;
            case R.id.btn_read_file_cache:
                readFile();
                return;
            case R.id.btn_delete_file_cache:
                deleteFile();
                return;
        }
    }

    private void createFile() {
        String fileName = mEtFileName.getText().toString();
        String suffix = mEtFileSuffix.getText().toString();
        String fileContent = mEtFileContent.getText().toString();


        try {
            mFile = internalFiles.getUniqueNameFile(fileName, suffix, InternalFiles.INTERNAL_CACHE_DIR);
            internalFiles.writeToFile(mFile, fileContent);
            Toast.makeText(mContext, "Done Creating", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {


        mTvFileContent.setText("");
        mTvFileName.setText("");
        String fileName = mEtFileName.getText().toString();

        File aFile = internalFiles.getFile(fileName, InternalFiles.INTERNAL_CACHE_DIR);

        mTvFileName.setText(aFile.getName() + " | " + aFile.getAbsolutePath());
        try {
            mTvFileContent.setText(internalFiles.readFromFile(aFile));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Done Reading", Toast.LENGTH_LONG).show();
        }

        mTvDirContent.setText("");
        // read content of dir
        for (String currContent: internalFiles.getInternalCacheDirContents()) {
            mTvDirContent.append(currContent + "\n");
        }

    }

    private void deleteFile() {
        if (internalFiles.deleteFile(mFile))
            Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mContext, "Not deleted", Toast.LENGTH_LONG).show();
    }


}
