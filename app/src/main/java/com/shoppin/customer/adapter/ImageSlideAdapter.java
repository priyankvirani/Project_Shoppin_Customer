package com.shoppin.customer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.shoppin.customer.R;

import java.util.ArrayList;

/**
 * Created by ubuntu on 17/8/16.
 */

public class ImageSlideAdapter extends PagerAdapter {
    private static final String TAG = ImageSlideAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<String> imageArrayList;
    private ImageView[] dots;
    private LinearLayout offerViewPagerIndicator;

    public ImageSlideAdapter(Context context, ArrayList<String> imageArrayList, LinearLayout offerViewPagerIndicator) {
        this.context = context;
        this.imageArrayList = imageArrayList;
        this.offerViewPagerIndicator = offerViewPagerIndicator;
    }

    @Override
    public int getCount() {
        return imageArrayList == null ? 0 : imageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == object;
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
        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

    public void setUiPageViewController() {
        if (offerViewPagerIndicator != null && imageArrayList != null && imageArrayList.size() > 0) {
            offerViewPagerIndicator.setVisibility(View.VISIBLE);
            Log.d(TAG, "imageArrayList.size() = " + imageArrayList.size());
            dots = new ImageView[imageArrayList.size()];

            for (int i = 0; i < imageArrayList.size(); i++) {
                dots[i] = new ImageView(context);
                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_selected_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(4, 0, 4, 0);

                offerViewPagerIndicator.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selected_dot));
        }
    }

    public void setIndicator(int position) {
        if (offerViewPagerIndicator != null && imageArrayList != null && imageArrayList.size() > 0) {
            for (int i = 0; i < imageArrayList.size(); i++) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_selected_dot));
            }
            dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selected_dot));
        }
    }

}