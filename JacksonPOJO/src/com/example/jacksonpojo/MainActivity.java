package com.example.jacksonpojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
/*
 * 1.USER OBJECT TO JSON(saved to config1.txt file)
 * 2.JSON TO USER1
 */

public class MainActivity extends Activity {
	
	 BufferedReader br;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//writeToFile(json);
		//streamJackson();//json to pojo using streaming jackson
		//jsonToPojo();
		
		pojoToJson();
		jsonToPojo();
			
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void pojoToJson(){
		User user = new User();
		ObjectMapper mapper = new ObjectMapper();
	 
		try {
	 
			// convert user object to json string, and save to a file
			mapper.writeValue(openFileOutput("config1.txt", Context.MODE_PRIVATE), user);
	 
			// display to console
			Log.i("Generated Json :",readFromFile());
			
	 
		} catch (JsonGenerationException e) {
			Log.i("Exception", "jsonGenerationexception");
			e.printStackTrace();
	 
		} catch (JsonMappingException e) {
			Log.i("Exception", "jsonmapping exception");
			e.printStackTrace();
	 
		} catch (IOException e) {
			Log.i("Exception", "ioexception");
			e.printStackTrace();
	 
		}
	}
	private void jsonToPojo(){
       ObjectMapper mapper=new ObjectMapper();
		
		try {
			User1 user1=mapper.readValue(readFromFile(), User1.class);
			System.out.println(user1);
			Toast.makeText(getApplicationContext(), "inside try", 0).show();
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
/*	
	private void streamJackson(){
		try {
			 
			JsonFactory jfactory = new JsonFactory();
		 
			
			JsonParser jParser = jfactory.createJsonParser(json);
		
			// loop until token equal to "}"
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				 Toast.makeText(getApplicationContext(), "inside while", 0).show();
				String fieldname = jParser.getCurrentName();
				if ("name".equals(fieldname)) {
		 
				  // current token is "name",
		                  // move to next, which is "name"'s value
				  jParser.nextToken();
				  Log.i("name", jParser.getText());
				  System.out.println(jParser.getText()); // display mkyong
		 
				}
		 
				if ("age".equals(fieldname)) {
		 
				  // current token is "age", 
		                  // move to next, which is "name"'s value
				  jParser.nextToken();
				  Log.i("age", ""+jParser.getIntValue());
				  System.out.println(jParser.getIntValue()); // display 29
		 
				}
		 
				if ("messages".equals(fieldname)) {
		 
				  jParser.nextToken(); // current token is "[", move next
		 
				  // messages is array, loop until token equal to "]"
				  while (jParser.nextToken() != JsonToken.END_ARRAY) {
		 
		                     // display msg1, msg2, msg3
					  Log.i("Message", jParser.getText());
				     System.out.println(jParser.getText()); 
		 
				  }
		 
				}
		 
			  }
			  jParser.close();
		 
		     } catch (JsonGenerationException e) {
		 
			  e.printStackTrace();
		 
		     } catch (JsonMappingException e) {
		 
			  e.printStackTrace();
		 
		     } catch (IOException e) {
		 
			  e.printStackTrace();
		 
		     }
	}
	*/
	private void writeToFile(String data) {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
	        outputStreamWriter.write(data);
	        outputStreamWriter.close();
	        Log.e("success", "file written");
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    } 
	}
	private String readFromFile() {

	    String ret = "";

	    try {
	        InputStream inputStream = openFileInput("config1.txt");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return ret;
	}
}
