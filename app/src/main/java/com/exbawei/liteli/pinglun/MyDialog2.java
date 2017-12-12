package com.exbawei.liteli.pinglun;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by li te li on 2017/12/5.
 */

public class MyDialog2 extends Dialog implements OnClickListener {
    public MyDialog2(Context context) {
        super(context);
        setContentView(R.layout.photo);
        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
        findViewById(R.id.btn_camera).setOnClickListener(this);
      //  findViewById(R.id.btn_gallery).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.btn_camera:
               onButton.camera();
            break;
            case R.id.btn_cancel:
                onButton.cancel();
                break;

        }

    }
        private OnButton onButton;

       public OnButton getOnButton(){
           return onButton;
       }
       public  void setOnButton(OnButton onButton){
           this.onButton=onButton;
       }
    public  interface OnButton{
        void camera();
        void cancel();


    }
}
