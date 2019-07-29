package com.example.ktsdemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.ktsdemo.base.BaseActivity;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
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

import static com.example.ktsdemo.util.CommonUtils.IP;
import static com.example.ktsdemo.util.CommonUtils.PATH;
import static com.example.ktsdemo.util.CommonUtils.UPDATE_FILE_PATH;
import static com.example.ktsdemo.util.CommonUtils.UPDATE_FILE_URL;

/**
 * @author merlin720
 * 具体的数据展示页这里是一个折线图的界面，这里是通过上一个界面点击传递了一个文件名字的参数，
 * 通过文件的路径获取文件的内容，就是一个二维的数据，加载到数轴上，画出折线图。
 */
public class MainActivity extends BaseActivity {




  LineChart chart;
  private String path;

  @Override protected int setLayoutId() {
    return R.layout.activity_main;
  }

  private void getIntentData() {
    path = PATH + getIntent().getStringExtra("path");
  }

  protected void initView() {
    titleLayout.setLeftImage(R.drawable.left_arrow);
    titleLayout.setLeftMargin(18);
    titleLayout.setMiddleTitle(getString(R.string.chart));
    titleLayout.getMiddleView().getPaint().setFakeBoldText(true);
    titleLayout.setMiddleTextSize(19);
    titleLayout.setMiddleTextColor(ContextCompat.getColor(this, R.color.col_333333));
    titleLayout.setRightTitle("设置");
    titleLayout.setRightAlpha(1);
    titleLayout.setRightTextSize(18);
    titleLayout.setRightTextColor(ContextCompat.getColor(this, R.color.col_333333));

    chart = findViewById(R.id.lineChart1);

    chart.getDescription().setEnabled(false);

    //是否可以缩放、移动、触摸
    //chart.setTouchEnabled(true);
    //chart.setDragEnabled(true);

    //chart.setPinchZoom(true);

    chart.setDrawGridBackground(false);
  }

  protected void setListener() {
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

    titleLayout.setTitleClickListener(new CustomTitleBar.TitleUpdateListener() {
      @Override public void onLeftClick() {
        finish();
      }

      @Override public void onRightClick() {
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
      }
    });
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

  /**
   * 通过获取过来的文件名字，获取文件的内容加载到图标上。
   * @param savedInstanceState
   */
  @Override
  protected void initData(@Nullable Bundle savedInstanceState) {
    getIntentData();
    HashMap<String, String> params = new HashMap<>();
    params.put("filePath", path);
    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.GET_FILE_CONTENT_URL, params, new CallBack<String>() {
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

  /**
   * 修改文件内容，传一个key 一个value，还有文件的路径。
   */
  private void update() {
    HashMap<String, String> params = new HashMap<>();
    params.put("key1", "key1");
    params.put("value", "ceshi");
    params.put("signal", ":");
    params.put("filePath", UPDATE_FILE_PATH);
    NetworkMgr1.getInstance()
        .post(String.class, UPDATE_FILE_URL, params, new CallBack<String>() {
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
}
