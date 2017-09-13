package com.warden.mobilecampus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.warden.mobilecampus.view.fragment.NewsListFragment;

import java.util.List;

/**
 * Created by Warden on 2017/9/12.
 * 首页ViewPage适配器
 */

public class NewsAdapter extends FragmentPagerAdapter {
    private List<String> tabs;

    public NewsAdapter(FragmentManager fm,List<String> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    private void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(tabs.get(position));
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
