package com.wallan.multimediacamera.library;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallan.common.utils.library.LogUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(String txt) {
        Bundle args = new Bundle();
        args.putString("content", txt);
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mmcamera_fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LogUtils.d("savedInstanceState:" +getArguments());
//        LogUtils.d("savedInstanceState:" + savedInstanceState.getString("content"));
    }
}
