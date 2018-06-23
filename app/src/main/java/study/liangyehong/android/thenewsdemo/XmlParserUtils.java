package study.liangyehong.android.thenewsdemo;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curtain_Liang on 2018/6/23.
 */

public class XmlParserUtils {
    //解析xml的方法
    public static List<News> parserXml(InputStream in) throws Exception{
        List<News> newsList = null;
        News news = null;
        //获取xml的解析器
        XmlPullParser parser = Xml.newPullParser();
        //设置解析器，第一个参数为解析的内容
        parser.setInput(in,"utf-8");
        //获取解析的事件类型
        int type = parser.getEventType();
        //但未解析至xml文件的结束标签时，不停的向下解析
        while (type != XmlPullParser.END_DOCUMENT){
            //具体地判断一下，解析的是开始节点还是结束节点
            switch (type){
                //解析开始节点
                case XmlPullParser.START_TAG:
                    if ("channel".equals(parser.getName())){
                        //创建一个list集合
                        newsList = new ArrayList<News>();
                    }else if ("item".equals(parser.getName())){
                        news = new News();
                    }else if ("title".equals(parser.getName())){
                        news.setTitle(parser.nextText());
                    }else if ("description".equals(parser.getName())){
                        news.setDescription(parser.nextText());
                    }else if ("image".equals(parser.getName())){
                        news.setImage(parser.nextText());
                    }else if ("type".equals(parser.getName())){
                        news.setType(parser.nextText());
                    }else if ("comment".equals(parser.getName())){
                        news.setComment(parser.nextText());
                    }
                    break;
                //解析结束节点
                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())){
                        //把javabean添加到集合
                        newsList.add(news);
                    }
                    break;
            }
            //不断地向下解析
            type = parser.next();
        }
        return newsList;
    }
}
