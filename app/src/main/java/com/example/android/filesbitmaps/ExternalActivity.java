package com.example.android.filesbitmaps;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.filesbitmaps.utils.ExternalFiles;

import java.io.File;
import java.io.IOException;

public class ExternalActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private Button mBtnCreateExternal;

    private EditText mEtFileName;
    private EditText mEtFileContent;

    private TextView mTvFullPath;
    private TextView mTvDirContent;
    private TextView mTvFileContent;

    private ExternalFiles externalFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        mContext = ExternalActivity.this;
        externalFiles = new ExternalFiles(mContext);


        mBtnCreateExternal = (Button) findViewById(R.id.btn_create_external);
        mBtnCreateExternal.setOnClickListener(this);

        mEtFileName = (EditText) findViewById(R.id.et_external_file_name);
        mEtFileContent = (EditText) findViewById(R.id.et_external_file_content);

        mTvFullPath = (TextView) findViewById(R.id.tv_external_full_path);
        mTvDirContent = (TextView) findViewById(R.id.tv_external_dir_content);
        mTvFileContent = (TextView) findViewById(R.id.tv_external_file_content);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_external:
                createFile();
                return;
        }
    }

    private void createFile() {
        // check for possibility to write to external
        if (!externalFiles.isExternalStorageWritable()) {
            Toast.makeText(mContext, "External Storage Inaccessible", Toast.LENGTH_LONG).show();
            return;
        }

        String fileName = mEtFileName.getText().toString();
        String fileContent = mEtFileContent.getText().toString();

        File dir = externalFiles.getExternalDir(ExternalFiles.EXTERNAL_PUBLIC_DIR, Environment.DIRECTORY_DOCUMENTS);
        File file = externalFiles.getFile(fileName, ExternalFiles.EXTERNAL_PUBLIC_DIR, Environment.DIRECTORY_DOCUMENTS);

        try {
            externalFiles.writeToFile(file, fileContent);
            Toast.makeText(mContext, "Done Writing", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Unable to write to file", Toast.LENGTH_LONG).show();
        }

        // read file
        try {
            String content = externalFiles.readFromFile(file);
            mTvFileContent.setText(content);
            Toast.makeText(mContext, "Done Reading", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Unable to read From File", Toast.LENGTH_LONG).show();
        }


        // set file full path
        mTvFullPath.setText(file.getAbsolutePath());


        // set dir contents
        mTvDirContent.setText("");
        String[] dirContent = externalFiles.getDirContent(dir);
        if (dirContent == null) {
            Toast.makeText(mContext, "No content", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Length: " + dirContent.length, Toast.LENGTH_LONG).show();
            for (String currContent: externalFiles.getDirContent(dir)) {
                mTvDirContent.append(currContent + "\n");
            }
        }


    }

}
