package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper{
   //Data base version
	private static final int DATABASE_VERSION=1;
   //Data base name
	private static final String DATABASE_NAME="contactsManager";
   //Contacts table name
	private static final String TABLE_CONTACTS="contacts";
	
  //Contact table contents
	private static final String KEY_ID="id";
	private static final String KEY_NAME="name";
	private static final String KEY_PH_NO="phone_number";
	public DBHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE="CREATE TABLE "+TABLE_CONTACTS+"("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT,"+KEY_PH_NO+" TEXT"+")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXIST " + TABLE_CONTACTS);
		onCreate(db);
	}
   //To add the contact details one by one!
    void addContact(Contacts contact){
    	SQLiteDatabase db=this.getWritableDatabase();
    	ContentValues cv=new ContentValues();
    	cv.put(KEY_NAME, contact.getName() );
    	cv.put(KEY_PH_NO, contact.getPhoneNumber());
    	//**************************************************************COMMIT/REVERT***************************************************
    	try {
    		db.beginTransaction();
        	db.insert(TABLE_CONTACTS, null, cv);
        	Log.i("Transaction", "Comitted");
        	db.setTransactionSuccessful();
   
		} catch (SQLiteException e) {
		      Log.e("SQLite exception", "rollback");
		}finally{
			db.endTransaction();
		}
    	db.close();
    }
   //to get all the contacts from the contacts table
    public List<Contacts> getAllContacts(){
    	List<Contacts> contactList=new ArrayList<Contacts>();
    	
    	String selectQuery="SELECT * FROM "+TABLE_CONTACTS;
    	SQLiteDatabase db=this.getReadableDatabase();
    	
    	Cursor cursor=db.rawQuery(selectQuery, null);
    	while(cursor.moveToNext()){
    		Contacts contacts=new Contacts();
    		contacts.setId(Integer.parseInt(cursor.getString(0)));
    		contacts.setName(cursor.getString(1));
    		contacts.setPhoneNumber(cursor.getString(2));
    		contactList.add(contacts);
    	}
    	db.close();
		return contactList;
    }
    //************************************************************updates two ids, even if one id is wrong it will roll back both the transaction.
    public int updateRow(Contacts contacts,int id){
    	int i,j;
    	SQLiteDatabase db=this.getWritableDatabase();
    	ContentValues cv=new ContentValues();
    	cv.put(KEY_NAME, contacts.getName());
    	cv.put(KEY_PH_NO, contacts.getPhoneNumber());
  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    		db.beginTransaction();
    		//******************************************************************************
        	i=db.update(TABLE_CONTACTS, cv, KEY_ID+" = ?", new String[]{String.valueOf(1)});
        	j=db.update(TABLE_CONTACTS, cv, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        	//******************************************************************************
        	Cursor cursor=db.query(TABLE_CONTACTS, new String[]{KEY_ID,KEY_NAME,KEY_PH_NO}, KEY_ID+" = ?", new String[]{String.valueOf(2)}, null, null, null,null);
        	
        	if(i==1&&j==1&&cursor!=null){
        		cursor.moveToFirst();
        		Log.i("returned val", cursor.getString(1)+"---"+cursor.getString(2));
        		Log.e("Transaction:", "Comitted");
        		db.setTransactionSuccessful();
        	}else{
        		Log.e("Transaction:", "Rolled back/Reverted");
        	}
		
			
			db.endTransaction();
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		return id;
    	
    }

}
