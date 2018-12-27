package com.example.suguoqing.expandablekey;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KeyListAdapter extends RecyclerView.Adapter<KeyListAdapter.ViewHolder> {
    private List<String> key_list;
    private Context mContext;
    private CallBackListener mListener;


    public interface CallBackListener{
        public void onItemClick(String name,int keyCode);
    }

    public void setmListener(CallBackListener mListener) {
        this.mListener = mListener;
    }

    public KeyListAdapter(List<String> key_list, Context context) {
        this.key_list = key_list;
        mContext = context;

    }

    @Override
    public KeyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final KeyListAdapter.ViewHolder holder, final int position) {
        //添加点击事件
        holder.key_text.setText(key_list.get(position)+"\n"+mContext.getResources().getString(R.string.left_kh)+
                position+mContext.getResources().getString(R.string.right_kh));

        holder.key_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(holder.key_text.getText().toString(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return key_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View key_item;
        TextView key_text;

        public ViewHolder(View itemView) {
            super(itemView);
            key_item = itemView;
            key_text = itemView.findViewById(R.id.key_item_context);
        }
    }

}
