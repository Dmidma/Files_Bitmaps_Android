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
    private Button mBtnGoCacheInternal;
    private Button mBtnGoExternal;
    private Button mBtnGoBitmap;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        mBtnGoInternal = (Button) findViewById(R.id.btn_go_internal);
        mBtnGoTempFile = (Button) findViewById(R.id.btn_go_temp_file);
        mBtnGoCacheInternal = (Button) findViewById(R.id.btn_go_cache_internal);
        mBtnGoExternal = (Button) findViewById(R.id.btn_go_external);
        mBtnGoBitmap = (Button) findViewById(R.id.btn_go_bitmap);

        mBtnGoInternal.setOnClickListener(this);
        mBtnGoTempFile.setOnClickListener(this);
        mBtnGoCacheInternal.setOnClickListener(this);
        mBtnGoExternal.setOnClickListener(this);
        mBtnGoBitmap.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_internal:
                startActivity(new Intent(mContext, InternalActivity.class));
                break;
            case R.id.btn_go_temp_file:
                startActivity(new Intent(mContext, TempFileActivity.class));
                break;
            case R.id.btn_go_cache_internal:
                startActivity(new Intent(mContext, InternalCacheActivity.class));
                break;
            case R.id.btn_go_external:
                startActivity(new Intent(mContext, ExternalActivity.class));
                break;
            case R.id.btn_go_bitmap:
                startActivity(new Intent(mContext, BitmapActivity.class));
                break;
        }
    }
}
