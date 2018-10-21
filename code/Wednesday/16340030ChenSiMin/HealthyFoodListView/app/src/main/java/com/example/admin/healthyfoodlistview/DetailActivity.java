package com.example.admin.healthyfoodlistview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class DetailActivity extends AppCompatActivity {
    private Collection collection;
    private boolean fullStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        // 接收数据
        Bundle bundle = getIntent().getExtras();
        collection = (Collection) bundle.get("data");
        collection.setIs_collected(false);
        fullStar = false;
        Init();
    }

    void Init() {
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(Color.parseColor(collection.getBg_color()));

        ((TextView)findViewById(R.id.food_type)).setText(collection.getFood_type());
        ((TextView)findViewById(R.id.nutrients)).setText("富含 "+collection.getNutrients());

        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setBackgroundColor(Color.parseColor(collection.getBg_color()));

        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText(collection.getName());

        ListView operationList = findViewById(R.id.operation);
        String[] operations = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(DetailActivity.this, R.layout.operation, operations);
        operationList.setAdapter(arrayAdapter);
    }

    public void backButtonOnClick(View target) {
        // 现在使用Eventbus传递，这里就不需要返回参数了，直接finish
        finish();
//        if (!collection.getIs_collected())
//            finish();
//        else {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("data", collection);
//            Intent mIntent = new Intent();
//            mIntent.putExtras(bundle);
//            setResult(RESULT_OK, mIntent);
//            finish();
//        }
    }

    public void startButtonOnClick(View target) {
        if (fullStar) {
            target.setBackgroundResource(R.drawable.empty_star);
            fullStar = false;
        } else {
            target.setBackgroundResource(R.drawable.full_star);
            fullStar = true;
        }
    }

    public void CollectImgOnClick(View target) {
        collection.setIs_collected(true);
        Toast.makeText(getApplication(), "已收藏", Toast.LENGTH_SHORT).show();
        // 发送动态广播
        sendDynamicBroadcast();
        // 传递事件
        EventBus.getDefault().post(new MessageEvent(collection));
    }

    protected void sendDynamicBroadcast() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", collection);
        Intent intentBroadcast = new Intent(DynamicReceiver.DYNAMICACTION);
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);
    }
}
