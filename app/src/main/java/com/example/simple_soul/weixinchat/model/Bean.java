package com.example.simple_soul.weixinchat.model;

/**
 * Created by simple_soul on 2017/12/26.
 */

public class Bean
{
    //0 本人 1 其他人 2 系统通知 3 红包 4 红包系统通知
    public int flag;
    //0 无 1 本人 2 其他人
    public int red = 0;
    public int all = 0;
    public int already = 0;
    public String picture = null;
    public String name;
    public String chatting;
    public String time;
    public String remark;
    public String liuyan;

    public Bean(int flag, String picture, String name, String chatting)
    {
        this.flag = flag;
        this.picture = picture;
        this.name = name;
        this.chatting = chatting;
    }
    public Bean(int flag, String picture, String name, String chatting, String time, String remark)
    {
        this.flag = flag;
        this.picture = picture;
        this.name = name;
        this.chatting = chatting;
        this.time = time;
        this.remark = remark;
    }

    public Bean(int flag, int red, int all, int already, String picture, String name, String chatting)
    {
        this.flag = flag;
        this.red = red;
        this.all = all;
        this.already = already;
        this.picture = picture;
        this.name = name;
        this.chatting = chatting;
    }

    @Override
    public String toString()
    {
        return "Bean{" +
                "flag=" + flag +
                ", red=" + red +
                ", all=" + all +
                ", already=" + already +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", chatting='" + chatting + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
