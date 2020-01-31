package demo.Yu.yplayer;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import bean.GetUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;


public class Fr_Video extends AppCompatActivity {


    private String value, contents;
    private static Handler mHandler;
    private String onlineurls, downloads, m3u8urls, name;
    private VideoView myVideoView;
    StandardGSYVideoPlayer videoPlayer;

    OrientationUtils orientationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_video);
        //EXOPlayer内核，支持格式更多
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        //系统内核模式
//        PlayerFactory.setPlayManager(SystemPlayerManager.class);
        //ijk内核，默认模式
//        PlayerFactory.setPlayManager(IjkPlayerManager.class);


        //exo缓存模式，支持m3u8，只支持exo
        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);
        //代理缓存模式，支持所有模式，不支持m3u8等，默认
//        CacheFactory.setCacheManager(ProxyCacheManager.class);
        Log.w("Fr_Video", "------------------- 进入Fr_Video ----------------------");
        value = getIntent().getStringExtra("key");
        setValue();
        findView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 200:
                        initData();
                        System.out.println("mHandle " + name);
                }
            }
        };

    }

    private void findView() {
        videoPlayer = (StandardGSYVideoPlayer) findViewById(R.id.video_player);
    }

    private void initData() {
        Log.w("init()", "初始化数据");
        try {
            JSONArray jsonArray = new JSONArray(contents);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject != null) {
                    String m3u8url = jsonObject.optString("m3u8url");
                    String onlineurl = jsonObject.optString("onlineurl");
                    String download = jsonObject.getString("download");
                    String num = jsonObject.optString("num");

                    GetUrl geturl = new GetUrl(m3u8url, onlineurl, download, num);
                    onlineurls = geturl.getOnlineurl();
                    downloads = geturl.getDownload();
                    m3u8urls = geturl.getM3u8url();
                    init();

                    Log.d("init()", "初始化成功 ");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setValue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.w("setValue", "初始化视频链接");
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
                            Log.w("setValue", "response.code() = 500");
                            Toast.makeText(Fr_Video.this, "HTTP 500 内部服务器错误", Toast.LENGTH_LONG).show();
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

    private void init() {
        //测试视频地址
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        videoPlayer.setUp(m3u8urls, true, "测试视频");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        videoPlayer.setAutoFullWithSize(true);
        //全屏动画
        videoPlayer.setShowFullAnimation(true);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
