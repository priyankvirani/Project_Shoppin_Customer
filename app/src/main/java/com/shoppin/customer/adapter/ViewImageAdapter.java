package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.shoppin.customer.R;
import com.shoppin.customer.model.Category;
import com.shoppin.customer.model.Offer;

import java.util.ArrayList;

public class ViewImageAdapter extends PagerAdapter {
    private static final String TAG = ViewImageAdapter.class.getSimpleName();
    Context context;
    ArrayList<String> imageArrayList;

    public ViewImageAdapter(Context context,
                            ArrayList<String> imageArrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.imageArrayList = imageArrayList;
        Log.d("iumage array size = ", " = " + imageArrayList.size());
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageArrayList == null ? 0 : imageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.cell_image_detail,
                container, false);

        ImageView imgDetail = (ImageView) viewLayout
                .findViewById(R.id.imgDetail);

        Glide.with(context).load(imageArrayList.get(position).toString())
                .placeholder(R.drawable.app_logo_transparent)
                .error(R.drawable.app_logo_transparent).into(imgDetail);
//        Glide.with(context).load(imageArrayList.get(position).toString()).into(imgDetail);

        // on page click listener of view pager
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, " positionClicked = " + position);
            }
        });
        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
