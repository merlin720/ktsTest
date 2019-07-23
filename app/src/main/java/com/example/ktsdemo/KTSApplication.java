package com.example.ktsdemo;

import android.app.Application;
import com.example.ktsdemo.net.NetworkMgr1;

/**
 * @author merlin720
 * @date 2019-07-23
 * @mail zy44638@gmail.com
 * @description
 */
public class KTSApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    NetworkMgr1.getInstance().init(this, true);
  }
}
