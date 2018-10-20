package com.example.admin.healthyfoodlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private List<Collection> list;
    private Context context;

    public MyListViewAdapter(Context _context, List<Collection> _list) {
        this.list = _list;
        this.context = _context;
    }
    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 新声明一个View变量和ViewHoleder变量,ViewHolder类在下面定义。
        View convertView ;
        ViewHolder viewHolder;
        // 当view为空时才加载布局，否则，直接修改内容
        if (view == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.foodName = view.findViewById(R.id.name);
            viewHolder.foodTag = view.findViewById(R.id.tag);
            view.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
            convertView = view;
        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.foodName.setText(list.get(i).getName());
        viewHolder.foodTag.setText(list.get(i).getTag());
        // 将这个处理好的view返回
        return convertView;
    }

    public void addItem(Collection collection) {
        if (collection.getIs_collected()) {
            list.add(collection);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        if (position < list.size()) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    public void refreshList(List<Collection> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView foodName;
        public TextView foodTag;
    }
}
