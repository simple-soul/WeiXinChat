package com.example.simple_soul.weixinchat.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simple_soul.weixinchat.R;
import com.example.simple_soul.weixinchat.model.Bean;
import com.example.simple_soul.weixinchat.utils.ImageUtils;
import com.example.simple_soul.weixinchat.utils.PreUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by simple_soul on 2017/12/28.
 */

public class RedAdapter extends BaseAdapter
{
    private Activity context;
    private List<Bean> beans;
    private List<Bean> beanList;
    private String me;
    private Gson gson = new Gson();

    public RedAdapter(Activity context, List<Bean> beans)
    {
        this.context = context;
        this.beans = beans;
        me = PreUtils.getString(context, "me", "");
    }

    @Override
    public int getCount()
    {
        return beans.size();
    }

    @Override
    public Object getItem(int position)
    {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Bean bean = beans.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_list, null);
            viewHolder.layout = convertView.findViewById(R.id.list_layout);
            viewHolder.head = convertView.findViewById(R.id.item_image_red_head);
            viewHolder.name = convertView.findViewById(R.id.list_tv_name);
            viewHolder.time = convertView.findViewById(R.id.list_tv_time);
            viewHolder.remark = convertView.findViewById(R.id.list_tv_remark);
            viewHolder.money = convertView.findViewById(R.id.list_tv_money);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try
        {
            viewHolder.head.setImageBitmap(ImageUtils.getBitmapFormUri(context, Uri.parse(bean.picture)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        viewHolder.name.setText(bean.name);
        viewHolder.money.setText(bean.remark);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = View.inflate(context, R.layout.dialog, null);
                final EditText remark = view.findViewById(R.id.dialog_edit_remark);
                final EditText time = view.findViewById(R.id.dialog_edit_time);
                final EditText money = view.findViewById(R.id.dialog_edit_money);
                builder.setNegativeButton("保存",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Type type = null;
                                type = new TypeToken<List<Bean>>() {}.getType();
                                if (gson.fromJson(PreUtils.getString(context, "beanList", ""), type) != null)
                                {
                                    beanList = gson.fromJson(PreUtils.getString(context, "beanList", ""), type);
                                }

                                for (Bean b : beanList)
                                {
                                    if(b.name.equals(bean.name) && b.flag == bean.flag)
                                    {
                                        if(!remark.getText().toString().equals(""))
                                        {
                                            b.liuyan = remark.getText().toString();
                                            bean.liuyan = remark.getText().toString();
                                        }
                                        if(!time.getText().toString().equals(""))
                                        {
                                            b.time = time.getText().toString();
                                            bean.time = time.getText().toString();
                                        }
                                        if(!money.getText().toString().equals(""))
                                        {
                                            b.remark = money.getText().toString();
                                            bean.remark = money.getText().toString();
                                        }
                                        break;
                                    }
                                }
                                PreUtils.setString(context, "beanList", gson.toJson(beanList));

                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }

                );
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        if(bean.name.equals(me))
        {
            if (bean.liuyan != null)
            {
                viewHolder.remark.setVisibility(View.VISIBLE);
                viewHolder.remark.setText(bean.liuyan);
            }
            else
            {
                viewHolder.remark.setVisibility(View.GONE);
                viewHolder.time.setText("留言");
                viewHolder.time.setTextColor(Color.rgb(92, 116, 159));
            }
        }
        else
        {
            viewHolder.time.setText(bean.time);
        }

        return convertView;
    }

    class ViewHolder
    {
        public ImageView head;
        public TextView name, remark, time, money;
        public LinearLayout layout;
    }
}
