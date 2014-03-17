//Team Pepper, [Amas, Larsen, Seid], Phase 2
package edu.virginia.cs.louslisttest2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	ListView courseList;
	String webserviceURL = "http://plato.cs.virginia.edu/~cs4720s14pepper/view/CLAS/";
	ArrayList<Course> values;
	ArrayAdapter<Course> adapter;

	public final static String COURSE_ID = "com.example.courseid.COURSEID";
	private SharedPreferences courseIDsEntered;
	private TableLayout courseScrollView;
	private EditText courseIDEditText;
	Button enterCourseIDButton;
	Button deleteCourseIDButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("onCreate", "start up");
		setContentView(R.layout.activity_course_list);

		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_course_list, menu);
		return true;
	}

	public void initView() {
		//retrieve saved courses
		courseIDsEntered = getSharedPreferences("courseList", MODE_PRIVATE);
				
		courseScrollView = (TableLayout) findViewById(R.id.courseScrollView);
		courseIDEditText = (EditText) findViewById(R.id.CourseIDEditText);
		enterCourseIDButton = (Button) findViewById(R.id.EnterCourseIDButton);
		deleteCourseIDButton = (Button) findViewById(R.id.deleteCourseIDButton);
		
		updateSavedCourseList(null); //update courseList if null passed in. 
		
		values = new ArrayList<Course>();

		// Adjust the URL with the appropriate parameters
		String url = webserviceURL + "ECON"; //will search for ECON classes for now.

		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data
		//Log.d("HTTP", url);
		adapter = new ArrayAdapter<Course>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

		// Assign adapter to ListView
		courseList.setAdapter(adapter);

		new GetCoursesTask().execute(url);

	}

	private void updateSavedCourseList(String newCourseID) {
		String [] courses = courseIDsEntered.getAll().keySet().toArray(new String[0]);
		//sort them
		Arrays.sort(courses, String.CASE_INSENSITIVE_ORDER);
		
		if(newCourseID != null) {
			//then add to courseList ScrollView
			insertCourseIDInScrollView(newCourseID, Arrays.binarySearch(courses, newCourseID));
		}
		
		else {
			for(int i= 0; i<courses.length; i++) {
				insertCourseIDInScrollView(courses[i], i);
			}
		}
	}

	//save course IDs
	private void saveCourseIDs(String newCourseID) {
		String isNewCourseID = courseIDsEntered.getString(newCourseID, null);
		SharedPreferences.Editor preferencesEditor = courseIDsEntered.edit();
		preferencesEditor.putString(newCourseID, newCourseID);
		preferencesEditor.apply(); //@SuppressLint "NewApi" above to deal with API 9 vs 8
		
		if (isNewCourseID == null) {
			updateSavedCourseList(newCourseID);
		}
	}
	
	private void insertCourseIDInScrollView(String newCourseID, int binarySearch) {
		//set/create a courseID row (from course_id_row.xml) dynamically inside ScrollView 
		//every time a new CourseID is entered
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newCourseIDRow = inflater.inflate(R.layout.course_id_row, null);
		TextView newCourseTextView = (TextView) newCourseIDRow.findViewById(R.id.CourseListTextView);
		newCourseTextView.setText(newCourseID);		
		
		Button enterCourseIDButton = (Button) newCourseIDRow.findViewById(R.id.EnterCourseIDButton);
		Button deleteCourseIDButton = (Button) newCourseIDRow.findViewById(R.id.deleteCourseIDButton);
		//button listener
		enterCourseIDButton.setOnClickListener(enterCourseIDButtonListener);
		deleteCourseIDButton.setOnClickListener(deleteCourseIDButtonListener);
		courseScrollView.addView(newCourseIDRow, binarySearch);
		
	}
	
	public OnClickListener enterCourseIDButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			//check if there is input
			if (courseIDEditText.getText().length() > 0) {
				//save new courseID and its components
				saveCourseIDs(courseIDEditText.getText().toString());
				
				//clear 
				courseIDEditText.setText("");
				
				//force keyboard close
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(courseIDEditText.getWindowToken(), 0);
			}
			
			else {
				//ALERT DIALOG BOX: NO INPUT
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle(R.string.invalid_course_ID);
				builder.setPositiveButton(R.string.OK_button, null);
				builder.setMessage(R.string.missing_course_ID);
				AlertDialog theAlert = builder.create();
				theAlert.show();
			}
			
		}
		
	};
	
	//delete all courses in view
	private void deleteAllCourseIDs() {
		courseScrollView.removeAllViews();
	}
	
	public OnClickListener deleteCourseIDButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			deleteAllCourseIDs();
			
			SharedPreferences.Editor preferencesEditor = courseIDsEntered.edit();
			preferencesEditor.clear();
			preferencesEditor.apply();
		}
		
		
	};

	public static String getJSONfromURL(String url) {

		// initialize
		InputStream is = null;
		String result = "";

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("LousList", "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("LousList", "Error converting result " + e.toString());
		}

		return result;
	}

	// The definition of our task class
	private class GetCoursesTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			ArrayList<Course> lcs = new ArrayList<Course>();

			try {

				String webJSON = getJSONfromURL(url);
				Log.d("JSON", webJSON);
				Gson gson = new Gson(); //Google's JSON parser

				JsonParser parser = new JsonParser();
				JsonArray Jarray = parser.parse(webJSON).getAsJsonArray();

				for (JsonElement obj : Jarray) {
					Course cse = gson.fromJson(obj, Course.class); //for each thing that 
					//I pull out of JSON (object, info about object) is an instance of a class
					
					Log.d("COURSE", cse.toString());
					lcs.add(cse);
				}

			} catch (Exception e) {
				Log.e("LousList", "JSONPARSE:" + e.toString());
			}

			values.clear();
			values.addAll(lcs);

			return "Done!";
		}

		@Override
		protected void onProgressUpdate(Integer... ints) {

		}

		@Override
		protected void onPostExecute(String result) {
			// tells the adapter that the underlying data has changed and it
			// needs to update the view
			adapter.notifyDataSetChanged();
		}
	}
    
}
