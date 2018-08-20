package com.tourismelves.view.activity;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tourismelves.R;
import com.tourismelves.model.net.OkHttpUtils;
import com.tourismelves.utils.common.ToastUtil;
import com.tourismelves.utils.system.LocationUtil;
import com.tourismelves.utils.system.ResolutionUtil;
import com.tourismelves.view.activity.base.StateBaseActivity;
import com.tourismelves.view.adapter.FragmentAdapter;
import com.tourismelves.view.widget.SlidingTabLayout;
import com.tourismelves.view.widget.loadlayout.State;
import com.tourismelves.view.widget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.tourismelves.app.constant.UrlConstants.nearOrganizationList;

/**
 * 附近景区
 */

public class NearScenicSpotActivity extends StateBaseActivity {

    @BindView(R.id.near_tab)
    SlidingTabLayout nearTab;
    @BindView(R.id.near_viewpager)
    NoScrollViewPager nearViewpager;

    private List<String> strings;
    private List<Fragment> fragments;
    private int tabWidth;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_near_scenic_spot);
    }

    @Override
    protected void initControls() {
        showStateLayout(1);
        setBaseRightImage(R.mipmap.search);
        tabWidth = ResolutionUtil.getInstance(getContext()).getWidth() / 4;

        strings = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    @Override
    protected void obtainData() {
        nearOrganizationList();
        strings.add("景区");
        strings.add("美食");
        strings.add("购物");
        strings.add("娱乐");

        nearTab.setTabWhite(tabWidth);


        nearViewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, strings));
        nearViewpager.setOffscreenPageLimit(fragments.size());
        //两者关联
        nearTab.setupWithViewPager(nearViewpager);
    }

    @Override
    protected void initEvent() {
        setBaseRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        nearViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                nearTab.redrawIndicator(position, positionOffset);  //自定义指示器的滑动
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void nearOrganizationList() {
        //当前页数
        int page = 1;
        //总页数
        int totalPage = 1;
        //获取当前经纬度
        Location location = LocationUtil.getInstance(getContext()).showLocation();
        OkHttpUtils.get(String.format(nearOrganizationList, location.getLatitude(), location.getLongitude(), 20, page),
                new OkHttpUtils.ResultCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject object = JSON.parseObject(response);
                        //获取请求结果的code码
                        Integer code = object.getInteger("code");
                        if (code == 200) {

                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        getLoadLayout().setLayoutState(State.FAILED);
                        ToastUtil.show(R.string.no_found_network);
                    }
                });
    }
}
