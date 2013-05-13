package com.example.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
    ListView lv;
    SimpleAdapter ad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.listView1);
		String[] source={"qwq","qwq","qwqwq","eqw","qqeq","q","qe","qe","qe","d","","","","","","","qwq","qwq","qwqwq","eqw","qqeq","q","qe","qe","qe","d","","","","","",""};
		ArrayAdapter<String> s=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, source);
		lv.setAdapter(s);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
