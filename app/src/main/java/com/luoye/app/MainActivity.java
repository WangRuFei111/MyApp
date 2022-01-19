package com.luoye.app;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.luoye.app.base.BaseActivity;
import com.luoye.app.databinding.ActivityMainBinding;
import com.luoye.app.fragment.MainFragment;
import com.luoye.app.fragment.MyFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private MainFragment fragmentMain;
    private MyFragment fragmentMy;
    private Fragment currentFragment = new Fragment();//（全局）

    @Override
    protected void initActivity() throws Exception {
        fragmentMain = new MainFragment();
        fragmentMy = new MyFragment();
        switchFragment(fragmentMain).commit();

    }


    /*设置显示的Fragment*/
    private FragmentTransaction switchFragment(Fragment targetFragment) {
        //创建碎片化对象
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //判断是否是第一次添加
        if (!targetFragment.isAdded()) {
            //有页面的时候隐藏 当前页面
            if (currentFragment != null) transaction.hide(currentFragment);
            //添加新的页面
            transaction.add(R.id.frameLayout, targetFragment, targetFragment.getClass().getName());
        } else {
            //隐藏当前页面 显示需要显示的页面
            transaction.hide(currentFragment).show(targetFragment);
        }
        //转换显示的页面 为当前页面
        currentFragment = targetFragment;
        return transaction;
    }

}