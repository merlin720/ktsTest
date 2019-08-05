package com.example.ktsdemo;

import android.content.Intent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ktsdemo.base.BaseActivity;
import com.example.ktsdemo.net.NetworkMgr1;
import com.example.ktsdemo.util.CommonUtils;
import com.example.ktsdemo.util.SizeUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.merlin.network.CallBack;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 第一个启动的界面
 */
public class ButtonActivity extends BaseActivity {

  private TextView look;
  private TextView start;
  private TextView stop;

  private ImageView imageView;

  @Override protected int setLayoutId() {
    return R.layout.activity_button;
  }

  @Override protected void initView() {
    super.initView();

    look = findViewById(R.id.detail);
    start = findViewById(R.id.start);
    stop = findViewById(R.id.stop);
    imageView = findViewById(R.id.image);

    float width = SizeUtils.getDisplayWidth() * 0.7f;
    float height = SizeUtils.getDisplayHeight() * 0.7f;

    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();

    layoutParams.height = (int) (layoutParams.width * height / width);
    layoutParams.width = (int) width;
    imageView.setLayoutParams(layoutParams);

    Disposable disposable = RxView.clicks(look)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            goToDetail();
          }
        });

    Disposable disposable1 = RxView.clicks(start)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            goStart();
          }
        });

    Disposable disposable2 = RxView.clicks(stop)
        .throttleFirst(500, TimeUnit.MICROSECONDS)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            goStop();
          }
        });
  }

  /**
   * 跳转到文件列表页
   */
  private void goToDetail() {
    startActivity(new Intent(ButtonActivity.this, FileListActivity.class));
  }

  private void goStart() {
    update("run", "1");
  }

  private void goStop() {
    update("stop", "1");
  }

  private void update(String key, String value) {
    HashMap<String, String> params = new HashMap<>();
    params.put("key1", key);
    params.put("value", value);
    params.put("signal", "=");
    params.put("needAddF", "1");
    params.put("filePath", CommonUtils.START_STOP_PATH);
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
                Toast.makeText(ButtonActivity.this, message, Toast.LENGTH_LONG).show();
              } else {
                Toast.makeText(ButtonActivity.this, message, Toast.LENGTH_LONG).show();
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
