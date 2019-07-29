package com.example.ktsdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ktsdemo.adapter.KTSListAdapter;
import com.example.ktsdemo.base.BaseActivity;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
import com.example.ktsdemo.util.SizeUtils;
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
 * 数据文件的列表页
 */
public class SettingContentListActivity extends BaseActivity {

  private String path;

  private RecyclerView mRecyclerView;

  private KTSListAdapter adapter;

  private List<String> mContentData;

  @Override protected int setLayoutId() {
    return R.layout.activity_setting;
  }

  @Override
  protected void initView() {
    titleLayout.setLeftImage(R.drawable.left_arrow);
    titleLayout.setLeftMargin(18);
    titleLayout.setMiddleTitle(getString(R.string.setting_detail));
    titleLayout.getMiddleView().getPaint().setFakeBoldText(true);
    titleLayout.setMiddleTextSize(19);
    titleLayout.setMiddleTextColor(ContextCompat.getColor(this, R.color.col_333333));
    titleLayout.getRigthTv().setVisibility(View.GONE);

    mRecyclerView = findViewById(R.id.setting_recycler_view);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    adapter = new KTSListAdapter();
    mRecyclerView.setAdapter(adapter);
  }

  private void getIntentData() {
    mContentData = new ArrayList<>();
    path = getIntent().getStringExtra("path");
  }

  /**
   * 通过获取过来的文件名字，获取文件的内容加载到图标上。
   */
  @Override
  protected void initData(@Nullable Bundle savedInstanceState) {
    getIntentData();
    initData();
  }

  private void initData() {
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
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                Gson gson = new Gson();
                List<String> list = gson.fromJson(str, type);
                mContentData.clear();
                mContentData.addAll(list);
                adapter.setNewData(list);
              } else {
                Toast.makeText(SettingContentListActivity.this, message, Toast.LENGTH_LONG).show();
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

  @Override
  protected void setListener() {
    titleLayout.setTitleClickListener(new CustomTitleBar.TitleUpdateListener() {
      @Override public void onLeftClick() {
        finish();
      }
    });
    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        createDialog(mContentData.get(position));
      }
    });
  }

  private AlertDialog alertDialog;

  private void createDialog(String str) {
    View mLmCountView = LayoutInflater.from(this).inflate(R.layout.setting_edit_dialog, null);
    String[] split = str.split("=");
    TextView key = mLmCountView.findViewById(R.id.set_dialog_text);
    EditText value = mLmCountView.findViewById(R.id.set_dialog_edit);

    if (split.length == 2) {
      key.setText(split[0]);
      value.setText(split[1]);
      value.setSelection(split[1].length());
    }

    Button button = mLmCountView.findViewById(R.id.set_dialog_button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String keyStr = key.getText().toString();
        String valueStr = value.getText().toString();
        update(keyStr, valueStr);
        hideDialog();
      }
    });
    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog_Fullscreen);
    builder.setCancelable(true);
    builder.setView(mLmCountView);
    alertDialog = builder.create();

    if (null != alertDialog && !alertDialog.isShowing()) {
      alertDialog.show();
      Window window = alertDialog.getWindow();
      window.setDimAmount(0.4f);
      window.setGravity(Gravity.CENTER);
      WindowManager.LayoutParams lp = window.getAttributes();
      lp.width = SizeUtils.getDisplayWidth(this);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
  }

  public void hideDialog() {
    if (null != alertDialog && alertDialog.isShowing()) {
      alertDialog.dismiss();
    }
  }

  private void update(String key, String value) {
    HashMap<String, String> params = new HashMap<>();
    params.put("key1", key);
    params.put("value", value);
    params.put("signal", "=");
    params.put("filePath", path);
    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.UPDATE_FILE_URL, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              if ("0".equals(code)) {
                initData();
                Toast.makeText(SettingContentListActivity.this, message, Toast.LENGTH_LONG).show();
              } else {
                Toast.makeText(SettingContentListActivity.this, message, Toast.LENGTH_LONG).show();
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
