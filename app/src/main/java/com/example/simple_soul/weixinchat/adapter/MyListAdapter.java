package com.example.simple_soul.weixinchat.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simple_soul.weixinchat.R;
import com.example.simple_soul.weixinchat.activity.RedActivity;
import com.example.simple_soul.weixinchat.model.Bean;
import com.example.simple_soul.weixinchat.utils.ImageUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by simple_soul on 2017/12/25.
 */

public class MyListAdapter extends BaseAdapter
{
    private Activity context;
    private List<Bean> beans;

    public MyListAdapter(Activity context, List<Bean> beans)
    {
        this.context = context;
        this.beans = beans;
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        TextView chatting = null;
        ImageView head = null, red = null;
        Bean bean = beans.get(position);
        try
        {
            switch (bean.flag)
            {
                //本人信息
                case 0:
                    convertView = View.inflate(context, R.layout.item_msg_me, null);
                    chatting = convertView.findViewById(R.id.item_tv_me);
                    head = convertView.findViewById(R.id.item_image_me);
                    chatting.setText(bean.chatting);
                    head.setImageBitmap(ImageUtils.getBitmapFormUri(context, Uri.parse(bean.picture)));
                    break;
                //别人信息
                case 1:
                    convertView = View.inflate(context, R.layout.item_msg_other, null);
                    chatting = convertView.findViewById(R.id.item_tv_other);
                    head = convertView.findViewById(R.id.item_image_other);
                    chatting.setText(bean.chatting);
                    head.setImageBitmap(ImageUtils.getBitmapFormUri(context, Uri.parse(bean.picture)));
                    break;
                //系统信息
                case 2:
                    convertView = View.inflate(context, R.layout.item_msg_system, null);
                    chatting = convertView.findViewById(R.id.item_tv_warning);
                    chatting.setText(bean.chatting);
                    break;
                //红包
                case 3:
                    if (bean.red == 1)
                    {
                        convertView = View.inflate(context, R.layout.item_red_me, null);
                        head = convertView.findViewById(R.id.item_image_me2);
                        red = convertView.findViewById(R.id.item_image_red_me);
                    }
                    else
                    {
                        convertView = View.inflate(context, R.layout.item_red_other, null);
                        head = convertView.findViewById(R.id.item_image_other2);
                        red = convertView.findViewById(R.id.item_image_red_other);
                    }
                    red.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent = new Intent(context, RedActivity.class);
                            intent.putExtra("pos", position);
                            context.startActivity(intent);
                        }
                    });
                    head.setImageBitmap(ImageUtils.getBitmapFormUri(context, Uri.parse(bean.picture)));
                    break;
                //红包系统提示
                case 4:
                    convertView = View.inflate(context, R.layout.item_red_system, null);
                    chatting = convertView.findViewById(R.id.item_tv_red);
                    chatting.setText(bean.chatting);
                    break;

                default:
                    Log.i("main", "出现了其他flag");
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return convertView;
    }
}
