package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.usk.glotus_final.R;
import java.util.ArrayList;

public class ListOtgAdapter extends ArrayAdapter {
    private Context mContext;
    public static Otg item;
    private int mResource;
    private int lastPosion= -1;
    private boolean b=false;
    private LinearLayout lastlinear;
    public static boolean isnew=false;


    private static class ViewHolder {

        TextView primech;
        TextView pallet;
        TextView korob;
        TextView kuda;
        TextView otkuda;
        TextView datakont;
        TextView datavyg;
        TextView vesfact;
        TextView obiemfact;
        TextView kolfact;
        TextView zakaz;
        TextView Ref_key;
}


    /**
     * Deafault constructor for ManagerListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ListOtgAdapter(@NonNull Context context, int resource, ArrayList<Otg> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        b=false;



        final Otg otg= (Otg) getItem(position);
        final View result;

        final ListOtgAdapter.ViewHolder holder;



        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ListOtgAdapter.ViewHolder();
           holder.primech = (TextView) convertView.findViewById(R.id.primech);
            holder.datavyg = (TextView) convertView.findViewById(R.id.datavyg);
            holder.datakont = (TextView) convertView.findViewById(R.id.datakont);
            holder.otkuda = (TextView) convertView.findViewById(R.id.otkuda);
            holder.kuda = (TextView) convertView.findViewById(R.id.kuda);
            holder.obiemfact = (TextView) convertView.findViewById(R.id.obiem);
            holder.kolfact=convertView.findViewById(R.id.kol);
            holder.vesfact=(TextView) convertView.findViewById(R.id.ves);
            holder.korob=(TextView)convertView.findViewById(R.id.korob);
            holder.pallet=(TextView)convertView.findViewById(R.id.pallet);
            holder.primech=(TextView)convertView.findViewById(R.id.primech);
            result = convertView;
            convertView.setTag(holder);      holder.primech.setText(otg.getPrimech());


        }
        else{
            holder = (ListOtgAdapter.ViewHolder) convertView.getTag();
        result = convertView;
    }

        holder.datavyg.setText(otg.getDatavyg());
        holder.datakont.setText(otg.getDatakont());
        holder.otkuda.setText(otg.getOtkuda());
        holder.kuda.setText(otg.getKuda());
        holder.obiemfact.setText(otg.getObiemfact());
        holder.kolfact.setText(otg.getKolfact());
        holder.vesfact.setText(otg.getVesfact());
        holder.korob.setText(otg.getKorob());
        holder.pallet.setText(otg.getPallet());
        holder.primech.setText(otg.getPrimech());





        lastPosion=position;

        return convertView;
    }
}
