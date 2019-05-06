package com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usk.glotus_final.ManagerApp.ManagerListFiles.ListOtg;
import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.Reception;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Zayavka> {

    private static final String TAG = "ZayavkaListAdapter";
    public static Zayavka item;

    private Context mContext;
    int mResource;
    private int lastPosion= -1;

    private static class ViewHolder {
        TextView number;
        TextView date;
        TextView sender;
        TextView recept;
        TextView receptadr;
        TextView senderadr;
        TextView manager;
        ImageView iv_status;
    }

    /**
     * Default constructor for
     * @param context
     * @param resource
     * @param objects
     */



    public OrderListAdapter(@NonNull Context context, int resource, @NonNull List<Zayavka> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date=getItem(position).getDate();
        String number=getItem(position).getNumber();
        String sender=getItem(position).getSender();
        String recept=getItem(position).getRecept();
        String receptadr=getItem(position).getReceptadr();
        String senderadr=getItem(position).getSenderadr();
        String manager=getItem(position).getMenedjer();
        String podrazd=getItem(position).getPodrazd();
        String ref_key=getItem(position).getRef_key();
        String zakaz=getItem(position).getZakaz();
        String status=getItem(position).getStatus();

        final Zayavka zayavka=new Zayavka(number,date,sender,recept,senderadr,receptadr,ref_key,zakaz,manager,podrazd,status);
        final View result;

        final ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.tv_code);
            holder.date = (TextView) convertView.findViewById(R.id.tv_data);
            holder.sender = (TextView) convertView.findViewById(R.id.tv_otpravitel);
            holder.recept = (TextView) convertView.findViewById(R.id.tv_poluchatel);
            holder.senderadr = (TextView) convertView.findViewById(R.id.tv_otkuda);
            holder.receptadr = (TextView) convertView.findViewById(R.id.tv_kuda);
            holder.manager=(TextView) convertView.findViewById(R.id.tv_manager);
            holder.iv_status=(ImageView) convertView.findViewById(R.id.iv_strela);

            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosion=position;
        try {
            holder.date.setText("     "+zayavka.getDate().split("T")[0]+"\n     "+zayavka.getDate().split("T")[1]);

        }
        catch (Exception e){}
         holder.number.setText(zayavka.getNumber());
        holder.sender.setText(zayavka.getSender());
        holder.recept.setText(zayavka.getRecept());
       // holder.senderadr.setText((zayavka.getSenderadr()));
       // holder.receptadr.setText((zayavka.getReceptadr()));
        holder.manager.setText(zayavka.getMenedjer());
        holder.senderadr.setText(zayavka.getSenderadr());
        holder.receptadr.setText(zayavka.getReceptadr());

        if(status.equals("ПринятноНаСкладе"))
            holder.iv_status.setImageResource(R.drawable.gruzovik_green);
        else
        if(status.equals("Отгружено"))
            holder.iv_status.setImageResource(R.drawable.gruzovik_blue);
        else
        if(status.equals("Новая"))
            holder.iv_status.setImageResource(R.drawable.gruzovik_red);
        else
        if(status.equals("ПринятоВРаботу"))
            holder.iv_status.setImageResource(R.drawable.gruzovik_violet);
        else
        if(status.equals("Доставлено"))
            holder.iv_status.setImageResource(R.drawable.gruzovik_yellow);
        else
            holder.iv_status.setImageResource(R.drawable.gruzovik_grey);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item=zayavka;
                Intent myIntent = new Intent(mContext, ListOtg.class);
                mContext.startActivity(myIntent);
            }
        });
        return convertView;
    }


}
