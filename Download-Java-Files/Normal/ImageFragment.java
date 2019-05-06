package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usk.glotus_final.R;

import java.io.File;

public class ImageFragment extends Fragment {

    private static final String ARG_IMAGE_NAME = "imageName";
    private static final String ARG_IMAGE_URI = "imageUri";

    private String imgName;
    private String imgUri;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String imageName, String imageUri) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_NAME, imageName);
        args.putString(ARG_IMAGE_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imgName = getArguments().getString(ARG_IMAGE_NAME);
            imgUri = getArguments().getString(ARG_IMAGE_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_image, container, false);

        TextView imageName=v.findViewById(R.id.tv_imageName);
        ImageView imageView=v.findViewById(R.id.iv_image);

        imageName.setText(imgName);

        File imgFile=new File(imgUri);
        if(imgFile.exists()) {
            imageView.setImageURI(Uri.fromFile(imgFile));
        }

        return v;
    }
}
