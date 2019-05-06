package com.frictionhacks.tenderhaltinfo.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContractorTenderDetailAdapter extends RecyclerView.Adapter<ContractorTenderDetailAdapter.ViewHolder> {
    private onItemClickListener clickListner;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater ll=LayoutInflater.from(parent.getContext());
    View v=ll.inflate(R.layout.recyclerview_tender_detail_contractor_row,parent,false);
    return new ViewHolder(v);
    }

    public ContractorTenderDetailAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.tvTenderId.setText(ContractorTenderDetailsDashboardModel.mData.get(position).getMtenderId());
//        holder.tvLocation.setText(String.format("%s", ContractorTenderDetailsDashboardModel.mData.get(position).getmLat()));
//        holder.tvLocation.append(String.format("%s", ContractorTenderDetailsDashboardModel.mData.get(position).getmLong()));
        holder.tvLocation.setText(ContractorTenderDetailsDashboardModel.mData.get(position).getlocation());
        holder.tvStartDate.setText(ContractorTenderDetailsDashboardModel.mData.get(position).getmDateStart());
        holder.tvEndDate.setText(ContractorTenderDetailsDashboardModel.mData.get(position).getmDataEnd());
        holder.tvStartDate.setText(ContractorTenderDetailsDashboardModel.mData.get(position).getmDateStart());
        holder.tvStatus.setText(String.format("%d", ContractorTenderDetailsDashboardModel.mData.get(position).getmStatus()));

    }

    @Override
    public int getItemCount() {
        return ContractorTenderDetailsDashboardModel.mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
TextView tvTenderId,tvStartDate,tvEndDate,tvLocation,tvStatus;
ImageView haltButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenderId=itemView.findViewById(R.id.tv_rv_contractor_row_tender_id);
            tvStartDate=itemView.findViewById(R.id.tv_rv_contractor_row_tender_date_start);
            tvEndDate=itemView.findViewById(R.id.tv_rv_contractor_row_tender_date_end);
            tvLocation=itemView.findViewById(R.id.tv_rv_contractor_row_tender_location);
            tvStatus=itemView.findViewById(R.id.tv_rv_contractor_row_tender_status);
            haltButton=itemView.findViewById(R.id.bt_rv_contractor_tender_halt_request);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListner.onHaltRequest(v,getAdapterPosition());
        }
    }
    public void setClickListener(onItemClickListener itemClickListener) {
        this.clickListner = itemClickListener;
    }
}
