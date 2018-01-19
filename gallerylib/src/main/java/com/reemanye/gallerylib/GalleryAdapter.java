package com.reemanye.gallerylib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ye on 2018/1/19.
 */

public class GalleryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int mRecyclerviewWidth;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public GalleryAdapter (Context context, RecyclerView recyclerView, @Nullable List<String> data) {
        super(R.layout.view_item, data);
        this.mContext = context;
        mRecyclerView = recyclerView;
        ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
        if (layoutParams.width == -1) {
            mRecyclerviewWidth = DisplayUtils.getScreenWidth(mContext);
        } else {
            mRecyclerviewWidth = layoutParams.width;
        }
    }

    @Override
    protected void convert (BaseViewHolder helper, String item) {
        helper.setText(R.id.iv_tv, item);
    }

    @Override
    public void onBindViewHolder (BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        RelativeLayout rootView = holder.getView(R.id.iv_root);
        final ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) rootView.getLayoutParams();
        Log.e("MainActivity", "==== p.with:" + p.width + " / " + mRecyclerviewWidth);
        // 为了居中， 第一个条目leftMagrin、最后一个条目的rightMargin是（recyclerView宽度减去一个条目的宽度）/2
        int margin = (mRecyclerviewWidth - p.width) / 2;
        if (position == 0) {
            p.leftMargin = margin;
            p.rightMargin = 0;
            rootView.setLayoutParams(p);
        } else if (position == getData().size() - 1) {
            p.leftMargin = 0;
            p.rightMargin = margin;
            rootView.setLayoutParams(p);
        } else {
            p.leftMargin = 0;
            p.rightMargin = 0;
            rootView.setLayoutParams(p);
        }

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int currentX = location[0];
                int currentCenterX = (int) (currentX + p.width / 2 * 0.8f);//因为除了中间外的其他条目是被缩放为0.8的状态
                int recyclerViewCenterX = mRecyclerviewWidth / 2;
                int offX = currentCenterX - recyclerViewCenterX;

                if (Math.abs(offX) >p.width / 2 * 0.21f) {//因为已经居中的Item，已经被放大到比例1了
                    mRecyclerView.smoothScrollBy(offX, 0);
                }
            }
        });
    }
}
