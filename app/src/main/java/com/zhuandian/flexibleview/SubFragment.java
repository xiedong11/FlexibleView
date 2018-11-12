package com.zhuandian.flexibleview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * desc :
 * author：xiedong
 * data：2018/11/12
 */
public class SubFragment extends Fragment {

    public static SubFragment getInstance(int pageIndex) {
        SubFragment fragment = new SubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", pageIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int pageIndex = getArguments().getInt("index", 0);
        View view = inflater.inflate(R.layout.fragment_sub, null);
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(String.format("答题区域第%d页", pageIndex));
        return view;
    }
}
