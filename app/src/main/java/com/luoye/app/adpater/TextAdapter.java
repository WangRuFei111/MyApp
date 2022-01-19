package com.luoye.app.adpater;

import android.content.Context;

import com.luoye.app.base.BaseAdapter;
import com.luoye.app.databinding.AdapterTextBinding;
import java.util.ArrayList;

/**
 * created by: ls
 * TIME：2021/11/19
 * user：
 */
public class TextAdapter<T> extends BaseAdapter<T, AdapterTextBinding> {


    public TextAdapter(Context context, ArrayList<T> objectArrayList) {
        super(context, objectArrayList);

    }

    @Override
    protected void initAdapter(ViewHolder holder, int position) throws Exception {

        holder.binding.text.setOnClickListener(view -> {
            if (onItemClickListener != null)
                onItemClickListener.Info(objectArrayList.get(position), position);
        });
    }
}
