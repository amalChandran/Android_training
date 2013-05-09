package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    Button b;
    TextView tv;
    public static int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b=(Button) findViewById(R.id.button1);
		tv=(TextView) findViewById(R.id.textView1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Task().execute();
			}
		});
	}
	
class Task extends AsyncTask<Void, Void, Void>{
	StringBuilder sb;
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		HttpClient client=new DefaultHttpClient();
		HttpGet get=new HttpGet("http://player.swagbucks.com/mobile/startrss.xml");
		try {
			
			HttpResponse res=client.execute(get);
			count++;
			HttpEntity ent=res.getEntity();
			InputStream is=ent.getContent();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			
			sb=new StringBuilder();
			String s=null;
			
			while(br.readLine()!=null){
				s=br.readLine();
				
				sb.append(s);
			}
			
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
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), ""+sb, 0).show();
		Log.i("Http get", ""+sb);
		tv.setText(""+sb);
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
