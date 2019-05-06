package com.sabrcare.app.medicine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.sabrcare.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineVH> {

    private ArrayList<MedicineModel> medicine;
    private Context ctx;

    public MedicineAdapter(ArrayList<MedicineModel> medicine, Context ctx) {
        this.ctx=ctx;
        this.medicine=medicine;
    }

    @NonNull
    @Override
    public MedicineAdapter.MedicineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicineAdapter.MedicineVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineVH holder, int position) {
        holder.populateMedicine(medicine.get(position));
    }

    public void addNewMedicine(MedicineModel medicineModel){
        medicine.add(medicineModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return medicine.size();
    }

    public class MedicineVH extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView med_name;
        public TextView day_phase;
        public TextView remState;
        public Button edit;

        public MedicineVH(@NonNull View itemView) {
            super(itemView);
            med_name=itemView.findViewById(R.id.medName);
            day_phase=itemView.findViewById(R.id.dayPhase);
            remState=itemView.findViewById(R.id.isReminderSet);
            edit=itemView.findViewById(R.id.editMed);
        }

//
//        public void setItemClickListener (ItemCl)
        public boolean onLongClick(View v) {
            Log.d(TAG, "Item long-clicked at position " + getAdapterPosition());
            return true;
        }
        void populateMedicine(final MedicineModel medicine){
            med_name.setText(medicine.getMedName());
            day_phase.setText(medicine.getDayPhase());

            if(medicine.isReminderOn()) {
                remState.setText("Reminder On");
            } else {
                remState.setText("Reminder Off");
            }

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editMed = new Intent(ctx,NewMedActivity.class);
                    editMed.setAction("Edit");
                    editMed.putExtra("medName",medicine.getMedName());
                    ctx.startActivity(editMed);
                }
            });
        }
    }
}
