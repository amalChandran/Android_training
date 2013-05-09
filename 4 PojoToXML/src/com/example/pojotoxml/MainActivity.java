package com.example.pojotoxml;

import java.io.IOException;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView) findViewById(R.id.textView1);
		
		Pojo pojo=new Pojo();
		//Serialization begins:
		XmlSerializer serializer=Xml.newSerializer();
		StringWriter writer=new StringWriter();
		try {
			serializer.setOutput(writer);
			//startdocument
			serializer.startDocument("UTF-8", true);
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
			
			serializer.startTag("", "file");
					serializer.startTag("", "something");
					   serializer.attribute("", "ID", pojo.getId());
					      serializer.startTag("", "name");
					          serializer.text(pojo.getName());
					      serializer.endTag("", "name");
					      serializer.startTag("", "location");
					          serializer.text(pojo.getLocation());
					      serializer.endTag("", "location");
					serializer.endTag("", "something");
			serializer.endTag("", "file");
			
			serializer.endDocument();
			//end document.
			
			Log.i("Pojo to xml",writer.toString() );
			tv.setText(""+writer.toString());
			//Toast.makeText(getApplicationContext(), ""+writer.toString(), 0).show();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
