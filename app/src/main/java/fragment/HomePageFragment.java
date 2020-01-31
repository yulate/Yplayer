package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import bean.GetInfo;
import demo.Yu.yplayer.Fr_Video;
import demo.Yu.yplayer.R;
import demo.Yu.yplayer.Ys_Listdata;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePageFragment extends Fragment {

    private EditText editText;
    private Button button;
    private ListView homepage_list;
    private ArrayAdapter adapter;
    private View view;

    private static Handler mHandler;

    private String ysname, code, message, contents, names, urls;

    private List<String> listData = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_homepage_fragment, container, false);

        findView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_contents();
            }
        });
        homepage_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) adapter.getItem(position);
                Toast.makeText(getActivity(), "你点击了第" + item + "项!", Toast.LENGTH_SHORT).show();
                String value = item.toString();

                System.out.println("点击内容为：" + value);

                Intent intent = new Intent();
                intent.setClass(getActivity(), Ys_Listdata.class);
                intent.putExtra("key", urls);
                startActivity(intent);
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 200:
                        Log.w("Handler()", "mHandler接受内容" + contents);
                        listData.clear();
                        init();
                }
            }
        };

        return view;
    }

    private void findView() {
        Log.w("findView()", "初始化控件");
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.get_ys_btn);
        homepage_list = view.findViewById(R.id.homepage_list);
    }

    private void init() {
        Log.w("init()", "初始化数据");
        try {
            JSONArray jsonArray = new JSONArray(contents);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject != null) {
                    String name = jsonObject.optString("name");
                    String url = jsonObject.optString("url");
                    String genre = jsonObject.getString("genre");
                    String time = jsonObject.optString("timr");

                    GetInfo getInfo = new GetInfo(name, url, genre, time);
                    names = getInfo.getName();
                    urls = getInfo.getUrl();

                    listData.add("{" + " \"name\"" + ":" + "\"" + names + "\"" + " , " + " \"url\"" + ":" + "\"" + urls + "\"" + "}");
                    list.add(names);
                    initAdapter();
                    Log.d("init()", "初始化成功 ");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        Log.w("initAdapter()", "初始Adapter");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
        homepage_list.setAdapter(adapter);
        Log.w("initAdapter()", "绑定数据 listDate = " + list);
    }

    public void get_contents() {
        ysname = editText.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String contents_path = "http://39.105.204.201/api/get_ys/?name=" + URLEncoder.encode(ysname, "utf-8");
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(contents_path)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        contents = response.body().string();
                        System.out.println(response.code());
                        if (response.code() == 200) {
                            System.out.println("response.code() = " + contents);
                            Message message = new Message();
                            mHandler.sendMessage(message);
                            message.what = response.code();
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
