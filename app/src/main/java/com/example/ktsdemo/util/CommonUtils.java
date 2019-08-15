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
  //public static final String IP = "http://192.168.1.83";
  public static final String IP = "http://192.168.1.234";
  public static final String PATH = "/Users/merlin720/kts/document/";
  public static final String SETTING_PATH = "/Users/merlin720/kts/document/config/testcfg/";

  public static final String UPDATE_FILE_PATH = "/Users/merlin720/kts/document/update.txt";
  //public static final String PATH = "E:\\data_R\\jjj\\niuzhuanpilao.xml\\";

  //配置开始关闭的文件路径
  //public static final String START_STOP_PATH = "C:\\keyen\\zsprog\\config\\syspricfg\\rio.ini";
  public static final String START_STOP_PATH = "/Users/merlin720/kts/document/openclose.txt";
  //数据存储的文件路径
  //public static final String NEW_DATA_FILE_PATH = "E:\\runt\\type16\\工位1-模拟路谱试验.xml";
  public static final String NEW_DATA_FILE_PATH = "/Users/merlin720/kts/document/";
  public static final String NEW_SETTING_PATH = "/Users/merlin720/kts/document/ktscfg/";
  public static final String NEW_MODEL_SETTING_PATH = "/Users/merlin720/kts/document/ktscfg/testcfg/";

  public static final String CURRENT_SYSTEM_PATH = CommonUtils.NEW_SETTING_PATH + "syscfg/varsave.ini";

  public static final String GET_FILE_CONTENT_URL = IP + ":8080/test/queryFileContent.do";
  public static final String UPDATE_FILE_URL = IP + ":8080/test/updateFileContent.do";
  public static final String getFilesInPathUrl = IP + ":8080/test/queryFilesInPath.do";

  public static final String GET_CURRENT_SYSTEM = IP + ":8080/setting/getCurrentSystem.do";
  //删除系统设置文件夹
  public static final String SETTING_DELETE_FILE = IP + ":8080/setting/deleteFile.do";
  public static final String SETTING_ADD_FILE = IP + ":8080/setting/addFile.do";

  public static final String GET_PROCESS_SETTING = IP + ":8080/setting/getProcessSeting.do ";

  public static String getTwoPoint(String num) {
    try {
      if (null == num || "".equals(num) || "null".equals(num)) {
        return "0.00";
      }
      //定义数据格式
      DecimalFormat myformat = new DecimalFormat("#####0.00");
      double a = Double.parseDouble(num);
      return myformat.format(a);
    } catch (Exception e) {
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
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}
