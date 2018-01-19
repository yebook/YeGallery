package com.reemanye.gallerylib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ye on 2018/1/19.
 */

public class YeGallery extends RelativeLayout {
    private int mScreenWidth;
    private int mScreenHeight;
    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;

    public YeGallery (Context context) {
        this(context, (AttributeSet) null);
    }

    public YeGallery (Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public YeGallery (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_gallery, this);
        mScreenWidth = DisplayUtils.getScreenWidth(context);
        mScreenHeight = DisplayUtils.getScreenHeight(context);

//        int width = getLayoutParams().width;
//        int height = getLayoutParams().height;

//        Log.e("YeGallery", "===width:" + width + " / " + height);

        mRecyclerView = findViewById(R.id.vg_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
//        layoutParams.width = width;
//        layoutParams.height = height;
//        mRecyclerView.setLayoutParams(layoutParams);
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);

        List<String> data = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        mAdapter = new GalleryAdapter(context, mRecyclerView, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int childCount = mRecyclerView.getChildCount();
                Log.e("ccc", childCount + "");

                int[] location = new int[2];
                for (int i = 0; i < childCount; i++) {
                    View v = mRecyclerView.getChildAt(i);
                    v.getLocationOnScreen(location);


                    int recyclerViewCenterX = mRecyclerView.getLeft() + mRecyclerView.getWidth() / 2;
                    int itemCenterX = location[0] + v.getWidth() / 2;

                    //                   ★ 两边的图片缩放比例
                    float scale = 0.8f;
                    //                     ★某个item中心X坐标距recyclerview中心X坐标的偏移量
                    int offX =  Math.abs(itemCenterX - recyclerViewCenterX);
                    //                    ★ 在一个item的宽度范围内，item从1缩放至scale，那么改变了（1-scale），从下列公式算出随着offX变化，item的变化缩放百分比

                    float percent =offX * (1 - scale) / v.getWidth();
                    //                   ★  取反哟
                    float interpretateScale = 1 - percent;


                    //                    这个if不走的话，得到的是多级渐变模式
                    if (interpretateScale < scale) {
                        interpretateScale = scale;
                    }
                    v.setScaleX((interpretateScale));
                    v.setScaleY((interpretateScale));
                }
            }
        });

    }
}
