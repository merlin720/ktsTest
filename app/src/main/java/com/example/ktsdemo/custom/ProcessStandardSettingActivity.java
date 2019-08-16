package com.example.ktsdemo.custom;

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
import com.example.ktsdemo.bean.ModelSettingBean;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
import com.example.ktsdemo.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.network.CallBack;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author merlin720
 * @date 2019/08/14
 * @desc 标准与过程设置界面
 */
public class ProcessStandardSettingActivity extends BaseActivity {

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


  @Override protected int setLayoutId() {
    return R.layout.activity_process_standard_set;
  }

  @Override protected void initView() {
    super.initView();
    titleLayout.setLeftImage(R.drawable.left_arrow);
    titleLayout.setLeftMargin(18);
    titleLayout.setMiddleTitle(getString(R.string.process_setting));
    titleLayout.getMiddleView().getPaint().setFakeBoldText(true);
    titleLayout.setMiddleTextSize(19);
    titleLayout.setMiddleTextColor(ContextCompat.getColor(this, R.color.col_333333));
    titleLayout.getRigthTv().setVisibility(View.GONE);
    update_tv = findViewById(R.id.update_tv);
    exit = findViewById(R.id.exit);
    partOne();
    partTwo();
  }

  private void partOne() {
    LinearLayout linearLayout1 = findViewById(R.id.process_layout_1);
    textView11 = linearLayout1.findViewById(R.id.process_item_1_tv);
    textView11.setText("输入转数（rpm）");
    textView12 = linearLayout1.findViewById(R.id.process_item_1_tv1);
    textView12.setText("扭矩限制（N.m）");
    editText11 = linearLayout1.findViewById(R.id.process_item_1_ed);
    editText12 = linearLayout1.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout2 = findViewById(R.id.process_layout_2);
    textView21 = linearLayout2.findViewById(R.id.process_item_1_tv);
    textView21.setText("换向扭矩（N.m）");
    textView22 = linearLayout2.findViewById(R.id.process_item_1_tv1);
    textView22.setText("系统流量（L/min）");

    editText21 = linearLayout2.findViewById(R.id.process_item_1_ed);
    editText22 = linearLayout2.findViewById(R.id.process_item_1_ed1);
  }

  private void partTwo(){
    LinearLayout linearLayout1 = findViewById(R.id.process_layout_3);
    textView31 = linearLayout1.findViewById(R.id.process_item_1_tv);
    textView31.setText("输入转数（rpm）");
    textView32 = linearLayout1.findViewById(R.id.process_item_1_tv1);
    textView32.setText("对冲油压1（MPa）");
    editText31 = linearLayout1.findViewById(R.id.process_item_1_ed);
    editText32 = linearLayout1.findViewById(R.id.process_item_1_ed1);

    LinearLayout linearLayout2 = findViewById(R.id.process_layout_4);
    textView41 = linearLayout2.findViewById(R.id.process_item_1_tv);
    textView41.setText("对中角度补偿（°）");
    textView42 = linearLayout2.findViewById(R.id.process_item_1_tv1);
    textView42.setText("对冲油压2（MPa）");

    editText41 = linearLayout2.findViewById(R.id.process_item_1_ed);
    editText42 = linearLayout2.findViewById(R.id.process_item_1_ed1);
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
    params.put("fileName", "process.ini");
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
              if ("0".equals(code)) {
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Gson gson = new Gson();
                HashMap<String, String> map = gson.fromJson(str, type);
                editText11.setText(map.get("movspd"));
                editText12.setText(map.get("torlim"));
                editText21.setText(map.get("torchk"));
                editText31.setText(map.get("nl_movspd"));
                editText32.setText(map.get("SymOil1"));
                editText42.setText(map.get("SymOil2"));
                currentPath = map.get("currentPath");
                LogUtils.e(map);
              } else {
                Toast.makeText(ProcessStandardSettingActivity.this, message, Toast.LENGTH_LONG)
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
  private void save(){
    HashMap<String,String> test = new HashMap<>();
    test.put("movspd",editText11.getText().toString());
    test.put("torchk",editText21.getText().toString());
    test.put("torlim",editText12.getText().toString());
    test.put("nl_movspd",editText31.getText().toString());
    test.put("SymOil1",editText32.getText().toString());
    test.put("SymOil2",editText42.getText().toString());

    HashMap<String, String> params = new HashMap<>();
    params.put("map", test.toString());
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
              } else {
                Toast.makeText(ProcessStandardSettingActivity.this, message, Toast.LENGTH_LONG)
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
