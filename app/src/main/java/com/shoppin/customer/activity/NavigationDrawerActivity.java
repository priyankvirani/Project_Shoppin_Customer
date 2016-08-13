package com.shoppin.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shoppin.customer.R;
import com.shoppin.customer.database.DBAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shoppin.customer.database.IDatabase.IMap;

public class NavigationDrawerActivity extends AppCompatActivity {
//    @BindView(R.id.logOut)
//    TextView logOut;

    private static final String TAG = SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        ButterKnife.bind(this);

        Log.d(TAG, "suburb_id = " + DBAdapter.getMapKeyValueString(NavigationDrawerActivity.this, IMap.SUBURB_ID));
        Log.d(TAG, "suburb_name = " + DBAdapter.getMapKeyValueString(NavigationDrawerActivity.this, IMap.SUBURB_NAME));
    }

    @OnClick(R.id.logOut)
    void logOut() {
        if (DBAdapter.getMapKeyValueBoolean(NavigationDrawerActivity.this, IMap.IS_LOGIN)) {
            DBAdapter.insertUpdateMap(NavigationDrawerActivity.this, IMap.SUBURB_ID, "");
            DBAdapter.insertUpdateMap(NavigationDrawerActivity.this, IMap.SUBURB_NAME, "");
            finish();
        } else {
            finish();
        }
    }
}
