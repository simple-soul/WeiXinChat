package com.example.simple_soul.weixinchat.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/6.
 */

public class PreUtils
{
    public static void setString(Context context, String key, String value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void remove(Context context, String key)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }

    public static void setInt(Context context, String key, int value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static void setLong(Context context, String key, long value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getLong(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }
}
