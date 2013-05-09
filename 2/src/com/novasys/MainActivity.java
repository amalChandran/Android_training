package com.novasys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
     Button b;
     TextView tv;
     
     EditText et1,et2;
     String appId=null,appVersion=null;
     public static int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//-----------------------------------------------------------------------linking xml
		b=(Button) findViewById(R.id.button1);
		tv=(TextView) findViewById(R.id.textView1);
		et1=(EditText) findViewById(R.id.editText1);
		et2=(EditText) findViewById(R.id.editText2);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et1.getText().toString()!=null && et2.getText().toString()!=null){
					appId=et1.getText().toString();
					appVersion=et2.getText().toString();
					new Task().execute();
				}
			
			}
		});
		
	}
	class Task extends AsyncTask<Void, Void, Void>{
		StringBuilder sb;
		HttpResponse res;
		String responseBody;
		@Override
		protected Void doInBackground(Void... params) {
			HttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost("http://swagbucks.com/?cmd=apm-3");
			
			//attaching name value pairs
			List<NameValuePair> namevaluepairs=new ArrayList<NameValuePair>(2);
			/*
			namevaluepairs.add(new BasicNameValuePair("emailaddress", "amal123456@gmail.com"));
			namevaluepairs.add(new BasicNameValuePair("pswd", "welcomes"));
			namevaluepairs.add(new BasicNameValuePair("cmp", "50"));
			namevaluepairs.add(new BasicNameValuePair("cxid", "ios.v1"));
			*/
			namevaluepairs.add(new BasicNameValuePair("appid", appId));
			namevaluepairs.add(new BasicNameValuePair("appversion", appVersion));
			
			try {count++;
				post.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				//Log.i("inside", "set entity");
				count++;
			    res=client.execute(post);
			    responseBody=EntityUtils.toString(res.getEntity());
			    
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			//Toast.makeText(getApplicationContext(), ""+responseBody, 0).show();
			tv.setText(""+responseBody);
			Log.i("HttpPost Response", ""+responseBody);
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
