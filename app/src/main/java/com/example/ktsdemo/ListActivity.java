package com.example.ktsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ktsdemo.adapter.KTSListAdapter;
import com.example.ktsdemo.net.NetworkMgr1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.merlin.network.CallBack;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.ktsdemo.util.CommonUtils.IP;

public class ListActivity extends AppCompatActivity {

  private static final String FilePath = "/Users/merlin720/kts/document";
  private static final String getFiles = IP + ":8080/test/queryFiles.do";
  private RecyclerView mRecyclerView;

  private KTSListAdapter adapter;

  private List<String> mContentData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    initView();
    initData();
    setListener();
  }

  private void initView() {
    mRecyclerView = findViewById(R.id.list_recycler_view);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    adapter = new KTSListAdapter();
    mRecyclerView.setAdapter(adapter);
  }

  private void initData() {
    mContentData = new ArrayList<>();
    getFiles();
  }

  private void getFiles() {
    HashMap<String, String> params = new HashMap<>();
    params.put("filePath", FilePath);
    NetworkMgr1.getInstance()
        .post(String.class, getFiles, params, new CallBack<String>() {
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
                Toast.makeText(ListActivity.this, message, Toast.LENGTH_LONG).show();
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

  private void setListener() {
    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mContentData.size()>0){
          Intent intent = new Intent(ListActivity.this,MainActivity.class);
          intent.putExtra("path", mContentData.get(position));
          startActivity(intent);
        }
      }
    });
  }
}