package com.example.ktsdemo.util;

import android.content.res.AssetManager;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.PointValue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author merlin720
 * @date 2019-07-13
 * @mail zy44638@gmail.com
 * @description
 */
public class FileUtils {
  /**
   * 项目里assets文件里的内容
   *
   * @param am manager
   * @param path 路径 就是名字
   * @return 返回list
   */
  public static List<PointValue> load1Data(AssetManager am, String path) {
    List<PointValue> entries = new ArrayList<>();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(am.open(path), "UTF-8"));
      String line = reader.readLine();
      while (null != line) {
        String s = line.replace("\t", "#");
        String[] split = s.split("#");
        //Log.e("merlin", split.length+"" + split[0]);
        entries.add(new PointValue(Float.parseFloat(split[0]), Float.parseFloat(split[1])));
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          Log.e("merlin", e.toString());
        }
      }
    }
    return entries;
  }

  public static List<Entry> loadData(AssetManager am, String path) {
    List<Entry> entries = new ArrayList<>();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(am.open(path), "UTF-8"));
      String line = reader.readLine();
      while (null != line) {
        String s = line.replace("\t", "#");
        String[] split = s.split("#");
        //Log.e("merlin", split.length+"" + split[0]);
        entries.add(new Entry(Float.parseFloat(split[0]), Float.parseFloat(split[1])));
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          Log.e("merlin", e.toString());
        }
      }
    }
    return entries;
  }

  /**
   * 把返回结果转换成list
   */
  public static List<Entry> loadData(String str) {
    List<Entry> entries = new ArrayList<>();
    try {

      Type type = new TypeToken<ArrayList<String>>() {
      }.getType();
      Gson gson = new Gson();

      Log.e("merlin", str);
      List<String> list = gson.fromJson(str, type);
      for (String line : list) {
        String s = line.replace("\t", "#");
        String[] split = s.split("#");
        if (null != split[0] && null != split[1]) {
          entries.add(new Entry(Float.parseFloat(split[0]), Float.parseFloat(split[1])));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return entries;
  }

  /**
   * 把返回结果转换成list
   */
  public static List<Entry> loadData(List<String> list) {
    List<Entry> entries = new ArrayList<>();
    try {



      for (String line : list) {
        String s = line.replace("\t", "#");
        String[] split = s.split("#");
        if (null != split[0] && null != split[1]) {
          entries.add(new Entry(Float.parseFloat(split[0]), Float.parseFloat(split[1])));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return entries;
  }

  public static List<String> getFilesInPath(String path) {
    List<String> list = new ArrayList<>();
    File file = new File(path);
    File[] array = file.listFiles();
    for (int i = 0; i < array.length; i++) {

      if (array[i].isFile()) {
        String name = array[i].getName();
        if (name.endsWith(".dat")) {
          list.add(array[i].getName());
        }
        // only take file name   
        System.out.println("^^^^^" + array[i].getName());
        // take file path and name   
        System.out.println("#####" + array[i]);
        // take file path and name   
        System.out.println("*****" + array[i].getPath());
      } else if (array[i].isDirectory()){
        getFilesInPath(array[i].getPath());
      }
    }
    return list;
  }
}
