package com.example.week3test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week3test.R;
import com.example.week3test.enity.Planet;

import java.util.List;

public class PlanetBaseAdapter extends BaseAdapter {

    // 上下文
    private Context mContext;
    // 映射信息列表
    private List<Planet> mPlanetList;

    public PlanetBaseAdapter(Context mContext, List<Planet> mPlanetList) {
        this.mContext = mContext;
        this.mPlanetList = mPlanetList;
    }

    // 集合的元素——列表项的个数
    @Override
    public int getCount() {
        return mPlanetList.size();
    }

    // 返回列表项
    @Override
    public Object getItem(int position) {
        return mPlanetList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_base, parent, false);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Planet planet = mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }

}
