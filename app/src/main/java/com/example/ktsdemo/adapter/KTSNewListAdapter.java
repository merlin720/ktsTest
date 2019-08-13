package com.example.ktsdemo.adapter;

import android.support.v4.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ktsdemo.R;
import com.example.ktsdemo.base.Test;
import com.example.ktsdemo.bean.ModelSettingBean;

/**
 * @author merlin720
 * @date 2019-07-24
 * @mail zy44638@gmail.com
 * @description
 */
public class KTSNewListAdapter extends BaseQuickAdapter<ModelSettingBean, BaseViewHolder> {

  public KTSNewListAdapter() {
    super(R.layout.home_list_item);
  }

  @Override protected void convert(BaseViewHolder helper, ModelSettingBean item) {
    helper.setText(R.id.list_tv, item.getName());
    if (item.selected) {
      helper.setBackgroundColor(R.id.list_tv, ContextCompat.getColor(mContext, R.color.blue));
    }else {
      helper.setBackgroundColor(R.id.list_tv, ContextCompat.getColor(mContext, R.color.white));
    }
  }

  public Test test;

  public void setTest(Test test) {
    this.test = test;
  }
}
