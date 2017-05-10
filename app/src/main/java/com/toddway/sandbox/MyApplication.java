package com.toddway.sandbox;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class MyApplication extends Application {
    public static DBTestHelper dbtestHelper;
    public static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.dbtestHelper = new DBTestHelper(getApplicationContext(), "tbdatatest", null, 1, null);
        this.context = getApplicationContext();
    }

    public static DBTestHelper getDbtestHelper() {
        return dbtestHelper;
    }
}
