package com.fongmi.android.tv.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.Setting;
import com.fongmi.android.tv.databinding.FragmentSettingInterfaceBinding;
import com.fongmi.android.tv.event.RefreshEvent;
import com.fongmi.android.tv.ui.base.BaseFragment;
import com.fongmi.android.tv.utils.ResUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingInterfaceFragment extends BaseFragment {

    private FragmentSettingInterfaceBinding mBinding;
    private String[] size;
    private String[] recommend;

    public static SettingInterfaceFragment newInstance() {
        return new SettingInterfaceFragment();
    }

    private String getSwitch(boolean value) {
        return getString(value ? R.string.setting_on : R.string.setting_off);
    }

    @Override
    protected ViewBinding getBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return mBinding = FragmentSettingInterfaceBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        setOtherText();
    }

    private void setOtherText() {
        mBinding.changeText.setText(getSwitch(Setting.isChange()));
        mBinding.sizeText.setText((size = ResUtil.getStringArray(R.array.select_size))[Setting.getSize()]);
        mBinding.recommendText
                .setText((recommend = ResUtil.getStringArray(R.array.select_recommend))[Setting.getRecommend()]);
    }

    @Override
    protected void initEvent() {
        mBinding.size.setOnClickListener(this::setSize);
        mBinding.change.setOnClickListener(this::setChange);
        mBinding.recommend.setOnClickListener(this::setRecommend);
    }

    private void setSize(View view) {
        new MaterialAlertDialogBuilder(requireActivity()).setTitle(R.string.setting_size)
                .setNegativeButton(R.string.dialog_negative, null)
                .setSingleChoiceItems(size, Setting.getSize(), (dialog, which) -> {
                    mBinding.sizeText.setText(size[which]);
                    Setting.putSize(which);
                    RefreshEvent.size();
                    dialog.dismiss();
                }).show();
    }

    private void setChange(View view) {
        Setting.putChange(!Setting.isChange());
        mBinding.changeText.setText(getSwitch(Setting.isChange()));
    }

    private void setRecommend(View view) {
        new MaterialAlertDialogBuilder(requireActivity()).setTitle(R.string.setting_recommend)
                .setNegativeButton(R.string.dialog_negative, null)
                .setSingleChoiceItems(recommend, Setting.getRecommend(), (dialog, which) -> {
                    mBinding.recommendText.setText(recommend[which]);
                    Setting.putRecommend(which);
                    dialog.dismiss();
                }).show();
    }
}
