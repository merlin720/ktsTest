package com.example.ktsdemo.custom;

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
import com.example.ktsdemo.CustomTitleBar;
import com.example.ktsdemo.FileListActivity;
import com.example.ktsdemo.R;

import com.example.ktsdemo.SettingContentListActivity;
import com.example.ktsdemo.adapter.KTSNewListAdapter;
import com.example.ktsdemo.base.BaseActivity;
import com.example.ktsdemo.base.Test;
import com.example.ktsdemo.bean.ModelSettingBean;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
import com.example.ktsdemo.util.SizeUtils;
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

import static com.example.ktsdemo.util.CommonUtils.IP;
import static com.example.ktsdemo.util.CommonUtils.SETTING_PATH;

/**
 * @author merlin
 * 设置页列表界面
 */
public class ModelSettingActivity extends BaseActivity {

  private RecyclerView mRecyclerView;
  private LinearLayoutManager linearLayoutManager;
  private KTSNewListAdapter mAdapter;
  private TextView exit;
  private TextView update_tv;
  private TextView model_setting_text;
  private TextView delete_tv;
  private TextView create_tv;

  private List<ModelSettingBean> mContentData;
  /**
   * 选中的项目名字
   */
  private String currentString;
  //文件里的名字
  private String currentModelFromServer;

  @Override protected int setLayoutId() {
    return R.layout.activity_model_setting;
  }

  @Override protected void initView() {
    super.initView();
    titleLayout.setLeftImage(R.drawable.left_arrow);
    titleLayout.setLeftMargin(18);
    titleLayout.setMiddleTitle(getString(R.string.setting));
    titleLayout.getMiddleView().getPaint().setFakeBoldText(true);
    titleLayout.setMiddleTextSize(19);
    titleLayout.setMiddleTextColor(ContextCompat.getColor(this, R.color.col_333333));
    titleLayout.getRigthTv().setVisibility(View.GONE);

    mRecyclerView = findViewById(R.id.model_setting_recycler);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mAdapter = new KTSNewListAdapter();

    mRecyclerView.setAdapter(mAdapter);
    exit = findViewById(R.id.exit);
    update_tv = findViewById(R.id.update_tv);
    delete_tv = findViewById(R.id.delete_tv);
    create_tv = findViewById(R.id.create_tv);
    model_setting_text = findViewById(R.id.model_setting_text);
  }

  private static final String path = CommonUtils.NEW_SETTING_PATH + "syscfg/varsave.ini";

  /**
   * 初始化数据
   */
  @Override
  protected void initData(@Nullable Bundle savedInstanceState) {
    mContentData = new ArrayList<>();
    getFiles();
  }

  private void getFiles() {
    HashMap<String, String> params = new HashMap<>();
    params.put("systemFilePath", path);
    params.put("filePath", CommonUtils.NEW_SETTING_PATH + "testcfg/");
    params.put("key", "mPriName");

    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.GET_CURRENT_SYSTEM, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              String str = jsonObject.getString("data");
              if ("0".equals(code)) {
                Type type = new TypeToken<ArrayList<ModelSettingBean>>() {
                }.getType();
                Gson gson = new Gson();
                List<ModelSettingBean> list = gson.fromJson(str, type);
                for (ModelSettingBean modelSettingBean : list) {
                  if (modelSettingBean.selected) {
                    currentModelFromServer = modelSettingBean.getName();
                    model_setting_text.setText(String.format(getString(R.string.current_model),
                        currentModelFromServer));
                  }
                }
                mContentData.clear();
                mContentData.addAll(list);
                mAdapter.setNewData(mContentData);
              } else {
                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
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

    RxView.clicks(exit)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            doExit();
          }
        });
    RxView.clicks(delete_tv)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            if (!currentString .equals(currentModelFromServer)) {
              delete(currentString);
            }else {
              Toast.makeText(ModelSettingActivity.this, "不能删除当前选中的", Toast.LENGTH_LONG).show();
            }
          }
        });
    RxView.clicks(create_tv)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            createDialog();
          }
        });
    RxView.clicks(update_tv)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            if (!currentString.equals(currentModelFromServer)) {
              update("mPriName", currentString.substring(currentString.lastIndexOf("/")+1));
            } else {
              Toast.makeText(ModelSettingActivity.this, "当前选中的项目就是当前的项目", Toast.LENGTH_LONG).show();
            }
          }
        });

    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < mContentData.size(); i++) {
          ModelSettingBean bean = mContentData.get(i);
          if (position == i) {
            bean.selected = true;
            currentString = bean.getName();
          } else {
            bean.selected = false;
          }
          mContentData.set(i, bean);
        }
        mAdapter.replaceData(mContentData);
        mAdapter.notifyDataSetChanged();
      }
    });
  }

  /**
   * 退出
   */
  private void doExit() {
    finish();
  }

  private void createFile(String name) {
    HashMap<String, String> params = new HashMap<>();
    params.put("sourcePath", mContentData.get(mContentData.size() - 1).getName());
    params.put("destPath", CommonUtils.NEW_SETTING_PATH + "testcfg/" + name);
    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.SETTING_ADD_FILE, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              if ("0".equals(code)) {

                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
                finish();
              } else {
                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
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
   * 删除文件
   */
  private void delete(String path) {
    HashMap<String, String> params = new HashMap<>();
    params.put("filePath", path);
    NetworkMgr1.getInstance()
        .post(String.class, CommonUtils.SETTING_DELETE_FILE, params, new CallBack<String>() {
          @Override
          public void onResponse(String response) {
            JSONObject jsonObject = null;
            try {
              jsonObject = new JSONObject(response);
              String code = jsonObject.getString("code");
              String message = jsonObject.getString("message");
              if ("0".equals(code)) {

                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
                finish();
              } else {
                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
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

  private void update(String key, String value) {
    HashMap<String, String> params = new HashMap<>();
    params.put("key1", key);
    params.put("value", value);
    params.put("needAddF", "1");
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

                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
                finish();
              } else {
                Toast.makeText(ModelSettingActivity.this, message, Toast.LENGTH_LONG).show();
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

  private AlertDialog alertDialog;

  private void createDialog() {
    View mLmCountView = LayoutInflater.from(this).inflate(R.layout.system_setting_add_dialog, null);

    EditText value = mLmCountView.findViewById(R.id.set_dialog_edit);

    Button button = mLmCountView.findViewById(R.id.set_dialog_button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String valueStr = value.getText().toString();
        createFile(valueStr);
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
}
