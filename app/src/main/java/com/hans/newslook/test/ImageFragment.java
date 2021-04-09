package com.hans.newslook.test;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hans.newslook.R;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

    private ImageView test_image;


    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_image, container, false);
        test_image = root.findViewById(R.id.test_image);
        Glide.with(getContext()).load("http://wpstatic.zuimeia.com/images/8b257f61e148c84113e585730b50879b_2160x1920.jpg").into(test_image);
        return root;
    }
    public static ImageFragment newInstance(String imageUrl) {
        final ImageFragment f = new ImageFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

}
