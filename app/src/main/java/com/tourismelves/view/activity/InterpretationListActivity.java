package com.tourismelves.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.tourismelves.R;
import com.tourismelves.utils.log.LogUtil;
import com.tourismelves.view.activity.base.StateBaseActivity;
import com.tourismelves.view.adapter.InterpretationListAdapter;

import java.util.ArrayList;

/**
 * 讲解列表
 */

public class InterpretationListActivity extends StateBaseActivity {
    private AppCompatImageView interpretationListComments;
    private LinearLayout interpretationListBottom;
    private int lastY = 0;

    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_interpretation_list);
    }

    @Override
    protected void initControls() {
        showStateLayout(1);
        setBaseTitle("讲解列表");
        interpretationListComments = findViewById(R.id.interpretation_list_comments);
        interpretationListBottom = findViewById(R.id.interpretation_list_bottom);

        setBehaviorCallback();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

        interpretationListComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.show();
            }
        });

        interpretationListBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //将点下的点的坐标保存
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //计算出需要移动的距离
                        int dy = (int) event.getRawY() + lastY;
                        if (dy > 1) {
                            mBottomSheetDialog.show();
                        }
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 讲解列表
     */
    private void setBehaviorCallback() {
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_fragment_interpretation_list, null, false);
        mBottomSheetDialog.setContentView(view);

        AppCompatImageView commentsOff = (AppCompatImageView) view.findViewById(R.id.interpretation_list_comments_off);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.interpretation_ll);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.interpretation_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new InterpretationListAdapter(getContext(), new ArrayList<String>()));

        commentsOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        View rootView = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        rootView.setBackgroundColor(0x00ffffff);

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(rootView);
        bottomSheetBehavior.setPeekHeight((int) getResources().getDimension(R.dimen.dp364));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                LogUtil.i("onStateChanged: " + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                LogUtil.i("onSlide: " + slideOffset);
            }
        });

        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //将点下的点的坐标保存
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //计算出需要移动的距离
                        int dy = (int) event.getRawY() + lastY;
                        if (dy > 5) {
                            mBottomSheetDialog.dismiss();
                            startActivity(new Intent(getContext(), InterpretationList2Activity.class));
                        }
                        break;
                }
                return true;
            }
        });
    }
}