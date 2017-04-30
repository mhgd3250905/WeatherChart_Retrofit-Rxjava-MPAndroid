package com.skkk.ww.skrecyclerviewitemdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/4/22.
 */
/*
* 
* 描    述：ViewHolder
* 作    者：ksheng
* 时    间：2017/4/22$ 22:37$.
*/
public class MyViewHolder extends RecyclerView.ViewHolder{
    @InjectView(R.id.tv_item_recylcer)
    public TextView tvItem;
    @InjectView(R.id.iv_item_move)
    public ImageView ivItemMove;

    public MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this,itemView);
    }
}
