package com.example.android.filesbitmaps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGoInternal;
    private Button mBtnGoTempFile;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        mBtnGoInternal = (Button) findViewById(R.id.btn_go_internal);
        mBtnGoTempFile = (Button) findViewById(R.id.btn_go_temp_file);

        mBtnGoInternal.setOnClickListener(this);
        mBtnGoTempFile.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_internal:
                startActivity(new Intent(mContext, InternalActivity.class));
                return;
            case R.id.btn_go_temp_file:
                startActivity(new Intent(mContext, TempFileActivity.class));
                return;
        }
    }
}
