package com.xing.shoppingcart;

import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/12.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<FruitBean> dataList;

    public RecyclerAdapter(Context context, List<FruitBean> list) {
        mContext = context;
        dataList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_shopping, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FruitBean fruitBean = dataList.get(position);
        ((ViewHolder) holder).imageView.setImageResource(fruitBean.getResId());
        ((ViewHolder) holder).nameTxtView.setText(fruitBean.getName());
        ((ViewHolder) holder).descTxtView.setText(fruitBean.getDesc());
        ((ViewHolder) holder).addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] outLocation = new int[2];
                view.getLocationInWindow(outLocation);
                if (listener != null) {
                    listener.onItemButtonClick(view, position, outLocation);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView nameTxtView;

        TextView descTxtView;

        Button addButton;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_icon);
            nameTxtView = itemView.findViewById(R.id.tv_name);
            descTxtView = itemView.findViewById(R.id.tv_desc);
            addButton = itemView.findViewById(R.id.btn_add_cart);

        }
    }

    OnItemButtonClickListener listener;

    public interface OnItemButtonClickListener {
        void onItemButtonClick(View view, int position, int[] locations);
    }

    public void setOnItemButtonClickListener(OnItemButtonClickListener listener) {
        this.listener = listener;
    }


}
