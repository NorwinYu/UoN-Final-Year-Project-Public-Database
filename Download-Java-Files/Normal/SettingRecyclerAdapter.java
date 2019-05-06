package com.msproject.myhome.mydays;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingRecyclerAdapter extends RecyclerView.Adapter<SettingRecyclerAdapter.ItemViewHolder> {
    ArrayList<SettingItem> mItems;
    Context context;


    public SettingRecyclerAdapter(ArrayList<SettingItem> items, Context context){
        mItems = items;
        this.context = context;
    }


    // 새로운 뷰 홀더 생성
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item,parent,false);
        return new ItemViewHolder(view);
    }


    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.itemName.setText(mItems.get(position).getTitle());
        holder.itemContent.setText(mItems.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction(position);
            }
        });
    }

    // 데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void doAction(int position){
        switch (position){
            case 0:
                Toast.makeText(context, "준비중입니다.", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent intent = new Intent(context, FunctionSettingActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    // 커스텀 뷰홀더
// item layout 에 존재하는 위젯들을 바인딩합니다.
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView itemName;
        private TextView itemContent;
        public final View mView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemContent = (TextView) itemView.findViewById(R.id.item_content);
            mView = itemView;
        }
    }
}

