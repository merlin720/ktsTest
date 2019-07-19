package com.example.ktsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    LineChart  chart = findViewById(R.id.lineChart1);

    chart.getDescription().setEnabled(false);

    chart.setDrawGridBackground(false);

    chart.setData(generateLineData());
    chart.animateX(3000);


  }

  protected LineData generateLineData() {

    ArrayList<ILineDataSet> sets = new ArrayList<>();
    LineDataSet ds1 = new LineDataSet(FileUtils.loadData(getAssets(), "test1.txt"), "Sine function");
    LineDataSet ds2 = new LineDataSet(FileUtils.loadData(getAssets(), "test1.txt"), "Cosine function");
    ds1.setLineWidth(2f);
    ds2.setLineWidth(2f);

    ds1.setDrawCircles(false);
    ds2.setDrawCircles(false);
    ds1.setDrawValues(true);
    ds2.setDrawValues(true);

    ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
    ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

    // load DataSets from files in assets folder
    sets.add(ds1);
    sets.add(ds2);

    LineData d = new LineData(sets);
    //d.setValueTypeface(tf);
    return d;
  }
}
