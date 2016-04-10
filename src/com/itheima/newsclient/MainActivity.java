package com.itheima.newsclient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.domain.NewsItem;
import com.itheima.service.NewsItemParser;
import com.loopj.android.image.SmartImageView;

public class MainActivity extends Activity {

	private TextView tv_title;
	private TextView tv_desc;
	private TextView tv_type;
	private SmartImageView iv_image;
	private List<NewsItem> newsItems;
	private ListView lv;
	//����һ��handler���޸�UI
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			lv.setAdapter(new MyAdapter());
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lv = (ListView) findViewById(R.id.lv);

		
		fillData();
		
	}
	/**
	 * �������ϻ�ȡ���ݣ������listview
	 */
	private void fillData() {
		
		new Thread(){
			public void run() {
				
				try {
					URL url = new URL("http://192.168.12.28:8080/news.xml");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					
					int code = conn.getResponseCode();
					if(code == 200){
						InputStream is = conn.getInputStream();
						//�Ѿ��õ���xml�ļ��е�����
						newsItems = NewsItemParser.getNewsItems(is);
						//��������Ŀ��ʾ��listview��
						Message msg = Message.obtain();
						handler.sendMessage(msg);
					}else{
						//��ȡ����ʧ��
					}
				} catch (Exception e) {
					//��������ʧ��
					e.printStackTrace();
				}
				
			};
		}.start();
		
	}

	
	/**
	 * ����һ��adapter�������listview
	 * @author Administrator
	 * 
	 */
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return newsItems.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if(convertView != null){
				view  = convertView;
			}else{
				view = View.inflate(MainActivity.this, R.layout.item, null);
			}
             
			NewsItem item = newsItems.get(position);
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_desc = (TextView) view.findViewById(R.id.tv_desc);
			tv_type = (TextView) view.findViewById(R.id.tv_type);
			
			iv_image = (SmartImageView) view.findViewById(R.id.iv_image);
			iv_image.setImageUrl(item.getImage());
			
			tv_title.setText(item.getTitle());
			tv_desc.setText(item.getDescription());
			if("1".equals(item.getType())){
				tv_type.setText("����"+item.getComment());
				tv_type.setTextColor(Color.BLACK);
			}else if("2".equals(item.getType())){
				tv_type.setText("��Ƶ");
				tv_type.setTextColor(Color.BLUE);
			}else if("3".equals(item.getType())){
				tv_type.setText("ר��");
				tv_type.setTextColor(Color.RED);
			}
			
			
			return view;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	
		
	}
}
