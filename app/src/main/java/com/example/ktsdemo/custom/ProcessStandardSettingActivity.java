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
import com.merlin.network.CallBack;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author merlin720
 * @date 2019/08/14
 * @desc 标准与过程设置界面
 */
public class ProcessStandardSettingActivity extends BaseActivity {

  private TextView textView11;
  private TextView textView12;
  private TextView textView21;
  private TextView textView22;

  private EditText editText11;
  private EditText editText12;
  private EditText editText21;
  private EditText editText22;

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

    partOne();
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

  @Override protected void setListener() {
    titleLayout.setTitleClickListener(new CustomTitleBar.TitleUpdateListener() {
      @Override public void onLeftClick() {
        finish();
      }
    });
  }

  @Override protected void initData(@Nullable Bundle savedInstanceState) {
    loadData();
  }

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
                editText11.setText(map.get("anglim"));
                editText21.setText(map.get("torchk"));
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
}
