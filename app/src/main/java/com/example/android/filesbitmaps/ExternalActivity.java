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

public class ExternalActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private Button mBtnCreateExternal;

    private EditText mEtFileName;

    private TextView mTvFullPath;
    private TextView mTvDirContent;

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

        mTvFullPath = (TextView) findViewById(R.id.tv_external_full_path);
        mTvDirContent = (TextView) findViewById(R.id.tv_external_dir_content);

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

        File dir = externalFiles.getExternalDir(ExternalFiles.EXTERNAL_PRIVATE_DIR, null);

        mTvFullPath.setText(dir.getAbsolutePath());

        mTvDirContent.setText("");
        String[] dirContent = externalFiles.getDirContent(dir);
        if (dirContent == null) {
            Toast.makeText(mContext, "No content", Toast.LENGTH_LONG).show();
        } else {
            for (String currContent: externalFiles.getDirContent(dir)) {
                mTvDirContent.append(currContent + "\n");
            }
        }


    }

}
