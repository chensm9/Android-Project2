package com.example.admin.healthyfoodlistview;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;


public class MainActivity extends AppCompatActivity {
    final List<Collection> data = new ArrayList<>();;
    final List<Collection> collectionList = new ArrayList<>();
    final MyListViewAdapter myListViewAdapter = new MyListViewAdapter(MainActivity.this, collectionList);
    final MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter<Collection>(MainActivity.this, R.layout.list_item, data) {
        @Override
        public void convert(MyViewHolder holder, Collection s) {
            TextView name = holder.getView(R.id.name);
            name.setText(s.getName());
            TextView tag = holder.getView(R.id.tag);
            tag.setText(s.getTag());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initRecyclerView();
        initListView();
    }

    protected void initData(){
        try {
            InputStream is = getAssets().open("FoodInfo.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String str;
            reader.readLine();
            while ((str = reader.readLine()) != null) {
                String info[] = str.split("\\s+");
                data.add(new Collection(info[0], info[1], info[2], info[3], info[4]));
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void initListView() {
        ListView listView = findViewById(R.id.listView);
        // 头布局
        View Header = View.inflate(this, R.layout.list_item, null);
        ((TextView)Header.findViewById(R.id.name)).setText("收藏夹");
        ((TextView)Header.findViewById(R.id.tag)).setText(" * ");
        Header.findViewById(R.id.line).setVisibility(View.VISIBLE);
        // 放入Header并设置不可点击
        listView.addHeaderView(Header, null, false);

        listView.setAdapter(myListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 处理单击事件
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", collectionList.get(i-1));
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                // 处理长按事件
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                final TextView name = view.findViewById(R.id.name);
                dialog.setTitle("删除").setMessage("确认删除" + name.getText());
                // 设置对话框确认按钮
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplication(), "删除"+name.getText(), Toast.LENGTH_SHORT).show();
                        collectionList.remove(position - 1);
                        myListViewAdapter.notifyDataSetChanged();
                    }
                });
                // 设置对话框取消按钮
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public  void onClick(DialogInterface dialogInterface,int i){
                        Toast.makeText(getApplication(), "取消删除", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.create().show();
                return true;
            }
        });
    }

    protected void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onLongClick(final int position) {
                Toast.makeText(getApplication(), "删除" + data.get(position).getName(),
                        Toast.LENGTH_SHORT).show();
                data.remove(position);
                myRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(myRecyclerViewAdapter);
        scaleInAnimationAdapter.setDuration(500);
        recyclerView.setAdapter((scaleInAnimationAdapter));
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());
    }

    public void floatingButtonOnClick(View target) {
        ListView listView = findViewById(R.id.listView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton button = (FloatingActionButton) target;
        if (listView.getVisibility() == View.INVISIBLE) {
            button.setImageResource(R.drawable.collect);
            recyclerView.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        } else {
            button.setImageResource(R.drawable.mainpage);
            recyclerView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Collection c = (Collection) bundle.get("data");
            collectionList.add(c);
            myListViewAdapter.refreshList(collectionList);
        }
    }
}
