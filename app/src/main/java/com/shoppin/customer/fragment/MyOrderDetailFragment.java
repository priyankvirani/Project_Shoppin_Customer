package com.shoppin.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppin.customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ubuntu on 30/7/16.
 */
public class MyOrderDetailFragment extends BaseFragment {
    private static final String ORDER_NUMBER = "order_number";

    private static final String TAG = MyOrderDetailFragment.class.getSimpleName();
    @BindView(R.id.rlvGlobalProgressbar)
    View rlvGlobalProgressbar;
//    @BindView(R.id.recyclerListOrder)
//    RecyclerView recyclerListOrder;
//    private ArrayList<Order> orderArrayList;
//    private MyOrderAdapter myOrderAdapter;

    public static MyOrderDetailFragment newInstance(String orderNumber) {
        MyOrderDetailFragment myOrderDetailFragment = new MyOrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_NUMBER, orderNumber);
        myOrderDetailFragment.setArguments(bundle);
        return myOrderDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_my_order_detail, container, false);
        ButterKnife.bind(this, layoutView);

//        orderArrayList = new ArrayList<>();
//        myOrderAdapter = new MyOrderAdapter(getActivity(), orderArrayList);
//        myOrderAdapter.setOnItemClickListener(new MyOrderAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) getActivity();
////                if (navigationDrawerActivity != null) {
////                    navigationDrawerActivity.switchContent(ProductDetailFragment.newInstance(orderArrayList.get(position).productId), false);
////                }
//            }
//        });
//        recyclerListOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerListOrder.setAdapter(myOrderAdapter);
//
//        getMyOrderDetail();
        return layoutView;
    }

//    private void getMyOrderDetail() {
//        try {
//            JSONObject myOrderParam = new JSONObject();
//            myOrderParam.put(IWebService.KEY_REQ_CUSTOMER_ID,
//                    DBAdapter.getMapKeyValueString(getActivity(), IDatabase.IMap.CUSTOMER_ID));
//            DataRequest getSuburbsDataRequest = new DataRequest(getActivity());
//            getSuburbsDataRequest.execute(IWebService.GET_MY_ORDER, myOrderParam.toString(), new DataRequest.CallBack() {
//                public void onPreExecute() {
//                    rlvGlobalProgressbar.setVisibility(View.VISIBLE);
//                }
//
//                public void onPostExecute(String response) {
//                    try {
//                        rlvGlobalProgressbar.setVisibility(View.GONE);
//                        if (!DataRequest.hasError(getActivity(), response, true)) {
//                            JSONObject dataJObject = DataRequest.getJObjWebdata(response);
//                            Gson gson = new Gson();
//                            ArrayList<Order> tmpOrderArrayList = gson.fromJson(
//                                    dataJObject.getJSONArray(IWebService.KEY_RES_ORDER_LIST).toString(),
//                                    new TypeToken<ArrayList<Order>>() {
//                                    }.getType());
//
//                            if (tmpOrderArrayList != null) {
//                                orderArrayList.clear();
//                                orderArrayList.addAll(tmpOrderArrayList);
//                                myOrderAdapter.notifyDataSetChanged();
//                                Log.d(TAG, "orderArrayList = " + orderArrayList.size());
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
