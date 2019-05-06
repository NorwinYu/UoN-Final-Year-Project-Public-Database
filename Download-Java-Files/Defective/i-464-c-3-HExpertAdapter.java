package com.sabrcare.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sabrcare.app.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.sabrcare.app.activities.HExpertActivity.hExpert;

public class HExpertAdapter extends RecyclerView.Adapter<HExpertAdapter.HExpertVH> {



    @NonNull
    @Override
    public HExpertAdapter.HExpertVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HExpertAdapter.HExpertVH(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_add_symptom,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HExpertAdapter.HExpertVH holder, final int position) {
            holder.addSymptom(hExpert.get(position).name);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.checkBox.isChecked()){
                        hExpert.get(position).isCheck=1;
                    }
                    else{
                        hExpert.get(position).isCheck=0;
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return hExpert.size();
    }

    class HExpertVH extends RecyclerView.ViewHolder {

        private TextView hexpertname;
        private CheckBox checkBox;

        HExpertVH(@NonNull View itemView) {
            super(itemView);
            hexpertname = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        private void addSymptom(final String symptom){
            hexpertname.setText(symptom);
        }

    }
}
