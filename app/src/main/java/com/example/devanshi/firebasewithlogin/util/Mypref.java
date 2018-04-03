package com.example.devanshi.firebasewithlogin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by Devanshi on 28-03-2018.
 */

public class Mypref extends AppCompatActivity
{
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public static final String mypreference = "mypref";
    public static final String FLAG = "flagKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Image = "imageKey";

    public void SharedPreferenceUtils(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }
    public void setValue(String key, String value) {
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.commit();
    }
    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void clear() {
        mSharedPreferencesEditor.clear().commit();
    }
    public void Glide(String value, final Context context, final ImageView image) {
        Glide.with(context).load(value).asBitmap().into(new BitmapImageViewTarget(image) {
            protected void setResource(Bitmap resource) {

                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                image.setImageDrawable(circularBitmapDrawable);
            }

        });
    }
}

