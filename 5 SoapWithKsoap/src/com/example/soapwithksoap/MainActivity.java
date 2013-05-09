package com.example.soapwithksoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
/*
 * webservicex accepts weight, fromUnit and toUnit. 
 * And it converts the weight fromunit to to unit and gives the response.
 * fromUnit and toUnit takes these values=>[Grains or Scruples or Carats or Grams or Pennyweight or DramAvoir or DramApoth or OuncesAvoir or OuncesTroyApoth or Poundals or PoundsTroy or PoundsAvoir or Kilograms or Stones or QuarterUS or Slugs or weight100UScwt or ShortTons or MetricTonsTonne or LongTons]
 * 
 */
public class MainActivity extends Activity {
	private final String NAMESPACE = "http://www.webserviceX.NET/";
    private final String URL = "http://www.webservicex.net/ConvertWeight.asmx";
    private final String SOAP_ACTION = "http://www.webserviceX.NET/ConvertWeight";
    private final String METHOD_NAME = "ConvertWeight";
    SoapObject request;
    String weight;
    String fromUnit;
    String toUnit;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
        request = new SoapObject(NAMESPACE, METHOD_NAME); 
       
        weight = "3700";
        fromUnit = "Grams";
        toUnit = "Kilograms";
       
        PropertyInfo weightProp =new PropertyInfo();
        weightProp.setName("Weight");
        weightProp.setValue(weight);
        weightProp.setType(double.class);
        request.addProperty(weightProp);
          
        PropertyInfo fromProp =new PropertyInfo();
        fromProp.setName("FromUnit");
        fromProp.setValue(fromUnit);
        fromProp.setType(String.class);
        request.addProperty(fromProp);
          
        PropertyInfo toProp =new PropertyInfo();
        toProp.setName("ToUnit");
        toProp.setValue(toUnit);
        toProp.setType(String.class);
        request.addProperty(toProp);
        //calling the AsyncTask
        new Task().execute();
         
        
    }
	class Task extends AsyncTask<Void, Void, Void>{
		SoapPrimitive response;
		@Override
		protected Void doInBackground(Void... params) {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	  
	        try {
	            androidHttpTransport.call(SOAP_ACTION, envelope);
	            response = (SoapPrimitive)envelope.getResponse();
	            Log.i("myApp", response.toString());
	     
	            
	  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			TextView tv = new TextView(getApplicationContext());
            tv.setText(weight+" "+fromUnit+" equal "+response.toString()+ " "+toUnit);
            setContentView(tv);
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
