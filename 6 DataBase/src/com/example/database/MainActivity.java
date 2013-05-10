package com.example.database;


import java.io.IOException;
import java.util.List;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import android.widget.Toast;

public class MainActivity extends Activity {
	 //Data base version
		private static final int DATABASE_VERSION=1;
	   //Data base name
		private static final String DATABASE_NAME="contactsManager";
		private List<Contacts> contactList;
		StringBuilder sb;
		Contacts contact;
		String json="{\"id\":12,\"name\": \"novasys\",\"phoneNumber\":\"903222\"}";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//1 converting from json to pojo
		jsonToPojo(json);
		
		//2 from pojo to database
		DBHandler dbh=new DBHandler(this, DATABASE_NAME, null, DATABASE_VERSION);
		dbh.addContact(contact);
		
		//3 from database to log info :)
		contactList=dbh.getAllContacts();
		for(int i=0;i<contactList.size();i++){
			Log.i(contactList.get(i).getName(), contactList.get(i).getPhoneNumber());
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void jsonToPojo(String json){
	       ObjectMapper mapper=new ObjectMapper();
			
			try {
				contact=mapper.readValue(json, Contacts.class);
				
				System.out.println(contact);
				Toast.makeText(getApplicationContext(), "json to pojo", 0).show();
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				Log.i("Exception", "jsonparseexception");
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				Log.i("Exception", "jsonmapping exception");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("Exception", "ioexception");
				e.printStackTrace();
			}
		}
	
}
