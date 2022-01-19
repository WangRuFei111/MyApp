package com.luoye.app.fragment;


import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luoye.app.Camera2Activity;
import com.luoye.app.adpater.TextAdapter;
import com.luoye.app.base.BaseFragment;
import com.luoye.app.base.BaseUtils;
import com.luoye.app.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class MainFragment extends BaseFragment<FragmentMainBinding> {


    @Override
    protected void initFragment() throws Exception {

        ArrayList<String> list = new ArrayList<>();
        list.add("测试数据1");
        list.add("相机2");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //瀑布流  StaggeredGridLayoutManager staggeredNewset = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //网格  GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);

        binding.recyclerView.setLayoutManager(linearLayoutManager);
        TextAdapter<String> textAdapter = new TextAdapter<>(context, list);
        binding.recyclerView.setAdapter(textAdapter);
        textAdapter.setOnItemClickListener((OnItemClickListener<String>) s -> {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            BaseUtils.startActivity(context,Camera2Activity.class);
        });
    }

}
