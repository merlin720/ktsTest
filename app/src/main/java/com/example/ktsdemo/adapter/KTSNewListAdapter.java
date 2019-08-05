package com.example.ktsdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ktsdemo.R;

/**
 * @author merlin720
 * @date 2019-07-24
 * @mail zy44638@gmail.com
 * @description
 */
public class KTSNewListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

  public KTSNewListAdapter() {
    super(R.layout.home_list_item);
  }

  @Override protected void convert(BaseViewHolder helper, String item) {
    helper.setText(R.id.list_tv, item);
  }
}
