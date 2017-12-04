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

public class TempFileActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private InternalFiles internalFiles;

    private EditText mEtPrefix;
    private EditText mEtSuffix;

    private TextView mTvTempFileName;
    private TextView mTvTempFullPath;

    private Button mBtnCreateTempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_file);

        mContext = TempFileActivity.this;
        internalFiles = new InternalFiles(mContext);

        mEtPrefix = (EditText) findViewById(R.id.et_prefix);
        mEtSuffix = (EditText) findViewById(R.id.et_suffix);

        mTvTempFileName = (TextView) findViewById(R.id.tv_temp_file_name);
        mTvTempFullPath = (TextView) findViewById(R.id.tv_temp_full_path);

        mBtnCreateTempFile = (Button) findViewById(R.id.btn_create_temp_file);
        mBtnCreateTempFile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_temp_file:
                createTempFile();
                return;
        }
    }


    public void createTempFile() {
        String prefix = mEtPrefix.getText().toString();
        String suffix = mEtSuffix.getText().toString();


        try {
            File tempFile = File.createTempFile(prefix, suffix, internalFiles.getInternalDir(InternalFiles.INTERNAL_DIR));



            mTvTempFileName.setText(tempFile.getName());
            mTvTempFullPath.setText(tempFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Unable to Create Temp File", Toast.LENGTH_LONG).show();
        }

    }

}
