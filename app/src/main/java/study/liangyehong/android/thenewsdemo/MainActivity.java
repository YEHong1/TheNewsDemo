package study.liangyehong.android.thenewsdemo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import study.liangyehong.android.thenewsdemo.loopj.android.image.SmartImageView;

public class MainActivity extends Activity {
    private List<News> newsLists;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [1]找到我们关心的控件

        lv = (ListView) findViewById(R.id.lv);
        // [2] 准备listview 要显示的数据 去服务器取数据 进行封装
        initListData();

    }

    // 准备listview的数据
    private void initListData() {
        new Thread() {

            public void run() {

                try {
                    // [2.1]去服务器取数据 http://110.10.11.13/news.xml
                    String path = "http://10.10.11.13/news.xml";
                    // [2.2]创建URL 对象指定我们要访问的 网址(路径)
                    URL url = new URL(path);

                    // [2.3]拿到httpurlconnection对象 用于发送或者接收数据
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    // [2.4]设置发送get请求
                    conn.setRequestMethod("GET");// get要求大写 默认就是get请求
                    // [2.5]设置请求超时时间
                    conn.setConnectTimeout(5000);
                    // [2.6]获取服务器返回的状态码
                    int code = conn.getResponseCode();
                    // [2.7]如果code == 200 说明请求成功
                    if (code == 200) {
                        // [2.8]获取服务器返回的数据 是以流的形式返回的
                        InputStream in = conn.getInputStream();
                        // [2.9]解析xml 抽出一个业务方法
                        newsLists = XmlParserUtils.parserXml(in);
                        System.out.println("newsLists:"+newsLists.size());

                        runOnUiThread(new  Runnable() {
                            public void run() {
                                //[3]更新ui  把数据展示到listview上
                                lv.setAdapter(new MyAdapter());

                            }
                        });


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();

    }

    //定义数据适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsLists.size();
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

            //[1.1]获取图片ip地址
            String imageUrl = newsLists.get(position).getImage();

            //展示图片
            iv_icon.setImageUrl(imageUrl, R.drawable.bg);

            // [2]显示数据
            tv_title.setText(newsLists.get(position).getTitle());
            tv_desc.setText(newsLists.get(position).getDescription());
            String typee = newsLists.get(position).getType();
            String comment = newsLists.get(position).getComment();
            int type = Integer.parseInt(typee);
            switch (type) {
                case 1:

                    tv_type.setText(comment+"国内");
                    break;
                case 2:
                    tv_type.setText("跟帖");
                    break;
                case 3:
                    tv_type.setText("国外");
                    break;

                default:
                    break;
            }

            return view;
        }

    }

}
