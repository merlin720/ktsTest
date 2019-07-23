package com.example.ktsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.ktsdemo.util.FileUtils;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

import static lecho.lib.hellocharts.util.ChartUtils.COLOR_BLUE;
import static lecho.lib.hellocharts.util.ChartUtils.COLOR_GREEN;
import static lecho.lib.hellocharts.util.ChartUtils.COLOR_ORANGE;
import static lecho.lib.hellocharts.util.ChartUtils.COLOR_RED;
import static lecho.lib.hellocharts.util.ChartUtils.COLOR_VIOLET;

public class SecondActivity extends AppCompatActivity {
  private boolean hasAxes = true;
  private boolean hasAxesNames = true;
  private boolean hasLines = true;
  private boolean hasPoints = true;
  private ValueShape shape = ValueShape.CIRCLE;
  private boolean isFilled = false;
  private boolean hasLabels = false;
  private boolean isCubic = false;
  private boolean hasLabelForSelected = false;
  public static final int[] COLORS = new int[]{COLOR_BLUE, COLOR_VIOLET, COLOR_GREEN, COLOR_ORANGE, COLOR_RED};
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    LineChartView chart = findViewById(R.id.chart);



    //In most cased you can call data model methods in builder-pattern-like manner.
    Line line = new Line(FileUtils.load1Data(getAssets(), "test1.txt")).setColor(Color.BLUE).setCubic(true);
    line.setPointRadius(1);
    line.setColor(COLORS[1]);
    line.setShape(shape);
    line.setCubic(isCubic);
    line.setFilled(isFilled);
    line.setHasLabels(hasLabels);
    line.setHasLabelsOnlyForSelected(hasLabelForSelected);
    line.setHasLines(hasLines);
    line.setHasPoints(hasPoints);
    List<Line> lines = new ArrayList<Line>();
    lines.add(line);

    LineChartData data = new LineChartData();
    data.setLines(lines);
    if (hasAxes) {
      Axis axisX = new Axis();
      Axis axisY = new Axis().setHasLines(true);
      if (hasAxesNames) {
        axisX.setName("Axis X");
        axisY.setName("Axis Y");
      }
      data.setAxisXBottom(axisX);
      data.setAxisYLeft(axisY);
    } else {
      data.setAxisXBottom(null);
      data.setAxisYLeft(null);
    }

    data.setBaseValue(Float.NEGATIVE_INFINITY);
    chart.setLineChartData(data);


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
