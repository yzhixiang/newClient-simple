package com.itheima.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.itheima.domain.NewsItem;

public class NewsItemParser {
	
	public static List<NewsItem> getNewsItems(InputStream is){
		
		try {
			//����һ��xml�ļ��Ľ�����
			XmlPullParser parser = Xml.newPullParser();
			//ʹ�ý�������������
			parser.setInput(is, "UTF-8");
			
			List<NewsItem> newsItems = new ArrayList<NewsItem>();
			NewsItem item =null;
			//��ȡ�����¼�������
			int type = parser.getEventType();
			while(type != XmlPullParser.END_DOCUMENT){
				switch (type) {
				//��������ǩ�Ŀ�ʼλ��
				case XmlPullParser.START_TAG:
					if("item".equals(parser.getName())){
						item = new NewsItem();
					}else if("title".equals(parser.getName())){
						String title = parser.nextText();
						item.setTitle(title);
					}else if("description".equals(parser.getName())){
						String description = parser.nextText();
						item.setDescription(description);
					}else if("image".equals(parser.getName())){
						String image = parser.nextText();
						item.setImage(image);
					}else if("type".equals(parser.getName())){
						String newsType = parser.nextText();
						item.setType(newsType);
					}else if("comment".equals(parser.getName())){
						String comment = parser.nextText();
						item.setComment(comment);
					}
					
					break;
					//��������ǩ�Ľ���λ��
				case XmlPullParser.END_TAG:
					if("item".equals(parser.getName())){
						newsItems.add(item);
						item = null;
					}
					break;

				}
				//һ�Ա�ǩ����������������һ�������¼�
				type = parser.next();
			}
			
			return newsItems;
		} catch (Exception e) {
			return null;
		}
	
	}

}
