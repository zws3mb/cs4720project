//Team Pepper, {Amas, Larsen, Seid}, Phase 2
package edu.virginia.cs.louslisttest2;

import java.net.HttpURLConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class CourseInfoActivity extends Activity {
	
	private static final String TAG = "COURSE"; //for LogCat  
	
	TextView courseIDTextView;
	TextView courseNameTextView;
	TextView instructorTextView;
	TextView sectionNumTextView;
	TextView meetStringTextView;
	TextView meetRoomTextView;

	static final String KEY_ITEM = "courseID"; //?? not sure about this one
	static final String KEY_NAME = "courseName";
	static final String KEY_SECTION_NUM = "sectionNum";
	static final String KEY_INSTRUCTOR = "courseInstructor";
	static final String KEY_MEET_STRING = "meetString";
	static final String KEY_MEET_ROOM = "meetRoom";

	String courseID = "";
	String courseName = "";
	String instructor = "";
	String sectionNum = "";
	String meetString = "";
	String meetRoom = "";
	
	String urlFirst = "http://plato.cs.virginia.edu/~cs4720s14pepper/view/CLAS/";
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		
		Intent intent = getIntent();
		String stockSymbol = intent.getStringExtra(MainActivity.COURSE_ID);
		
		courseIDTextView = (TextView) findViewById(R.id.courseIDTextView);
		courseNameTextView = (TextView) findViewById(R.id.courseNameTextView);
		instructorTextView = (TextView) findViewById(R.id.instructorTextView);
		sectionNumTextView = (TextView) findViewById(R.id.sectionNumTextView);
		meetStringTextView = (TextView) findViewById(R.id.meetStringTextView);
		meetRoomTextView = (TextView) findViewById(R.id.meetRoomTextView);
		
		Log.d(TAG, "Before URL Creation " + courseID);
		
		//build URL
		final String theURL = urlFirst + courseID;
		
//		new MyAsyncTask().execute();
		
	}
	
/*	private class MyAsyncTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... params) {
			
			try {
				//get URL
				URL url = new URL(params[0]);
				URLConnection connection;
				connection = url.openConnection();
				
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				
				//proper connection?
				int responseCode = httpConnection.getResponseCode();
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					//TODO read from connection
				}
			}
			
			catch ()
			
			return null;
			
			
		}
		
		
	
	}
	*/
}
