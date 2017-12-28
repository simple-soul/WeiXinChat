package com.example.simple_soul.weixinchat.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simple_soul.weixinchat.R;
import com.example.simple_soul.weixinchat.adapter.RedAdapter;
import com.example.simple_soul.weixinchat.model.Bean;
import com.example.simple_soul.weixinchat.utils.ImageUtils;
import com.example.simple_soul.weixinchat.utils.PreUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RedActivity extends Activity
{
    private ImageView head;
    private TextView name, money, remark, count, yuan;
    private ListView listView;
    private ImageView back;

    private Gson gson = new Gson();
    private List<Bean> beans = new ArrayList<>();
    private RedAdapter redAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        initView();
        intiData();
    }

    private void initView()
    {
        head = findViewById(R.id.red_image_head);
        name = findViewById(R.id.red_tv_name);
        money = findViewById(R.id.red_tv_money);
        remark = findViewById(R.id.red_tv_tv);
        count = findViewById(R.id.red_tv_count);
        yuan = findViewById(R.id.text_yuan);
        listView = findViewById(R.id.red_list);
        back = findViewById(R.id.bar_image_back3);


    }

    private void intiData()
    {
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);

        Type type = null;
        type = new TypeToken<List<Bean>>() {}.getType();
        if (gson.fromJson(PreUtils.getString(this, "beanList", ""), type) != null)
        {
            beans = gson.fromJson(PreUtils.getString(this, "beanList", ""), type);
        }

        Bean bean = beans.get(pos);

        count.setText("领取"+bean.already+"/"+bean.all+"个");
        try
        {
            head.setImageBitmap(ImageUtils.getBitmapFormUri(this, Uri.parse(bean.picture)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        name.setText(bean.name);

        List<Bean> beanList = this.beans.subList(pos+1, pos + this.beans.get(pos).already+1);
        Log.i("main", "RedActivity的beanList-===--->"+gson.toJson(beanList));
        String me = PreUtils.getString(this, "me", "");
        int mark = 0;
        for (Bean b : beanList)
        {
            if(b.name.equals(me))
            {
                money.setVisibility(View.VISIBLE);
                money.setText(b.remark);
                yuan.setVisibility(View.VISIBLE);
                remark.setVisibility(View.VISIBLE);
                mark++;
                break;
            }
        }
        if(mark == 0)
        {
            money.setVisibility(View.GONE);
            yuan.setVisibility(View.GONE);
            remark.setVisibility(View.GONE);
        }

        redAdapter = new RedAdapter(this, beanList);
        listView.setAdapter(redAdapter);
    }
}
