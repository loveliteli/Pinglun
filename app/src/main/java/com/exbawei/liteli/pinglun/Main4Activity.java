package com.exbawei.liteli.pinglun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ImageView iv= (ImageView) findViewById(R.id.iv);
        final TextView tv2= (TextView) findViewById(R.id.tv2);
        Intent intent = getIntent();
        String aaa = intent.getStringExtra("aaa");
        tv2.setText(aaa);
//        String bbb = intent.getStringExtra("bbb");
//        iv.setImageResource(bbb);
    }
}
