package com.fongmi.android.tv.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.Setting;
import com.fongmi.android.tv.databinding.ActivitySettingInterfaceBinding;
import com.fongmi.android.tv.event.RefreshEvent;
import com.fongmi.android.tv.ui.base.BaseActivity;
import com.fongmi.android.tv.utils.ResUtil;

public class SettingInterfaceActivity extends BaseActivity {

    private ActivitySettingInterfaceBinding mBinding;
    private String[] recommend;
    private String[] size;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, SettingInterfaceActivity.class));
    }

    @Override
    protected ViewBinding getBinding() {
        return mBinding = ActivitySettingInterfaceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        mBinding.sizeText.setText((size = ResUtil.getStringArray(R.array.select_size))[Setting.getSize()]);
        mBinding.recommendText
                .setText((recommend = ResUtil.getStringArray(R.array.select_recommend))[Setting.getRecommend()]);
        mBinding.changeText.setText(getSwitch(Setting.isChange()));
    }

    @Override
    protected void initEvent() {
        mBinding.size.setOnClickListener(this::setSize);
        mBinding.recommend.setOnClickListener(this::setRecommend);
        mBinding.change.setOnClickListener(this::setChange);
    }

    private void setSize(View view) {
        int index = (Setting.getSize() + 1) % size.length;
        mBinding.sizeText.setText(size[index]);
        Setting.putSize(index);
        RefreshEvent.size();
    }

    private void setRecommend(View view) {
        int index = (Setting.getRecommend() + 1) % recommend.length;
        mBinding.recommendText.setText(recommend[index]);
        Setting.putRecommend(index);
    }

    private void setChange(View view) {
        Setting.putChange(!Setting.isChange());
        mBinding.changeText.setText(getSwitch(Setting.isChange()));
    }

    private String getSwitch(boolean value) {
        return getString(value ? R.string.setting_on : R.string.setting_off);
    }
}
