package com.exbawei.liteli.pinglun;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.autonavi.ae.pos.LocManager.init;
import static com.exbawei.liteli.pinglun.R.id.gridView;
import static com.exbawei.liteli.pinglun.R.id.textView;
import static com.exbawei.liteli.pinglun.R.id.tv;


public class MainActivity extends AppCompatActivity implements MyDialog.OnButtonClickListener, AdapterView.OnItemClickListener {

    private Button button2;
    private TextView tv;
    private EditText et;

    MyDialog2 myDialog2;
    private TextView wei;
    private TextView shei;
    int num=200;
    private MyDialog dialog;// 图片选择对话框
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private String fileName = " hello.txt";
    private GridView gridView; // 网格显示缩略图
    private final int IMAGE_OPEN = 4; // 打开图片标记
    private String pathImage; // 选择图片路径
    private Bitmap bmp; // 导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter; // 适配器

   int res[] = new int[]{R.drawable.raw_1500020546};
    List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
    private ImageView ivfan;
    //Handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        dialog = new MyDialog(this);
        dialog.setOnButtonClickListener(this);
        LayoutInflater layout = this.getLayoutInflater();
        View view = layout.inflate(R.layout.layout_select_photo, null);

        bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.raw_1500020546); // 加号
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this, imageItem,
                R.layout.item, new String[] { "itemImage" },
                new int[] { R.id.imageView1 });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {

                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(simpleAdapter);
    }
    private void initView() {
        ivfan = (ImageView) findViewById(R.id.ivfan);
        button2 = (Button) findViewById(R.id.button2);
        et = (EditText) findViewById(R.id.et);
        wei = (TextView) findViewById(R.id.wei);
        shei = (TextView) findViewById(R.id.shei);
        tv = (TextView) findViewById(R.id.tv);

        ivfan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              myDialog2 =new MyDialog2(MainActivity.this);
                myDialog2.show();
                myDialog2.setOnButton(new MyDialog2.OnButton() {
                    @Override
                    public void camera() {
                        myDialog2.cancel();
//
                        Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                        String fileContent = et.getText().toString();
                        try {
                            save(fileContent);

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }


                    @Override
                    public void cancel() {
                        myDialog2.dismiss();
                        Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_LONG).show();
                    }


                });
            }
        });
        try {
            String content = read();
            et.setText(content);
        } catch (IOException e) {
            e.printStackTrace();

        }
      button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Toast.makeText(MainActivity.this,"发表成功",Toast.LENGTH_LONG).show();

        Intent intent=new Intent(MainActivity.this,Main4Activity.class);
        intent.putExtra("aaa",et.getText().toString());
        startActivity(intent);

    }
});


        shei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(intent);


            }
        });
        Main3Activity.getData(new Main3Activity.Data() {
            @Override
            public void setData(String str) {
                shei.setText(str);
            }
        });
        wei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
        Main2Activity.getPian(new Main2Activity.Pian() {
            @Override
            public void setPian(String str) {
                wei.setText(str);
            }
        });
        for (int i=0;i<res.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("imageView",res[i]);
            data.add(map);
        }

        et.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        tv.setText("剩余："+(num-editable.length())+"字");
    }
});

    }

    @Override
    public void camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, PHOTOHRAPH);
    }

    @Override
    public void gallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_OPEN);

    }

    @Override
    public void cancel() {

        dialog.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                // 将图片放入gridview中
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("itemImage", photo);
                imageItem.add(map);
                simpleAdapter = new SimpleAdapter(this, imageItem,
                        R.layout.item, new String[] { "itemImage" },
                        new int[] { R.id.imageView1 });
                simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {

                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView i = (ImageView) view;
                            i.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });
                gridView.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

        }
        // 打开图片
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            startPhotoZoom(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {

        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this, imageItem,
                    R.layout.item, new String[] { "itemImage" },
                    new int[] { R.id.imageView1 });
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {

                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();

            pathImage = null;
            dialog.dismiss();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        if (imageItem.size() == 10) { // 第一张为默认图片
            Toast.makeText(MainActivity.this, "图片数9张已满",
                    Toast.LENGTH_SHORT).show();
        } else if (position == 0) { // 点击图片位置为+ 0对应0张图片
            // 选择图片
            dialog.show();

            // 通过onResume()刷新数据
        } else {
            dialog(position);
        }

    }

    /*
     * Dialog对话框提示用户删除操作 position为删除图片位置
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }
            public  static Fa fa;
            public static void getbiao(Fa fa1){
                MainActivity.fa=fa1;
            }

    public interface Fa{
        void biao(String str);
    }
    public void save(String fileContent) throws Exception {


        FileOutputStream output = this.openFileOutput(fileName, Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());
        output.close();
    }

    public String read() throws IOException {
        //打开文件输入流
        FileInputStream input = this.openFileInput(fileName);
        byte[] temp = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer("");
        int len = 0;
        while ((len = input.read(temp)) > 0) {
            stringBuffer.append(new String(temp, 0, len));
        }
        //关闭输入流
        input.close();
        return stringBuffer.toString();
    }
}
