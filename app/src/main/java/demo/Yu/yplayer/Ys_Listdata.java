package demo.Yu.yplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Ys_Listdata extends AppCompatActivity {

    private String value, contents;
    private static Handler mHandler;

    private Button button_play, button_collections;
    private TextView text_intro;
    private ImageView img_cover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ys_listdata);
        //获取Intent传递的数据
        value = getIntent().getStringExtra("key");
        initFindView();
        initOkHttp();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 200:
                        System.out.println("mHandle " + contents);
                        text_intro.setText(contents);
                }
            }
        };

    }

    private void initOnClick(){
        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Ys_Listdata.this, Fr_Video.class);
                intent.putExtra("key", contents);
                startActivity(intent);
            }
        });
        button_collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initFindView() {
        //初始化控件
        button_play = findViewById(R.id.button_play);
        button_collections = findViewById(R.id.button_collections);
        text_intro = findViewById(R.id.text_intro);
        img_cover = findViewById(R.id.img_cover);
    }

    public void initOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.w("initOkHttp", "初始化视频链接");
                    String path = "http://39.105.204.201/api/get_ysurl/?ysurl=" + URLEncoder.encode(value, "utf-8");
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(path)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        contents = response.body().string();
                        System.out.println(response.code());
                        if (response.code() == 200) {
                            System.out.println("response.code() == " + contents);
                            Message message = new Message();
                            mHandler.sendMessage(message);
                            message.what = response.code();
                        }
                        if (response.code() == 500) {
                            Log.w("initOkHttp", "response.code() = 500");
                            Toast.makeText(Ys_Listdata.this, "HTTP 500 内部服务器错误", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
