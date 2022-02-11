package com.sunny.intentattackdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private boolean isAddTryCatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null) {
            try {
                String params1 = intent.getStringExtra("params1");
                Log.e("sunny", "params1:" + params1);
            } catch (Exception e) {
                e.printStackTrace();
            }catch (Throwable throwable){
                Log.e("sunny","t:"+throwable.getMessage());
            }

        }
    }

    public void addTryCatch(View view) {

    }
}