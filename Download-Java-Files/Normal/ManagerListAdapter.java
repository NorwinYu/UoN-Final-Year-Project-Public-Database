package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.System.Catalog.Adress;
import com.example.usk.glotus_final.System.Catalog.Kontragent;
import com.example.usk.glotus_final.System.DoubleClick;
import com.example.usk.glotus_final.System.DoubleClickListener;

import java.util.ArrayList;

public class ManagerListAdapter extends ArrayAdapter <ManagerZayavka> {

    private Context mContext;
    public static ManagerZayavka item;
    private int mResource;
    private int lastPosion= -1;
    private boolean b=false;
    private LinearLayout lastlinear;
    public static boolean isnew=false;


    private static class ViewHolder {
        TextView number;
        TextView date;
        TextView sender;
        TextView recept;
        TextView receptadr;
        TextView senderadr;
        TextView manager;
        TextView iv_status;
        LinearLayout linearLayout;
        TextView zakazname;
        TextView namegruz;
        TextView soprdoc;
    }


    /**
     * Deafault constructor for ManagerListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ManagerListAdapter(@NonNull Context context, int resource, ArrayList<ManagerZayavka> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        b=false;

        String date=getItem(position).getDate();
        String number=getItem(position).getNumber();
        String sender=getItem(position).getSender();
        String recept=getItem(position).getRecept();
        String receptadr=getItem(position).getReceptadr();
        String senderadr=getItem(position).getSenderadr();
        String manager=getItem(position).getMenedjer();
        String podrazd=getItem(position).getPodrazd();
        String ref_key=getItem(position).getRef_key();
        final String zakaz=getItem(position).getZakaz();
        String status=getItem(position).getStatus();
        String namegruz=getItem(position).getNamegruz();
        String soprdoc=getItem(position).getSoprdocument();


        final ManagerZayavka ManagerZayavka=getItem(position);
        final View result;

        final ManagerListAdapter.ViewHolder holder;

        if (convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ManagerListAdapter.ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.tv_code);
            holder.date = (TextView) convertView.findViewById(R.id.tv_data);
            holder.sender = (TextView) convertView.findViewById(R.id.tv_otpravitel);
            holder.recept = (TextView) convertView.findViewById(R.id.tv_poluchatel);
            holder.senderadr = (TextView) convertView.findViewById(R.id.tv_otkuda);
            holder.receptadr = (TextView) convertView.findViewById(R.id.tv_kuda);
            holder.linearLayout=convertView.findViewById(R.id.ll_moreInf);
            holder.iv_status=(TextView) convertView.findViewById(R.id.tv_status);
            holder.zakazname=(TextView)convertView.findViewById(R.id.tv_partner);
            holder.namegruz=(TextView)convertView.findViewById(R.id.tv_tovar);
            holder.soprdoc=(TextView)convertView.findViewById(R.id.tv_document);

            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }




        holder.date.setText("     "+ManagerZayavka.getDate().split("T")[0]+"\n     "+ManagerZayavka.getDate().split("T")[1]);
        holder.number.setText(ManagerZayavka.getNumber());
        holder.sender.setText(ManagerZayavka.getSender());
        holder.recept.setText(ManagerZayavka.getRecept());
        holder.senderadr.setText((String) Adress.adresspreferences.getAll().get(ManagerZayavka.getSenderadr()));
        holder.receptadr.setText((String) Adress.adresspreferences.getAll().get(ManagerZayavka.getReceptadr()));
        holder.iv_status.setText(ManagerZayavka.getStatus());
        holder.zakazname.setText((String)Kontragent.kontrpreferences.getAll().get(ManagerZayavka.getZakaz()));
        holder.namegruz.setText(ManagerZayavka.getNamegruz());
        holder.soprdoc.setText(ManagerZayavka.getSoprdocument());

        holder.linearLayout.setVisibility(View.GONE);





        convertView.setOnClickListener(new DoubleClick(new DoubleClickListener() {

            @Override
            public void onSingleClick(View view) {
                   if (lastlinear!=null)
                    lastlinear.setVisibility(View.GONE);
                System.out.println("aaaa");

                if (holder.linearLayout.getVisibility()==View.VISIBLE)
                {
                    holder.linearLayout.setVisibility(View.GONE);
                }
                else
                holder.linearLayout.setVisibility(View.VISIBLE);
                lastlinear=holder.linearLayout;


            }

            @Override
            public void onDoubleClick(View view) {
                item=ManagerZayavka;
                isnew=false;
                Intent myIntent = new Intent(mContext, ReceptionManagerActivity.class);
                mContext.startActivity(myIntent);

            }
        }));



        lastPosion=position;

        return convertView;
    }
}
