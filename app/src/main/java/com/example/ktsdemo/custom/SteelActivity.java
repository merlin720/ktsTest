package com.example.ktsdemo.custom;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ktsdemo.CustomTitleBar;
import com.example.ktsdemo.R;
import com.example.ktsdemo.base.BaseActivity;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
import com.example.ktsdemo.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.network.CallBack;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author merlin720
 * @date 2019/08/14
 * @desc 钢性
 */
public class SteelActivity extends BaseActivity {

  private TextView update_tv;
  private TextView exit;

  private TextView textView11;
  private TextView textView12;
  private TextView textView21;
  private TextView textView22;

  private EditText editText11;
  private EditText editText12;
  private EditText editText21;
  private EditText editText22;

  private TextView textView31;
  private TextView textView32;
  private TextView textView41;
  private TextView textView42;

  private EditText editText31;
  private EditText editText32;
  private EditText editText41;
  private EditText editText42;

  private TextView textView51;
  private TextView textView52;
  private TextView textView61;
  private TextView textView62;

  private EditText editText51;
  private EditText editText52;
  private EditText editText61;
  private EditText editText62;


  private TextView textView71;
  private TextView textView72;
  private TextView textView81;
  private TextView textView82;

  private EditText editText71;
  private EditText editText72;
  private EditText editText81;
  private EditText editText82;


  private TextView textView91;
  private TextView textView92;
  private TextView textView101;
  private TextView textView102;

  private EditText editText91;
  private EditText editText92;
  private EditText editText101;
  private EditText editText102;


  @Override protected int setLayoutId() {
    return R.layout.activity_steel;
  }

  @Override protected void initView() {
    super.initView();
    titleLayout.setLeftImage(R.drawable.left_arrow);
    titleLayout.setLeftMargin(18);
    titleLayout.setMiddleTitle(getString(R.string.steel_setting));
    titleLayout.getMiddleView().getPaint().setFakeBoldText(true);
    titleLayout.setMiddleTextSize(19);
    titleLayout.setMiddleTextColor(ContextCompat.getColor(this, R.color.col_333333));
    titleLayout.getRigthTv().setVisibility(View.GONE);
    update_tv = findViewById(R.id.update_tv);
    exit = findViewById(R.id.exit);
    partOne();
    partTwo();
    partThree();
    partFour();

  }

  private void partOne() {
    LinearLayout linearLayout1 = findViewById(R.id.process_layout_1);
    textView11 = linearLayout1.findViewById(R.id.process_item_1_tv);
    textView11.setText("转速（r/min）");
    textView12 = linearLayout1.findViewById(R.id.process_item_1_tv1);
    textView12.setText("加速度");
    editText11 = linearLayout1.findViewById(R.id.process_item_1_ed);
    editText12 = linearLayout1.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout2 = findViewById(R.id.process_layout_2);
    textView21 = linearLayout2.findViewById(R.id.process_item_1_tv);
    textView21.setText("换向角度（°）");
    textView22 = linearLayout2.findViewById(R.id.process_item_1_tv1);
    textView22.setText("换向扭矩（N·m）");

    editText21 = linearLayout2.findViewById(R.id.process_item_1_ed);
    editText22 = linearLayout2.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout3 = findViewById(R.id.process_layout_3);
    textView31 = linearLayout3.findViewById(R.id.process_item_1_tv);
    textView31.setText("计算角度上限（°）");
    textView32 = linearLayout3.findViewById(R.id.process_item_1_tv1);
    textView32.setText("计算角度下限（°）");
    editText31 = linearLayout3.findViewById(R.id.process_item_1_ed);
    editText32 = linearLayout3.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout4 = findViewById(R.id.process_layout_4);
    textView41 = linearLayout4.findViewById(R.id.process_item_1_tv);
    textView41.setText("采集率（Hz）");
    textView42 = linearLayout4.findViewById(R.id.process_item_1_tv1);
    textView42.setText("曲线名称");

    editText41 = linearLayout4.findViewById(R.id.process_item_1_ed);
    editText42 = linearLayout4.findViewById(R.id.process_item_1_ed1);
  }

  private void partTwo() {
    LinearLayout linearLayout1 = findViewById(R.id.process_layout_5);
    textView51 = linearLayout1.findViewById(R.id.process_item_1_tv);
    textView51.setText("x轴名称");
    textView52 = linearLayout1.findViewById(R.id.process_item_1_tv1);
    textView52.setText("最小值");
    editText51 = linearLayout1.findViewById(R.id.process_item_1_ed);
    editText52 = linearLayout1.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout2 = findViewById(R.id.process_layout_6);
    textView61 = linearLayout2.findViewById(R.id.process_item_1_tv);
    textView61.setText("跨度");
    textView62 = linearLayout2.findViewById(R.id.process_item_1_tv1);
    textView62.setText("颜色");

    editText61 = linearLayout2.findViewById(R.id.process_item_1_ed);
    editText62 = linearLayout2.findViewById(R.id.process_item_1_ed1);
  }

  private void partThree(){
    LinearLayout linearLayout1 = findViewById(R.id.process_layout_7);
    textView71 = linearLayout1.findViewById(R.id.process_item_1_tv);
    textView71.setText("y轴名称");
    textView72 = linearLayout1.findViewById(R.id.process_item_1_tv1);
    textView72.setText("最小值");
    editText71 = linearLayout1.findViewById(R.id.process_item_1_ed);
    editText72 = linearLayout1.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout2 = findViewById(R.id.process_layout_8);
    textView81 = linearLayout2.findViewById(R.id.process_item_1_tv);
    textView81.setText("跨度");
    textView82 = linearLayout2.findViewById(R.id.process_item_1_tv1);
    textView82.setText("颜色");

    editText81 = linearLayout2.findViewById(R.id.process_item_1_ed);
    editText82 = linearLayout2.findViewById(R.id.process_item_1_ed1);
  }

