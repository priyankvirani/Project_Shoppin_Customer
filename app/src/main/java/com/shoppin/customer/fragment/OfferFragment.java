package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppin.customer.R;
import com.shoppin.customer.activity.NavigationDrawerActivity;
import com.shoppin.customer.adapter.OfferAdapter;
import com.shoppin.customer.model.Offer;
import com.shoppin.customer.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 24/8/16.
 */

public class OfferFragment extends BaseFragment {

    private static final String TAG = OfferFragment.class.getSimpleName();

    @BindView(R.id.recyclerListOffer)
    RecyclerView recyclerListOffer;

    private ArrayList<Offer> offerArrayList;
    private OfferAdapter offerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_offer, container, false);
        ButterKnife.bind(this, layoutView);

        offerArrayList = new ArrayList<>();
        offerAdapter = new OfferAdapter(getActivity(), offerArrayList);
        offerAdapter.setOnItemClickListener(new OfferAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Utils.copyTextToClipBoard(getActivity(), offerArrayList.get(position).offer_detail);
            }
        });
        recyclerListOffer.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerListOffer.setAdapter(offerAdapter);

        dummyData();

        return layoutView;
    }

    private void dummyData() {

        Offer objOffer = new Offer("offer_1");
        offerArrayList.add(objOffer);

        Offer objOffer1 = new Offer("offer_2");
        offerArrayList.add(objOffer1);

        Offer objOffer2 = new Offer("offer_3");
        offerArrayList.add(objOffer2);

        Offer objOffer3 = new Offer("offer_4");
        offerArrayList.add(objOffer3);

        Offer objOffer4 = new Offer("offer_5");
        offerArrayList.add(objOffer4);

        offerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateFragment() {
        super.updateFragment();
        if (getActivity() != null && getActivity() instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) getActivity()).setToolbarTitle("Offers");
        }
    }


}
