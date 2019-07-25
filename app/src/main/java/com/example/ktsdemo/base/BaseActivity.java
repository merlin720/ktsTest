package com.example.ktsdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import com.example.ktsdemo.CustomTitleBar;
import com.example.ktsdemo.R;
import com.example.ktsdemo.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import org.greenrobot.eventbus.EventBus;

/**
 * @author merlin720
 * @date 2019-07-25
 * @mail zy44638@gmail.com
 * @description
 */
public abstract class BaseActivity extends AppCompatActivity {
  protected ImmersionBar mImmersionBar;
  protected View mRootView;
  public CustomTitleBar titleLayout;
  /**
   * 是否在Activity使用沉浸式
   *
   * @return the boolean
   */
  protected boolean isImmersionBarEnabled() {
    return true;
  }

  /**
   * 是否注册EventBus
   * 默认不注册
   */
  protected boolean isRegisterEventBus() {
    return false;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isRegisterEventBus()) {
      EventBus.getDefault().register(this);
    }
    LogUtils.e("===当前activity：(" + this.getClass().getSimpleName() + ".java:1)");
    mRootView = LayoutInflater.from(this).inflate(setLayoutId(), null);
    setContentView(mRootView);

    titleLayout = findViewById(R.id.title_layout);
    //初始化view
    initView();

    //初始化数据绑定
    initData(savedInstanceState);
    //设置监听
    setListener();
    if (isImmersionBarEnabled()) {
      initImmersionBar();
    }
  }


  @Override protected void onDestroy() {
    super.onDestroy();
    if (EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().unregister(this);
    }
  }

  /**
   * 初始化沉浸式
   */
  protected void initImmersionBar() {
    mImmersionBar = ImmersionBar.with(this);
    mImmersionBar
        .statusBarDarkFont(true, 0.2f)
        .navigationBarWithKitkatEnable(false)
        .init();
  }

  protected abstract int setLayoutId();

  /**
   * 初始化数据
   */
  protected void initData(@Nullable Bundle savedInstanceState) {

  }

  /**
   * 初始化view
   */
  protected void initView() {
  }

  /**
   * 设置监听
   */
  protected void setListener() {

  }
}
