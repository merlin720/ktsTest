package com.example.ktsdemo.util;

import com.example.ktsdemo.BuildConfig;
import java.text.DecimalFormat;

/**
 * @author merlin720
 * @date 2019-07-23
 * @mail zy44638@gmail.com
 * @description
 */
public class CommonUtils {
  public static final boolean isDebug = !BuildConfig.ISRELEASE;
  public static final String IP = "http://192.168.1.234";
  public static final String PATH = "/Users/merlin720/kts/document/";
  public static final String SETTING_PATH = "/Users/merlin720/kts/document/config/testcfg/";

  public static final String GET_FILE_CONTENT_URL = IP + ":8080/test/queryFileContent.do";
  public static final String UPDATE_FILE_URL = IP + ":8080/test/updateFileContent.do";
  public static final String UPDATE_FILE_PATH = "/Users/merlin720/kts/document/update.txt";
  //public static final String PATH = "E:\\data_R\\jjj\\niuzhuanpilao.xml\\";


  public static final String START_STOP_PATH = "";

  public static String getTwoPoint(String num) {
    try {
      if (null == num || "".equals(num) || "null".equals(num)) {
        return "0.00";
      }
      //定义数据格式
      DecimalFormat myformat = new DecimalFormat("#####0.00");
      double a = Double.parseDouble(num);
      return myformat.format(a);
    }catch (Exception e){
      e.printStackTrace();
    }
    return "";
  }

  public static String getOnePoint(String num) {
    try {
      if (null == num || "".equals(num) || "null".equals(num)) {
        return "0.00";
      }
      //定义数据格式
      DecimalFormat myformat = new DecimalFormat("#####0.0");
      double a = Double.parseDouble(num);
      return myformat.format(a);
    }catch (Exception e){
      e.printStackTrace();
    }
    return "";
  }
}
