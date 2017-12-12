package com.exbawei.liteli.pinglun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioGroup rg;
    private Button buque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();


    }

    private void initView() {
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rg = (RadioGroup) findViewById(R.id.rg);
        buque = (Button) findViewById(R.id.buque);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb1:
                data.setData(rb1.getText().toString());
                finish();
                break;
            case R.id.rb2:
                data.setData(rb2.getText().toString());
                finish();
                break;
            case R.id.rb3:
                data.setData(rb3.getText().toString());
                finish();
                break;
        }
    }
    public  static  Data data;

    public static void getData(Data str) {
        Main3Activity.data = str;
    }

    public  interface Data{
    void  setData(String str);
}
}
