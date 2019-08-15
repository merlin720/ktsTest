package com.example.ktsdemo.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.example.ktsdemo.ButtonActivity;
import com.example.ktsdemo.CustomTitleBar;
import com.example.ktsdemo.R;
import com.example.ktsdemo.base.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.ktsdemo.util.CommonUtils.IP;

/**
 * @author merlin
 * 设置页列表界面
 */
public class CustomSettingActivity extends BaseActivity {

  private static final String GET_SETTING_FILE = IP + ":8080/test/querySettingFiles.do";

  private TextView model_setting;
  private TextView system_param;
  private TextView curve_parameter;
  private TextView process_standard;
  private TextView process_count_setting;
  private TextView exit;

  private List<String> mContentData;

  @Override protected int setLayoutId() {
    return R.layout.activity_custom_setting;
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

    model_setting = findViewById(R.id.model_setting);
    system_param = findViewById(R.id.system_param);
    curve_parameter = findViewById(R.id.curve_parameter);
    process_standard = findViewById(R.id.process_standard);
    process_count_setting = findViewById(R.id.process_count_setting);
    exit = findViewById(R.id.exit);
  }

  /**
   * 初始化数据
   */
  @Override
  protected void initData(@Nullable Bundle savedInstanceState) {

  }

  @Override
  protected void setListener() {
    titleLayout.setTitleClickListener(new CustomTitleBar.TitleUpdateListener() {
      @Override public void onLeftClick() {
        finish();
      }
    });
    RxView.clicks(model_setting)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            setModelSetting();
          }
        });
    RxView.clicks(system_param)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            setSystemParameter();
          }
        });
    RxView.clicks(curve_parameter)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            setCurveParameter();
          }
        });
    RxView.clicks(process_standard)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            setProcessStandard();
          }
        });
    RxView.clicks(process_count_setting)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            setProcessCountSetting();
          }
        });
    RxView.clicks(exit)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            doExit();
          }
        });
  }

  /**
   * 型号设置
   */
  private void setModelSetting() {
      startActivity(new Intent(CustomSettingActivity.this,ModelSettingActivity.class));
  }

  /**
   * 系统设置
   */
  private void setSystemParameter() {

  }

  /**
   * 曲线参数
   */
  private void setCurveParameter() {

  }

  /**
   * 过程与标准
   */
  private void setProcessStandard() {
    startActivity(new Intent(CustomSettingActivity.this, ProcessStandardSettingActivity.class));
  }

  /**
   * 加工计数设置
   */
  private void setProcessCountSetting() {

  }

  /**
   * 退出
   */
  private void doExit() {
    finish();
  }
}
