package com.lznby.baidumapdemo.util;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lznby.baidumapdemo.R;
import com.lznby.baidumapdemo.entity.Hydrant;

/**
 * RecyclerView适配器
 */
public class HydrantAdapter extends BaseQuickAdapter<Hydrant,BaseViewHolder>{

    public HydrantAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Hydrant item) {
        helper.setText(R.id.card_hydrant_id_tv,"消防栓编号："+item.getHydrant_id()+"")
                .setText(R.id.card_status_tv,"水压状态："+Tools.estimateStatus(item.getStatus()))
                .setText(R.id.card_princical_name,"负责人姓名："+item.getPrincipal_name())
                .setText(R.id.card_princical_phone,"负责人电话："+item.getPrincipal_phone())
                .setText(R.id.card_address,"地址："+item.getAddress());
    }
}
