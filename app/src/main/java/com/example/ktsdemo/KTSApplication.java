package com.example.ktsdemo;

import android.app.Application;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
import com.example.ktsdemo.util.LogUtils;
import com.example.ktsdemo.util.Utils;

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
    Utils.init(this);
    initLog();//log 初始化
    LogUtils.e("ssssssssssssssssssss");
  }
  /**
   * Log打印初始
   */
  public void initLog() {
    LogUtils.Config config = LogUtils.getConfig()
        .setLogSwitch(CommonUtils.isDebug)// 设置log总开关，包括输出到控制台和文件，默认开
        .setConsoleSwitch(CommonUtils.isDebug)// 设置是否输出到控制台开关，默认开
        .setGlobalTag(null)// 设置log全局标签，默认为空
        // 当全局标签不为空时，我们输出的log全部为该tag，
        // 为空时，如果传入的tag为空那就显示类名，否则显示tag
        .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
        .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
        .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
        .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
        .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
        .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
        .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
        .setStackDeep(1);// log栈深度，默认为1
    LogUtils.d(config.toString());
  }
}
