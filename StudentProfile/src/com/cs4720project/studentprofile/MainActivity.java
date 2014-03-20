//Team Pepper, {Amas, Larsen, Seid}, Phase 2
package com.cs4720project.studentprofile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

//import com.cs4720project.studentprofile.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.cs4720project.studentprofile.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	ListView courseList; //NOT USED
	static String searchWebServiceURL = "http://plato.cs.virginia.edu/~cs4720s14pepper/view/CLAS/";
	ArrayList<Course> values = new ArrayList<Course>();
	ArrayAdapter<Course> adapter;

	public final static String COURSE_ID = "com.cs4720project.studentprofile.COURSEID";
	//TODO: check all package names and make sure to reflect correct package path everywhere. 
	
	private SharedPreferences courseIDsEntered;
	private ListView courseListView;
	private EditText courseIDEditText;
	Button enterCourseIDButton;
	Button deleteCourseIDButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("onCreate", "start up");
		setContentView(R.layout.activity_main);

		
		
		//retrieve saved courses if app closes
		courseIDsEntered = getSharedPreferences("courseIDList", MODE_PRIVATE);
		//				Log.d("initView", "init" );	
		courseListView = (ListView) findViewById(R.id.courseListView);
		courseIDEditText = (EditText) findViewById(R.id.courseIDEditText);

		//ENTER and DELETE (CLEAR) BUTTONS and LISTENERS
		enterCourseIDButton = (Button) findViewById(R.id.enterCourseIDButton);
		enterCourseIDButton.setOnClickListener(enterCourseIDButtonListener);

		deleteCourseIDButton = (Button) findViewById(R.id.deleteCourseIDButton);		
		deleteCourseIDButton.setOnClickListener(deleteCourseIDButtonListener);

		//Add saved courses to the "Course List" ScrollView
		//updateSavedCourseList(null);
		String url = searchWebServiceURL + "ECON"; //hard code for now
		
		Log.d("HTTP", url);
		adapter = new ArrayAdapter<Course>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		courseListView.setAdapter(adapter);
		
		new GetCoursesTask().execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_course_list, menu);
		return true;
	}

	//Adds a new course OR if null is passed the course list 
	//is updated with saved courses.
	private void updateSavedCourseList(String newCourseID) {
//		String [] courses = courseIDsEntered.getAll().keySet().toArray(new String[0]);

		if(newCourseID != null) {
			//then add to courseList ScrollView
			String [] courses = searchCourseID(newCourseID);
			Arrays.sort(courses, String.CASE_INSENSITIVE_ORDER); 		//sort them
			insertCourseIDInScrollView(newCourseID, Arrays.binarySearch(courses, newCourseID));
		
		//otherwise display the saved course list

		for(int i= 0; i < courses.length; i++) {
			insertCourseIDInScrollView(courses[i], i);
			Log.d("courses[i]", courses[i]);
			}
		}
	}
	
	private String[] searchCourseID(String newCourseID) {
		ArrayList<String> courses = new ArrayList<String>();
//		queryWebService(newCourseID);
		
		//String test = "[{\"courseID\":\"ANTH 2120\",\"courseName\":\"The Concept of Culture\",\"sectionNum\":100,\"courseInstructor\":\"Ira Bashkow\",\"meetString\":\"TuTh 11:00AM - 12:15PM\",\"meetRoom\":\"Dell 1 103\"},{\"courseID\":\"ASTR 4993\",\"courseName\":\"Tutorial\",\"sectionNum\":14,\"courseInstructor\":\"Scott Ransom\",\"meetString\":\"TBA\",\"meetRoom\":\"TBA\"}]";
		String webJSON = getJSONfromURL(searchWebServiceURL);
		Log.d("JSON", webJSON);	
		
		Gson gson = new Gson(); //Google's JSON parser

		JsonParser parser = new JsonParser();
		JsonArray Jarray = parser.parse(webJSON).getAsJsonArray();

		for (JsonElement obj : Jarray) {
			Course cse = gson.fromJson(obj, Course.class); //for each thing that 
			courses.add(cse.courseID);
			values.add(cse);
			//I pull out of JSON (object, info about object) is an instance of a class
			Log.d("COURSE", cse.toString());
//			lcs.add(cse);	
		}
		String[] answer = courses.toArray(new String[courses.size()]);
		return answer;
	}

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

	private class GetCoursesTask extends AsyncTask<String, Integer, String> {
		/*
		 * NEW
		 */
		ArrayList<String> courses = new ArrayList<String>();
		
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
				Gson gson = new Gson();

				JsonParser parser = new JsonParser();
				JsonArray Jarray = parser.parse(webJSON).getAsJsonArray();

				for (JsonElement obj : Jarray) {
					Course cse = gson.fromJson(obj, Course.class);
					/*
					 * NEW
					 */
					courses.add(cse.courseID);
					values.add(cse);
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
	
	
//	private String queryWebService(String newCourseID) {
//		
//		InputStream is = null;
//		String result = "";
//		
//		try {
//			
//		
//		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//		HttpPost httppost = new HttpPost(searchWebServiceURL + newCourseID);
//		httppost.setHeader("Content-type", "application/json");
//				
//		}
//		
//		catch (Exception e) {
//			Log.e("LousList", "Error in http: " + e.toString());
//		}
//		
//			HttpResponse response = httpclient.execute(httppost);
//			StatusLine statusLine = response.getStatusLine();
//			
//			int statusCode = statusLine.getStatusCode();
//			if (statusCode == 200) //SUCCESS
//			{
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//				Log.d("STATUSCODE", "200!!!!!!");
//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//				StringBuilder sb = new StringBuilder();
//				
//				String line = null;
//				
//				while ((line = reader.readLine()) != null) {
//					sb.append(line + "\n");
//				}
//				is.close();
//				result = sb.toString();
//				Log.d("result", result);
//
//			} else {
//				Log.d("JSON","Failed to download");
//			}
//		
//		} 
//		
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	//save course IDs
	private void saveCourseIDs(String newCourseID) {
		//check that it is a new courseID
		String isNewCourseID = courseIDsEntered.getString(newCourseID, null);
		
		//Editor stores key/value pair
		SharedPreferences.Editor preferencesEditor = courseIDsEntered.edit();
		preferencesEditor.putString(newCourseID, newCourseID);
		preferencesEditor.apply(); //@SuppressLint "NewApi" above to deal with API 9 vs 8
		
		if (isNewCourseID == null) {
			updateSavedCourseList(newCourseID);
		}
	}
	
	private void insertCourseIDInScrollView(String course, int arrayIndex) {
		//set/create a courseID row (course_id_row.xml) dynamically inside ScrollView 
		//every time a new CourseID is entered
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//inflate a new courseID row from course_id_row.xml
		View newCourseIDRow = inflater.inflate(R.layout.course_id_row, null);
		
		//TextView for ScrollView Row
		TextView courseRowTextView = (TextView) newCourseIDRow.findViewById(R.id.courseRowTextView);
		courseRowTextView.setText(course);		
		
		//buttons and listeners
		Button getCourseInfoButton = (Button) newCourseIDRow.findViewById(R.id.getCourseInfoButton);
		getCourseInfoButton.setOnClickListener(getCourseInfoListener);
		
		Button addCourseButton = (Button) newCourseIDRow.findViewById(R.id.addCourseButton);
		addCourseButton.setOnClickListener(addCourseButtonListener);
		
		courseListView.addView(newCourseIDRow, arrayIndex);	
	}
	
	//delete all courses in view
	private void deleteAllCourseIDs() {
		courseListView.removeAllViews();
	}
	
	/* ----------- BUTTON LISTENERS ---------------*/

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
				//ALERT DIALOG BOX: Missing CourseID, aka No Input
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle(R.string.invalid_course_ID);
				builder.setPositiveButton(R.string.OK_button, null);
				builder.setMessage(R.string.missing_course_ID);
				AlertDialog theAlert = builder.create();
				theAlert.show();
			}
		}
	};
	
	public OnClickListener deleteCourseIDButtonListener = new OnClickListener() {
		
		public void onClick(View v) {
			deleteAllCourseIDs();
			SharedPreferences.Editor preferencesEditor = courseIDsEntered.edit();
			preferencesEditor.clear();
			preferencesEditor.apply();
		}
	};
	
	public OnClickListener getCourseInfoListener = new OnClickListener() {
		
		public void onClick(View v) {
			//parsed Data
			TableRow tableRow = (TableRow) v.getParent();
			TextView courseIDTextView = (TextView) tableRow.findViewById(R.id.courseListTextView);
			String courseID = courseIDTextView.getText().toString();
			
			//new activity - new intent
			Intent intent = new Intent(MainActivity.this, CourseInfoActivity.class);
			
			//Add courseID to the intent
			intent.putExtra(COURSE_ID, courseID); //passes into the new activity
			startActivity(intent);
		}
	};
	
	public OnClickListener addCourseButtonListener = new OnClickListener() {

		public void onClick(View v) {
			//add course to list
			TableRow tableRow = (TableRow) v.getParent();
			TextView courseIDTextView = (TextView) tableRow.findViewById(R.id.courseListTextView);
			String courseID = courseIDTextView.getText().toString();
			
			//TODO: ADD courseID TO COURSE LIST
		}
	};    
}
