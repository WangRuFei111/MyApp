package com.luoye.app.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;


/**
 * created by: ls
 * TIME：2021/6/11
 * user：适配器基类
 */
public abstract class BaseAdapter<T, E extends ViewBinding> extends RecyclerView.Adapter<BaseAdapter<T, E>.ViewHolder> {

    protected String TAG = "---BaseAdapter";
    protected Context context;
    protected ArrayList<T> objectArrayList;
    protected OnItemClickListener<T> onItemClickListener;
    protected ViewHolder viewHolder;

    public BaseAdapter(Context context, ArrayList<T> objectArrayList) {
        this.context = context;
        this.objectArrayList = objectArrayList;
    }

    public void setObjectArrayList(ArrayList<T> objectArrayList) {
        this.objectArrayList = objectArrayList;
        notifyDataSetChanged();
    }

    public void setObjectArrayList(ArrayList<T> objectArrayList, Object... objects) {
        this.objectArrayList = objectArrayList;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        public E binding;

        public ViewHolder(@NonNull E binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ViewBindingUtil.create(getClass(), LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            initAdapter(holder, position);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "BaseAdapter_initAdapter_Exception:" + e.getMessage());
        }
    }

    protected abstract void initAdapter(ViewHolder holder, int position) throws Exception;

    @Override
    public int getItemCount() {
        return objectArrayList.size();
    }

}
