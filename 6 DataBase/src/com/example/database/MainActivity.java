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
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	 //Data base version
		private static final int DATABASE_VERSION=1;
	   //Data base name
		private static final String DATABASE_NAME="contactsManager";
		private List<Contacts> contactList;
		StringBuilder sb;
		Contacts contact;
		Button b;
		String json="{\"id\":123,\"name\": \"nam3\",\"phoneNumber\":\"000000\"}";
		DBHandler dbh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b=(Button) findViewById(R.id.button1);
		//1 converting from json to pojo
		jsonToPojo(json);
		
		//2 inserting pojo to database
		dbh=new DBHandler(this, DATABASE_NAME, null, DATABASE_VERSION);
		dbh.addContact(contact);
		dbh.addContact(contact);
		dbh.addContact(contact);
		dbh.addContact(contact);
		dbh.addContact(contact);
		
		showAllContacts();
		//udpating the database value with the id
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/* "updateRow()" will perform the two operation as follows.
				 * 1.insert new object to table with id=1 and 2.
				 * 2.it reads the row from table with id=(second parameter) and displays in log.
				 * Even if one of the transaction goes wrong! then it will be rolled back, else comitted.
				 */
				dbh.updateRow(new Contacts("updated", "11111111111"), 100);
				showAllContacts();				
			}
		});
	
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
private void showAllContacts(){
	//3 from database to log info :)
			contactList=dbh.getAllContacts();
			for(int i=0;i<contactList.size();i++){
				Log.i(""+contactList.get(i).getId(), contactList.get(i).getName()+"--"+contactList.get(i).getPhoneNumber());
			}
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
