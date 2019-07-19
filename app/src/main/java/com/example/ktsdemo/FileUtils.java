package com.example.ktsdemo;

import android.content.res.AssetManager;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author merlin720
 * @date 2019-07-13
 * @mail zy44638@gmail.com
 * @description
 */
public class FileUtils {

    public static List<Entry> loadData(AssetManager am , String path){
      List<Entry> entries = new ArrayList<>();
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new InputStreamReader(am.open(path),"UTF-8"));
        String line = reader.readLine();
        while (null != line){
          String s = line.replace("\t", "#");
          String[] split = s.split("#");
          //Log.e("merlin", split.length+"" + split[0]);
          entries.add(new Entry(Float.parseFloat(split[0]),Float.parseFloat(split[1])));
          line  = reader.readLine();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException e) {
            Log.e("merlin", e.toString());
          }
        }
      }
      return  entries;
    }

  public static List<Entry> loadData (String str){
    List<Entry> entries = new ArrayList<>();
    try {

      Type type = new TypeToken<ArrayList<String>>() {}.getType();
      Gson gson = new Gson();

      Log.e("merlin", str);
      List<String> list = gson.fromJson(str, type);
      for (String line : list) {
        String s = line.replace("\t", "#");
        String[] split = s.split("#");
        entries.add(new Entry(Float.parseFloat(split[0]),Float.parseFloat(split[1])));
      }
    } catch ( Exception e) {
      e.printStackTrace();
    }

    return entries;
  }
}
