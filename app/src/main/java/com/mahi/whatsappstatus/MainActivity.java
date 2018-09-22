package com.mahi.whatsappstatus;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    private RecyclerView mRecyclerViewMediaList;
    private LinearLayoutManager mLinearLayoutManager;
    public static final String TAG = "Home";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerViewMediaList = (RecyclerView) findViewById(R.id.recyclerViewMedia);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewMediaList.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewMediaList.setAdapter(new WhtaspAdatper(this,getListFiles(new File(Environment.getExternalStorageDirectory().getPath().toString()+WHATSAPP_STATUSES_LOCATION))));

        //RecyclerViewMediaAdapter recyclerViewMediaAdapter = new RecyclerViewMediaAdapter(this.getListFiles(new File(Environment.getExternalStorageDirectory().getPath().toString()+WHATSAPP_STATUSES_LOCATION)), MainActivity.this);
      //  mRecyclerViewMediaList.setAdapter(recyclerViewMediaAdapter);
    }

    /**
     * get all the files in specified directory
     *
     * @param parentDir
     * @return
     */
    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {

                if (file.getName().endsWith(".jpg") ||
                        file.getName().endsWith(".gif") ||
                        file.getName().endsWith(".mp4")) {
                    if (!inFiles.contains(file))
                        inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


}