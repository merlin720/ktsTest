package com.example.ktsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.ktsdemo.util.FileUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.merlin.network.CallBack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  private static final String IP = "http://192.168.1.219";
  private static final String path = "/Users/merlin720/kts/document/123.dat";
  private static final String FilePath = "/Users/merlin720/kts/document";
  private static final String path1 = "E:\\data_R\\jjj\\niuzhuanpilao.xml\\test.txt";
  private static final String url = IP + ":8080/test/queryFileContent.do";
  private static final String getFiles = IP + ":8080/test/queryFiles.do";
  private static final String updateUrl = IP + ":8080/test/updateFileContent.do";
  LineChart chart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    NetworkMgr1.getInstance().init(this, true);
    chart = findViewById(R.id.lineChart1);

    chart.getDescription().setEnabled(false);
    //是否可以缩放、移动、触摸
    chart.setTouchEnabled(true);
    chart.setDragEnabled(true);

    //不能让缩放，不然有bug，所以接口也没实现
    chart.setScaleEnabled(false);
    chart.setPinchZoom(true);

    chart.setDrawGridBackground(false);

    findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
      }
    });
    findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        update();
      }
    });
    findViewById(R.id.get_path).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getFiles();
      }
    });
    init();

    rxTest();
  }

  protected LineData generateLineData() {

    ArrayList<ILineDataSet> sets = new ArrayList<>();
    LineDataSet ds1 = new LineDataSet(FileUtils.loadData(getAssets(), "test.txt"), "Sine function");
    LineDataSet ds2 =
        new LineDataSet(FileUtils.loadData(getAssets(), "test.txt"), "Cosine function");
    ds1.setLineWidth(2f);
    ds2.setLineWidth(2f);

    ds1.setDrawCircles(false);
    ds2.setDrawCircles(false);

    ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
    ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

    // load DataSets from files in assets folder
    sets.add(ds1);
    sets.add(ds2);

    LineData d = new LineData(sets);
    //d.setValueTypeface(tf);
    return d;
  }

  private void init() {

    HashMap<String, String> params = new HashMap<>();
    params.put("filePath", path);
    NetworkMgr1.getInstance()
        .post(String.class, url, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              String str = jsonObject.getString("data");
              if ("0".equals(code)) {

                chart.setData(generateLineData1(FileUtils.loadData(str)));
                chart.animateX(3000);
              } else {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(Exception exception) {
            exception.printStackTrace();
          }
        });
  }

  private void update() {
    HashMap<String, String> params = new HashMap<>();
    params.put("key1", "key1");
    params.put("value", "ceshi");
    params.put("filePath", "/Users/merlin720/kts/document/update.txt");
    NetworkMgr1.getInstance()
        .post(String.class, updateUrl, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              if ("0".equals(code)) {

              } else {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(Exception exception) {
            exception.printStackTrace();
          }
        });
  }

  private void getFiles() {
    HashMap<String, String> params = new HashMap<>();
    params.put("filePath", FilePath);
    NetworkMgr1.getInstance()
        .post(String.class, getFiles, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              String str = jsonObject.getString("data");
              if ("0".equals(code)) {


              } else {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onFailure(Exception exception) {
            exception.printStackTrace();
          }
        });
  }

  protected LineData generateLineData1(List<Entry> list) {

    ArrayList<ILineDataSet> sets = new ArrayList<>();
    LineDataSet ds1 = new LineDataSet(list, "Sine function");
    ds1.setLineWidth(2f);

    ds1.setDrawCircles(false);

    ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);

    // load DataSets from files in assets folder
    sets.add(ds1);

    LineData d = new LineData(sets);
    //d.setValueTypeface(tf);
    return d;
  }

  private void rxTest() {
    //Flowable.range(1, 10)
    //    .observeOn(Schedulers.computation())
    //    .map(v -> v * v)
    //    .blockingSubscribe(System.out::println);
  }
}
