package com.example.week3test.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.week3test.R;
import com.example.week3test.fragment.LaunchFragment;

public class LaunchImproveAdapter extends FragmentPagerAdapter {


    private final int[] mImageArray;

    public LaunchImproveAdapter(@NonNull FragmentManager fm, int[] imageArray) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mImageArray = imageArray;
    }

    // 每翻一页都需要通过这个方法new出来
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LaunchFragment.newInstance(mImageArray.length, position, mImageArray[position]);
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }
}