  private void partFour(){
    LinearLayout linearLayout1 = findViewById(R.id.process_layout_9);
    textView91 = linearLayout1.findViewById(R.id.process_item_1_tv);
    textView91.setText("上限");
    textView92 = linearLayout1.findViewById(R.id.process_item_1_tv1);
    textView92.setText("下限");
    editText91 = linearLayout1.findViewById(R.id.process_item_1_ed);
    editText92 = linearLayout1.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout2 = findViewById(R.id.process_layout_10);
    textView101 = linearLayout2.findViewById(R.id.process_item_1_tv);
    textView101.setText("上限");
    textView102 = linearLayout2.findViewById(R.id.process_item_1_tv1);
    textView102.setText("下限");
    editText101 = linearLayout2.findViewById(R.id.process_item_1_ed);
    editText102 = linearLayout2.findViewById(R.id.process_item_1_ed1);


  }
  @Override protected void setListener() {
    titleLayout.setTitleClickListener(new CustomTitleBar.TitleUpdateListener() {
      @Override public void onLeftClick() {
        finish();
      }
    });
    RxView.clicks(exit)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            finish();
          }
        });
    RxView.clicks(update_tv)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            save();
          }
        });
  }

  @Override protected void initData(@Nullable Bundle savedInstanceState) {
    loadData();
  }

  private String currentPath;

  private void loadData() {
    HashMap<String, String> params = new HashMap<>();
    params.put("fileName", "im.ini");
    params.put("basePath", CommonUtils.NEW_MODEL_SETTING_PATH);
    params.put("filePath", CommonUtils.CURRENT_SYSTEM_PATH);
    params.put("key", "mPriName");

    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.GET_PROCESS_SETTING, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              String str = jsonObject.getString("data");
              if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                boolean isnull = Objects.nonNull(str);
              }
              if ("0".equals(code)) {
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Gson gson = new Gson();
                HashMap<String, String> map = gson.fromJson(str, type);
                editText11.setText(map.get("numRSpeed"));
                editText12.setText(map.get("numMovAcc"));
                editText21.setText(map.get("numAngLim"));
                editText22.setText(map.get("numCDCondition"));
                editText31.setText(map.get("numOKUp"));
                editText32.setText(map.get("numOKDown"));
                editText41.setText(map.get("numgather_rate"));
                editText42.setText(map.get("strLineName"));

                editText51.setText(map.get("strXName"));
                editText52.setText(map.get("numXMin"));
                editText61.setText(map.get("numXRange"));
                editText62.setText(map.get("numXRange"));

                editText71.setText(map.get("strYName"));
                editText72.setText(map.get("numYMin"));
                editText81.setText(map.get("numYRange"));
                editText82.setText(map.get("numYRange"));

                String [] split = map.get("AITorqueCWMax").split("<");
                editText91.setText(split[0]);
                editText92.setText(split[1]);
                String [] split1 = map.get("AITorqueACWMax").split("<");
                editText101.setText(split1[0]);
                editText102.setText(split1[1]);



                currentPath = map.get("currentPath");
                LogUtils.e(map);
              } else {
                Toast.makeText(SteelActivity.this, message, Toast.LENGTH_LONG)
                    .show();
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
   * 当前修改的内容保存到文件上
   */
  private void save() {
    HashMap<String, String> test = new HashMap<>();
    test.put("numRSpeed", editText11.getText().toString());
    test.put("numMovAcc", editText12.getText().toString());
    test.put("numAngLim", editText21.getText().toString());
    test.put("numCDCondition", editText22.getText().toString());
    test.put("numOKUp", editText31.getText().toString());
    test.put("numOKDown", editText32.getText().toString());
    test.put("numgather_rate", editText41.getText().toString());
    test.put("strLineName", editText42.getText().toString());

    test.put("strXName", editText51.getText().toString());
    test.put("numXMin", editText52.getText().toString());
    test.put("numXRange", editText61.getText().toString());
    test.put("numXRange", editText62.getText().toString());

    test.put("strYName", editText71.getText().toString());
    test.put("numYMin", editText72.getText().toString());
    test.put("numYRange", editText81.getText().toString());
    test.put("numYRange", editText82.getText().toString());

    test.put("AITorqueCWMax", editText91.getText().toString()+"<"+editText92.getText().toString());
    test.put("AITorqueACWMax", editText101.getText().toString()+"<"+editText102.getText().toString());


    Gson gson = new Gson();
    Type type = new TypeToken<HashMap<String, String>>() {
    }.getType();
    HashMap<String, String> params = new HashMap<>();
    params.put("map", gson.toJson(test, type));
    params.put("filePath", currentPath);

    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.PROCCESS_UPDATE_CONTENT, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              String str = jsonObject.getString("data");
              if ("0".equals(code)) {
                //Type type = new TypeToken<HashMap<String, String>>() {
                //}.getType();
                //Gson gson = new Gson();
                //HashMap<String, String> map = gson.fromJson(str, type);
                //editText11.setText(map.get("anglim"));
                //editText21.setText(map.get("torchk"));
                //LogUtils.e(map);
                Toast.makeText(SteelActivity.this, message, Toast.LENGTH_LONG)
                    .show();
              } else {
                Toast.makeText(SteelActivity.this, message, Toast.LENGTH_LONG)
                    .show();
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
}
