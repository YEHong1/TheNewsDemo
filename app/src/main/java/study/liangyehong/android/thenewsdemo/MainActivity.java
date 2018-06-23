package study.liangyehong.android.thenewsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import study.liangyehong.android.thenewsdemo.loopj.android.image.SmartImageView;

public class MainActivity extends AppCompatActivity {

    private List<News> newsList;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //【1】找到我们关心的控件
        lv = (ListView) findViewById(R.id.lv);
        //【2】准备listview要显示的内容，去服务器取数据，进行封装
        initListData();
    }

    //准备listview的数据
    private void initListData() {
        //更新UI或耗时操作必须要开子线程
        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    //【2】去服务器去数据（我本地的服务器）
                    String path = "http://10.10.11.13/news.xml";
                    //【2.1】创建URL对象，指定我们要访问的网址（路径）
                    URL url = new URL(path);
                    //【2.2】拿到HttpURLConnection对象，用于发送或者接收数据
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //【2.3】设置发送get请求
                    conn.setRequestMethod("GET");
                    //【2.4】设置请求超时时间
                    conn.setConnectTimeout(5000);
                    //【2.5】获取服务器返回的状态码
                    int code = conn.getResponseCode();
                    //【2.6】如果返回的状态码为200，说明请求成功
                    if (code == 200){
                        //【2.7】获取服务器返回的数据，是以流的形式返回的
                        InputStream in = conn.getInputStream();
                        //解析xml
                        newsList = XmlParserUtils.parserXml(in);
                        //更新UI，把数据展示到listview上
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                lv.setAdapter(new MyAdapter());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //定义数据适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item,
                        null);
            } else {
                view = convertView;

            }
            // [1]找到控件 显示集合里面的数据
            SmartImageView iv_icon = (SmartImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);

            //获取图片的网址（路径）
            String imageUrl = newsList.get(position).getImage();

            //展示图片，当获取不到第一个参数时，展示第二个参数
            iv_icon.setImageUrl(imageUrl, R.drawable.bg);

            //显示数据
            tv_title.setText(newsList.get(position).getTitle());
            tv_desc.setText(newsList.get(position).getDescription());
            String typee = newsList.get(position).getType();
            String comment = newsList.get(position).getComment();
            int type = Integer.parseInt(typee);
            switch (type) {
                case 1:

                    tv_type.setText(comment+"国内");
                    break;
                case 2:
                    tv_type.setText(comment+"跟帖");
                    break;
                case 3:
                    tv_type.setText(comment+"国外");
                    break;
            }
            return view;
        }

    }
}

