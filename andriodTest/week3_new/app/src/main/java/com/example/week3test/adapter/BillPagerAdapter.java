package com.example.week3test.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.week3test.fragment.BillFragment;

public class BillPagerAdapter extends FragmentPagerAdapter {
    private final int mYear;

    public BillPagerAdapter(@NonNull FragmentManager fm, int year) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mYear = year;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //传输月份

        int month = position + 1;

        // 9 - 09;  10- 10
        String zeroMonth = month < 10 ? "0" + month : String.valueOf(month);
        String yearMonth = mYear + "-" + zeroMonth;
        //打印看看
        Log.d("ning", yearMonth);
        return BillFragment.newInstance(yearMonth);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "月份" ;
    }
}
