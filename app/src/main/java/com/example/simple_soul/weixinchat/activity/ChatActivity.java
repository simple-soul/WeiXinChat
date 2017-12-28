package com.example.simple_soul.weixinchat.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simple_soul.weixinchat.R;
import com.example.simple_soul.weixinchat.adapter.MyListAdapter;
import com.example.simple_soul.weixinchat.fragment.EmotionMainFragment;
import com.example.simple_soul.weixinchat.model.Bean;
import com.example.simple_soul.weixinchat.utils.PreUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity
{
    private TextView title;
    private ImageView back, person, people;
    private ListView listView;
    private EmotionMainFragment emotionMainFragment;

    private List<Bean> beans = new ArrayList<>();
    private Map<String, String> memberInfo = new HashMap<>();
    private MyListAdapter myListAdapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView()
    {
        title = findViewById(R.id.bar_tv_title);
        back = findViewById(R.id.bar_image_back);
        person = findViewById(R.id.bar_image_person);
        people = findViewById(R.id.bar_image_people);
        listView = findViewById(R.id.chat_list);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }

    /**
     * 初始化布局数据
     */
    private void initData()
    {
        title.setText(PreUtils.getString(this, "title", "张津涤"));

        initEmotionMainFragment();
//        beans.add(new Bean(2, null, "2","14:15"));
//        beans.add(new Bean(1, null, "1","在不在"));
//        beans.add(new Bean(0, null, "0","干嘛事"));
//        beans.add(new Bean(1, null, "1","有没有好东西啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"));
//        beans.add(new Bean(0, null, "0","要啥有啥，要啥直说"));
//        beans.add(new Bean(2, null, "2","系统通知：您已被封号三天"));
//        beans.add(new Bean(1, null, "1","赶快赶快，有啥发啥"));
//        beans.add(new Bean(2, null, "2","14:23"));
//        beans.add(new Bean(1, null, "1","你丫到底发不发"));
//        beans.add(new Bean(1, null, "1","人呢"));
        Type type = null;
        type = new TypeToken<List<Bean>>() {}.getType();
        if (gson.fromJson(PreUtils.getString(this, "beanList", ""), type) != null)
        {
            beans = gson.fromJson(PreUtils.getString(this, "beanList", ""), type);
        }

        type = new TypeToken<Map<String, String>>() {}.getType();
        if (gson.fromJson(PreUtils.getString(this, "member", ""), type) != null)
        {
            memberInfo = gson.fromJson(PreUtils.getString(this, "member", ""), type);
        }
        if(memberInfo.size() >= 3)
        {
            person.setVisibility(View.GONE);
            people.setVisibility(View.VISIBLE);
        }
        else
        {
            person.setVisibility(View.VISIBLE);
            people.setVisibility(View.GONE);
        }

        myListAdapter = new MyListAdapter(this, beans);
        listView.setAdapter(myListAdapter);

//        PreUtils.setString(this, "key", gson.toJson(beans));
//
//        Log.i("main", PreUtils.getString(this, "key", ""));
//
//        Type type = new TypeToken<List<Bean>>() {}.getType();
//        List<Bean> b = gson.fromJson(PreUtils.getString(this, "key", ""), type);
//
//        Log.i("main", b.get(1).chatting);
    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment()
    {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToContentView(listView);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.chat_frame, emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress())
        {
            super.onBackPressed();
        }
    }
}
