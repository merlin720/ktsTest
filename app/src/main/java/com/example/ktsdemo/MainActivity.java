package com.example.ktsdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.example.ktsdemo.util.SizeUtils;
import com.example.ktsdemo.view.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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

  LineChart mLineChart;
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

    mLineChart = findViewById(R.id.lineChart1);

    //Description description = new Description();
    //description.setText("输入扭矩（N·m）");
    //description.setTextColor(Color.RED);
    ////description.setTextAlign(Paint.Align.RIGHT );
    //description.setTextSize(18);
    //description.setPosition(SizeUtils.dp2px(160), SizeUtils.dp2px(20));

    //mLineChart.setDescription(description);
    mLineChart.getDescription().setEnabled(false);

    // *********************滑动相关*************************** //
    mLineChart.setTouchEnabled(true); // 所有触摸事件,默认true
    mLineChart.setDragEnabled(true);    // 可拖动,默认true
    mLineChart.setScaleEnabled(true);   // 两个轴上的缩放,X,Y分别默认为true
    mLineChart.setScaleXEnabled(true);  // X轴上的缩放,默认true
    mLineChart.setScaleYEnabled(true);  // Y轴上的缩放,默认true
    mLineChart.setPinchZoom(true);  // X,Y轴同时缩放，false则X,Y轴单独缩放,默认false
    mLineChart.setDoubleTapToZoomEnabled(true); // 双击缩放,默认true
    mLineChart.setDragDecelerationEnabled(true);    // 抬起手指，继续滑动,默认true
    mLineChart.setDragDecelerationFrictionCoef(
        0.9f);   // 摩擦系数,[0-1]，较大值速度会缓慢下降，0，立即停止;1,无效值，并转换为0.9999.默认0.9f.

    mLineChart.getAxisRight().setEnabled(false);    // 不绘制右侧的轴线

    //是否可以缩放、移动、触摸
    //chart.setTouchEnabled(true);
    //chart.setDragEnabled(true);

    //chart.setPinchZoom(true);

    mLineChart.setDrawGridBackground(false);
    XAxis xAxis = mLineChart.getXAxis();
    YAxis yAxis = mLineChart.getAxisLeft();
    //设置y轴的位置
    //  yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    //设置xy轴线的宽度
    xAxis.setAxisLineWidth(2f);
    yAxis.setAxisLineWidth(2f);
    //设置x轴的颜色
    xAxis.setAxisLineColor(Color.BLACK);
    //设置
    xAxis.setTextSize(14);
    //xAxis.setAxisMaximum(3);
    //xAxis.setAxisMinimum(1);
    xAxis.setLabelCount(4);
    //xAxis.setXOffset(SizeUtils.dp2px(150));

    yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
    yAxis.setDrawTopYLabelEntry(true);
    yAxis.setAxisLineColor(Color.BLACK);
    yAxis.setTextSize(14);
    //最大的数在下边，最小的在上边。
    //yAxis.setInverted(true);
    yAxis.setValueFormatter(new ValueFormatter(){
      @Override public String getFormattedValue(float value) {
        return CommonUtils.getTwoPoint(String.valueOf(value)) + "N";
      }
    });
    xAxis.setValueFormatter(new ValueFormatter() {
      @Override public String getFormattedValue(float value) {
        return CommonUtils.getOnePoint(String.valueOf(value)) + "m";
      }

      @Override public String getAxisLabel(float value, AxisBase axis) {
        return super.getAxisLabel(value, axis);
      }
    });

    /***折线图例 标签 设置***/
    Legend legend = mLineChart.getLegend();
    //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
    legend.setForm(Legend.LegendForm.LINE);
    legend.setTextSize(18f);
    //显示位置 左下方
    legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    //是否绘制在图表里面
    legend.setDrawInside(true);

    //// create marker to display box when values are selected
    //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
    //
    //// Set the marker to the chart
    //mv.setChartView(mLineChart);
    //mLineChart.setMarker(mv);

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
                mLineChart.setData(generateLineData1(FileUtils.loadData(str)));
                mLineChart.animateX(3000);
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
    LineDataSet ds1 = new LineDataSet(list, "输入扭矩（N·m）");
    ds1.setLineWidth(2f);
    ds1.setDrawCircles(false);
    ds1.disableDashedLine();

    ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);

    // load DataSets from files in assets folder
    sets.add(ds1);

    LineData d = new LineData(sets);
    //d.setValueTypeface(tf);
    return d;
  }
}
