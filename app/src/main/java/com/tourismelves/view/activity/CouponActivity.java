package com.tourismelves.view.activity;

import android.widget.TextView;

import com.tourismelves.R;
import com.tourismelves.view.activity.base.StateBaseActivity;

public class CouponActivity extends StateBaseActivity {

    TextView tv_title;


    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_coupon);
        initView();
    }
    private void initView() {
        tv_title = findViewById(R.id.title_name);
        tv_title.setText("我的优惠券");
    }


    @Override
    protected void initControls() {
     //   setStatusBar(R.id.select_city_status);
        setStatusUi();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

    }
}