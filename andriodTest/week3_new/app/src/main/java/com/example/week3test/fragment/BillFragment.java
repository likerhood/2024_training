package com.example.week3test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.week3test.R;
import com.example.week3test.adapter.BillListAdapter;
import com.example.week3test.database.BillDBHelper;
import com.example.week3test.utils.BillInfo;

import java.util.List;

public class BillFragment extends Fragment {

    public static BillFragment newInstance(String yearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("yearMonth", yearMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView lv_bill = view.findViewById(R.id.lv_bill);
        // 查询记录
        BillDBHelper mDBHelper = BillDBHelper.getInstance(getContext());
        //String yearMonth = "2024-06";
        Bundle arguments = getArguments();
        String yearMonth = arguments.getString("yearMonth");
        List<BillInfo> billInfoList = mDBHelper.queryByMonth(yearMonth);
        // 适配器
        BillListAdapter adapter = new BillListAdapter(getContext(), billInfoList);
        lv_bill.setAdapter(adapter);
        return view;
    }
}