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

import java.util.ArrayList;

/**
 * Created by ubuntu on 17/8/16.
 */

public class OfferHomeAdapter extends PagerAdapter {
    private static final String TAG = OfferHomeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<String> imageArrayList;

    public OfferHomeAdapter(Context context, ArrayList<String> imageArrayList) {
        this.context = context;
        this.imageArrayList = imageArrayList;
    }

    @Override
    public int getCount() {
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
        View viewLayout = inflater.inflate(R.layout.cell_offer_home,
                container, false);

        ImageView imgOffer = (ImageView) viewLayout.findViewById(R.id.imgOffer);

        Glide.with(context).load(imageArrayList.get(position).toString())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imgOffer);

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