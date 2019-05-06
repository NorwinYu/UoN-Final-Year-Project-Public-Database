package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.Zayavka;

import java.util.ArrayList;

public class VygruzkaAdapter extends ArrayAdapter <Zayavka> {

    private Context mContext;
    private int mResource;

    /**
     * Deafault constructor for ManagerListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public VygruzkaAdapter(@NonNull Context context, int resource, ArrayList<Zayavka> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        return convertView;
    }
}
