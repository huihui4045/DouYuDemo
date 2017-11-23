package com.team.zhuoke.view.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team.zhuoke.R;

import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private Context mContext;
    private ViewHolder holder;
    private List<String> data;

    public MessageAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void NotifyAdapter(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_messageadapter, null);
            holder.tvcontent = (TextView) convertView.findViewById(R.id.tvcontent);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        if (position == 0) {
            holder.tvcontent.setTextColor(Color.parseColor("#ff4248"));
        } else {
            holder.tvcontent.setTextColor(Color.parseColor("#ffffff"));
        }
        holder.tvcontent.setText(data.get(position));
        return convertView;
    }

    private final class ViewHolder {
        TextView tvcontent;
    }
}