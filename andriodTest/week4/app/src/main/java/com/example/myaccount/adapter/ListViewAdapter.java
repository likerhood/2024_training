package com.example.myaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myaccount.MyApplication;
import com.example.myaccount.R;
import com.example.myaccount.entity.ListItem;

import java.util.List;

public class ListViewAdapter extends BaseAdapter{

    // 上下文
    private Context mContext;
    private List<ListItem> mListInfo;

    public ListViewAdapter(Context mContext, List<ListItem> mListInfo) {
        this.mContext = mContext;
        this.mListInfo = mListInfo;
    }

    @Override
    public int getCount() {
        return mListInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回指定位置的视图。这里是主要的部分，用于将数据绑定到视图上。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list,null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_category_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_date_remarks);
            holder.tv_account = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem info = mListInfo.get(position);
        holder.iv_icon.setImageResource(info.pic);
        holder.tv_name.setText(info.name);
        holder.tv_desc.setText(info.date + "   " + info.desc);
        holder.tv_account.setText("￥"+String.valueOf(info.account));

        // 设置金额文本的颜色
        boolean isIncome = MyApplication.getInstance().incomeCategoryMap.containsKey(info.name);
        if (isIncome) {
            holder.tv_account.setTextColor(ContextCompat.getColor(mContext, R.color.green)); // 绿色，表示收入
        } else {
            holder.tv_account.setTextColor(ContextCompat.getColor(mContext, R.color.red)); // 红色，表示支出
        }


        return convertView;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
        TextView tv_account;
    }


}


