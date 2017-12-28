package com.example.simple_soul.weixinchat.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simple_soul.weixinchat.R;
import com.example.simple_soul.weixinchat.model.Bean;
import com.example.simple_soul.weixinchat.utils.ImageUtils;
import com.example.simple_soul.weixinchat.utils.PreUtils;
import com.example.simple_soul.weixinchat.utils.RedUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button save_title, choose, add_member, bind, add_event, clear;
    private EditText title, member_name, content, count, person;
    private Spinner sp_me, sp_member, sp_event;
    private ImageView head;
    private LinearLayout ly_count, ly_person;

    private List<Bean> beans = new ArrayList<>();
    private Map<String, String> memberInfo = new HashMap<>();
    private List<String> memberList = new ArrayList<>(), eventList;
    private ArrayAdapter<String> memberAdapter, eventAdapter;
    private Gson gson = new Gson();
    private String imageUri;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initData();
    }

    private void initView()
    {
        save_title = findViewById(R.id.setting_btn_save);
        choose = findViewById(R.id.setting_btn_choose);
        add_member = findViewById(R.id.setting_btn_member);
        bind = findViewById(R.id.setting_btn_bind);
        add_event = findViewById(R.id.setting_btn_event);
        clear = findViewById(R.id.setting_btn_clear);

        title = findViewById(R.id.setting_edit_title);
        member_name = findViewById(R.id.setting_edit_name);
        content = findViewById(R.id.setting_edit_content);
        count = findViewById(R.id.setting_edit_count);
        person = findViewById(R.id.setting_edit_person);

        sp_me = findViewById(R.id.setting_sp_me);
        sp_member = findViewById(R.id.setting_sp_member);
        sp_event = findViewById(R.id.setting_sp_event);

        head = findViewById(R.id.setting_image_head);

        ly_count = findViewById(R.id.setting_ly_count);
        ly_person = findViewById(R.id.setting_ly_person);

        save_title.setOnClickListener(this);
        choose.setOnClickListener(this);
        add_member.setOnClickListener(this);
        bind.setOnClickListener(this);
        add_event.setOnClickListener(this);
        clear.setOnClickListener(this);

        sp_event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 1)
                {
                    ly_count.setVisibility(View.VISIBLE);
                    ly_person.setVisibility(View.VISIBLE);
                }
                else
                {
                    ly_count.setVisibility(View.GONE);
                    ly_person.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initData()
    {
        eventList = new ArrayList<>();
        eventList.add("发信息");
        eventList.add("发红包");
        eventList.add("系统信息");

        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                eventList);
        sp_event.setAdapter(eventAdapter);

        memberList = new ArrayList<>();

        //初始化以前的数据
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

        memberList.addAll(memberInfo.keySet());

        memberAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                memberList);

        sp_member.setAdapter(memberAdapter);
        sp_me.setAdapter(memberAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "取消从相册选择", Toast.LENGTH_LONG).show();
        }
        else if (requestCode == 12)
        {
            try
            {
                Uri uri = data.getData();
                Log.e("main", uri.toString());
                head.setImageBitmap(ImageUtils.getBitmapFormUri(this, uri));
                imageUri = uri.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        Type type = null;
        switch (v.getId())
        {
            case R.id.setting_btn_save:
                PreUtils.setString(this, "title", title.getText().toString());
                Toast.makeText(SettingActivity.this, "已保存", Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting_btn_choose:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, 12);
                break;

            case R.id.setting_btn_member:
                type = new TypeToken<Map<String, String>>() {}.getType();
                memberInfo.clear();
                if (gson.fromJson(PreUtils.getString(this, "member", ""), type) != null)
                {
                    memberInfo = gson.fromJson(PreUtils.getString(this, "member", ""), type);
                    Log.i("main", "Pre中的memberInfo=====>" + PreUtils.getString(this, "member", ""));
                }
                memberInfo.put(member_name.getText().toString(), imageUri);
                memberList.clear();
                memberList.addAll(memberInfo.keySet());
                memberAdapter.notifyDataSetChanged();
                PreUtils.setString(this, "member", gson.toJson(memberInfo));
                Log.i("main",
                        "memberInfo==>" + memberInfo.toString() + ", gson----->" + gson.toJson(
                                memberInfo) + "memberInfo======>" + memberInfo.toString());
                Toast.makeText(SettingActivity.this, "成员已添加："+ member_name.getText().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting_btn_bind:
                PreUtils.setString(this, "me", sp_me.getSelectedItem().toString());
                Log.i("main", "我的名字：" + sp_me.getSelectedItem().toString());
                Toast.makeText(SettingActivity.this, "已绑定："+sp_me.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting_btn_event:
                String me = PreUtils.getString(this, "me", "");
                if (me.equals(""))
                {
                    Toast.makeText(this, "请先在成员中选择一个为“我”", Toast.LENGTH_LONG).show();
                }
                else
                {
                    type = new TypeToken<List<Bean>>() {}.getType();
                    if (gson.fromJson(PreUtils.getString(this, "beanList", ""), type) != null)
                    {
                        beans = gson.fromJson(PreUtils.getString(this, "beanList", ""), type);
                    }
                    switch (sp_event.getSelectedItemPosition())
                    {
                        //发信息
                        case 0:
                        {
                            if (me.equals(sp_member.getSelectedItem().toString()))
                            {
                                beans.add(new Bean(0, memberInfo.get(me), me,
                                        content.getText().toString()));
                            }
                            else
                            {
                                beans.add(new Bean(1,
                                        memberInfo.get(sp_member.getSelectedItem().toString()),
                                        sp_member.getSelectedItem().toString(),
                                        content.getText().toString()));
                            }
                            break;
                        }
                        //发红包
                        case 1:
                            int c = Integer.parseInt(count.getText().toString());
                            int p = Integer.parseInt(person.getText().toString());
                            //发送人名字
                            String n = sp_member.getSelectedItem().toString();
                            float money = Integer.parseInt(content.getText().toString());
                            List<Float> reds = RedUtils.getRed(money, c);
                            //自己发的红包
                            if (me.equals(n))
                            {
                                beans.add(
                                        new Bean(3, 1, c, p,
                                                memberInfo.get(me), me,
                                                content.getText().toString()
                                        ));

                                for (int i = 0; i < p; i++)
                                {
                                    String s = memberList.get(i);
                                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                                    Date curDate = new Date(
                                            System.currentTimeMillis());//获取当前时间
                                    String str = formatter.format(curDate);
                                    Log.i("main", "获取时间--------------------->"+str);
                                    if (s.equals(me))
                                    {
                                        beans.add(
                                                new Bean(4, memberInfo.get(s), s, "你领取了" + me + "的", str,
                                                        String.valueOf(reds.get(i))));
                                    }
                                    else
                                    {
                                        beans.add(new Bean(4, memberInfo.get(s), s, s + "领取了你的", str, String.valueOf(reds.get(i))));
                                    }
                                }
                            }
                            //别人发的红包
                            else
                            {
                                beans.add(
                                        new Bean(3, 2, c, p,
                                                memberInfo.get(
                                                        sp_member.getSelectedItem().toString()),
                                                sp_member.getSelectedItem().toString(),
                                                content.getText().toString()));
                                for (int i = 0; i < p; i++)
                                {
                                    String s = memberList.get(i);
                                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                                    Date curDate = new Date(
                                            System.currentTimeMillis());//获取当前时间
                                    String str = formatter.format(curDate);
                                    if (s.equals(me))
                                    { beans.add(new Bean(4, memberInfo.get(s), s, "你领取了" + n + "的", str, String.valueOf(reds.get(i)))); }
                                    else
                                    {
                                        beans.add(new Bean(4, memberInfo.get(s), s, s + "领取了" + n + "的", str, String.valueOf(reds.get(i))));
                                    }
                                }
                                Log.i("main", "Setting生成的bean----------->"+gson.toJson(beans));
                            }
                            break;
                        //系统信息
                        case 2:
                            beans.add(new Bean(2, null, "system",
                                    content.getText().toString()));
                            break;
                    }
                }
                PreUtils.setString(this, "beanList", gson.toJson(beans));
                Toast.makeText(SettingActivity.this, "事件添加成功", Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting_btn_clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定清空么？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        PreUtils.remove(SettingActivity.this, "member");
                        PreUtils.remove(SettingActivity.this, "title");
                        PreUtils.remove(SettingActivity.this, "me");
                        PreUtils.remove(SettingActivity.this, "beanList");
                        Toast.makeText(SettingActivity.this, "已清空", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }
}
