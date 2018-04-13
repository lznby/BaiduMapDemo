package com.lznby.baidumapdemo.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lznby.baidumapdemo.DetailedActivity;
import com.lznby.baidumapdemo.R;
import com.lznby.baidumapdemo.entity.Hydrant;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class HydrantAdapter extends RecyclerView.Adapter<HydrantAdapter.ViewHolder>{
    private Context mContext;
    private List<Hydrant> mHydrant;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        private final TextView mCardHydrantIdTV;
        private final TextView mCardStatusTV;
        private final TextView mCardPrincicalNameTV;
        private final TextView mCardPrincicalPhoneTV;
        private final TextView mCardAddressTV;


        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;

            mCardHydrantIdTV = (TextView) view.findViewById(R.id.card_hydrant_id_tv);
            mCardStatusTV = (TextView) view.findViewById(R.id.card_status_tv);
            mCardPrincicalNameTV = (TextView) view.findViewById(R.id.card_princical_name);
            mCardPrincicalPhoneTV = (TextView) view.findViewById(R.id.card_princical_phone);
            mCardAddressTV = (TextView) view.findViewById(R.id.card_address);
        }
    }

    public HydrantAdapter(List<Hydrant> hydrantList){
        mHydrant = hydrantList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.hydrant_card_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Hydrant hydrant = mHydrant.get(position);
                Intent intent = new Intent(mContext, DetailedActivity.class);
                //传对象到详情页面
                intent.putExtra("hydrant",hydrant);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    //Holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hydrant hydrant = mHydrant.get(position);
        holder.mCardHydrantIdTV.setText("消防栓编号："+hydrant.getHydrant_id()+"");
        holder.mCardStatusTV.setText("水压状态："+Tools.estimateStatus(hydrant.getStatus()));
        holder.mCardPrincicalNameTV.setText("负责人姓名："+hydrant.getPrincipal_name());
        holder.mCardPrincicalPhoneTV.setText("负责人电话："+hydrant.getPrincipal_phone());
        holder.mCardAddressTV.setText("地址："+hydrant.getAddress());
    }

    @Override
    public int getItemCount() {
        return mHydrant.size();
    }
}
