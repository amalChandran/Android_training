package com.example.xmlwithsimple;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends Activity {
	
    private DefaultHttpClient client = new DefaultHttpClient();
    public static String  xmlData=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//from xml to pojo (object binding)
		 new Task().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void simpleXMl(String xml){
		 Serializer serializer = new Persister(); 
		 Reader reader = new StringReader(xmlData);
		 OpenSearchDescription osd;
		try {
			osd = serializer.read(OpenSearchDescription.class, reader, false);
			//from pojo to XML
			serialization(osd);
			Log.d(MainActivity.class.getSimpleName(), osd.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void serialization(OpenSearchDescription osd1){
		Serializer serializer=new Persister();
		try {
			serializer.write(osd1, openFileOutput("config1.txt", Context.MODE_PRIVATE));
			Log.e("serialization", "done");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class Task extends AsyncTask<Void, Void, Void>{
		private static final String url ="http://dl.dropboxusercontent.com/u/7215751/JavaCodeGeeks/AndroidFullAppTutorialPart03/Transformers%2B2007.xml";
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpGet getRequest = new HttpGet(url);
			try {
				   	HttpResponse getResponse = client.execute(getRequest);
				   	final int statusCode = getResponse.getStatusLine().getStatusCode();
				   	if (statusCode != HttpStatus.SC_OK) {
				   		return null;
				   		}
				   	HttpEntity getResponseEntity = getResponse.getEntity();
				   	if (getResponseEntity != null) {
				   		xmlData=EntityUtils.toString(getResponseEntity);
				   		}
				}catch (IOException e) {
					getRequest.abort();
					Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
					}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.i("Inside async task", "00000000000000000000000000000000000000000000000000000000000000000000000000"+xmlData);
			simpleXMl(xmlData);
			super.onPostExecute(result);
		}
	}



}
