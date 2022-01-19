package com.luoye.sortadpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.List;

/**
 * created by: ls
 * TIME：2021/6/10
 * user：recyclerView 排序 添加 删除 适配器
 */
public class SortAdpater extends RecyclerView.Adapter<SortAdpater.ViewHolder> implements SortCallback.ItemTouchAdapter {

    private Context context;
    private List<String> listPath;
    private ViewHolder holders = null;
    private OnData onData;
    public SortAdpater(List<String> listPath) {
        this.listPath = listPath;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageButton imageButton;
        private ImageView iamegDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
            imageButton = itemView.findViewById(R.id.imageButton);
            iamegDelete = itemView.findViewById(R.id.iameg_delete);
        }
    }


    public void interfaceData(OnData onData) {
        this.onData = onData;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);

        holder.iamegDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                listPath.remove(pos);
                notifyDataSetChanged();
                //添加事件
                if (onData != null) {
                    onData.sortOnClick();
                }
            }
        });


        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加事件
                if (onData != null) {
                    onData.addOnCliock();
                }

            }
        });


        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (position == listPath.size()) {
            holder.imageButton.setVisibility(View.VISIBLE);
            holder.iamegDelete.setVisibility(View.GONE);
        } else {
            holder.imageButton.setVisibility(View.GONE);
            holder.iamegDelete.setVisibility(View.VISIBLE);
            Glide.with(context).setDefaultRequestOptions(new RequestOptions().frame(100).centerCrop()).load(listPath.get(position)).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return listPath.size() + 1;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition == listPath.size() || toPosition == listPath.size()) {
            return;
        }

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listPath, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listPath, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {

        if (position == listPath.size()) {
            return;
        }

        listPath.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void clearView() {
        if (onData != null) {
            onData.sortOnClick();
        }
    }
}
