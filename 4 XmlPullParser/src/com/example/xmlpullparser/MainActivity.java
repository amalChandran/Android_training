package com.example.xmlpullparser;

import java.io.IOException;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
  ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 
        List<Employee> employees = null;
            
            try {
            	XMLPullParserHandler parser = new XMLPullParserHandler();
				employees = parser.parse(getAssets().open("employees.xml"));
				for(int i=0;i<employees.size();i++){
	            	Toast.makeText(getApplicationContext(), ""+employees.get(i).toString(), 0).show();
	            	Log.i("employee"+i+":", ""+employees.get(i).toString());
	            }
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
