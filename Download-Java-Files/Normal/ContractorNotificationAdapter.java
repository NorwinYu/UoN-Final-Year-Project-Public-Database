package com.frictionhacks.tenderhaltinfo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frictionhacks.tenderhaltinfo.DataModel.ContractorNotificationModel;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContractorNotificationAdapter extends RecyclerView.Adapter<ContractorNotificationAdapter.ViewHolder> {

    public ContractorNotificationAdapter() {
        super();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater ll=LayoutInflater.from(parent.getContext());
        View v=ll.inflate(R.layout.rv_notification_adapter_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNotification.setText(ContractorNotificationModel.mData.get(position).getmNotificationStatus());
        holder.tvTenderID.setText(ContractorNotificationModel.mData.get(position).getmTenderId());

    }

    @Override
    public int getItemCount() {
        return ContractorNotificationModel.mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenderID,tvNotification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenderID=itemView.findViewById(R.id.tv_rv_notification_tender);
            tvNotification=itemView.findViewById(R.id.tv_rv_notification_noti);
        }
    }
}
