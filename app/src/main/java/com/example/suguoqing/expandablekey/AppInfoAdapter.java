package com.example.suguoqing.expandablekey;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {
    private static final String TAG = "AppInfoAdapter";
    private AppCallBackListener listener;
    private List<AppInfo> list;
    private Context mContext;
    private List<Boolean> isChecked=new ArrayList<>();
    private String key;//用来判断是否是F1，F2这两个键，F1默认是计算器，f2默认是联系人



    public void setListener(AppCallBackListener listener) {
        this.listener = listener;
    }

    public AppInfoAdapter(List<AppInfo> list, Context mContext,String key) {
        this.list = list;
        this.mContext = mContext;
        this.key = key;
        for(int i=0;i<list.size();i++){
            isChecked.add(false);
            if("f1".equals(key)){
                if(mContext.getResources().getString(R.string.calculator).equals(list.get(i).getPackageName())){
                    Log.d(TAG, "AppInfoAdapter: suguoqing");
                    isChecked.set(i,true);
                }
            }else if("f2".equals(key)){
                if(mContext.getResources().getString(R.string.contacts).equals(list.get(i).getPackageName())){
                    Log.d(TAG, "AppInfoAdapter: suguoqing2");
                    isChecked.set(i,true);
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.short_cut_item,parent,false);
        AppInfoAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.imageView.setImageDrawable(list.get(position).getIcon());
        holder.textView.setText(list.get(position).getName());
        holder.ok.setChecked(isChecked.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked.clear();
                for(int i=0;i<list.size();i++){
                    isChecked.add(false);
                    if(position==i){
                        isChecked.add(position,true);
                        listener.appItemOnClick(position,list);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView textView;
       // ImageView imgOk;
        RadioButton ok;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.shortcut_icon);
            textView = itemView.findViewById(R.id.shortcut_name);
            ok =itemView.findViewById(R.id.shortcut_ok);
        }
    }


    public interface AppCallBackListener{
        public void appItemOnClick(int postion,List<AppInfo> list);
    }
    
}
