//Team Pepper, {Amas, Larsen, Seid}, Phase 2
package com.cs4720project.studentprofile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cs4720project.studentprofile.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class CourseInfoActivity extends Activity {

	// private static final String TAG = "COURSE"; //for LogCat

	// TextViews used in layout: activity_course_info.xml
	TextView courseIDTextView;
	TextView courseNameTextView;
	TextView instructorTextView;
	TextView sectionNumTextView;
	TextView meetStringTextView;
	TextView meetRoomTextView;

	// Data to retrieve
	String courseID = "";
	String courseName = "";
	String instructor = "";
	String sectionNum = "";
	String meetString = "";
	String meetRoom = "";

	String url = "http://plato.cs.virginia.edu/~cs4720s14pepper/view/CLAS/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);

//		new MyAsyncTask().execute();

		// Intent intent = getIntent();
		// String stockSymbol = intent.getStringExtra(MainActivity.COURSE_ID);
		//
		// courseIDTextView = (TextView) findViewById(R.id.courseIDTextView);
		// courseNameTextView = (TextView)
		// findViewById(R.id.courseNameTextView);
		// instructorTextView = (TextView)
		// findViewById(R.id.instructorTextView);
		// sectionNumTextView = (TextView)
		// findViewById(R.id.sectionNumTextView);
		// meetStringTextView = (TextView)
		// findViewById(R.id.meetStringTextView);
		// meetRoomTextView = (TextView) findViewById(R.id.meetRoomTextView);
		//
		// Log.d(TAG, "Before URL Creation " + courseID);

		// build URL
		// final String theURL = urlFirst + courseID;

	}

//	private class MyAsyncTask extends AsyncTask<String, String, String> {
//
//		protected String doInBackground(String... params) {
//
//			DefaultHttpClient httpclient = new DefaultHttpClient(
//					new BasicHttpParams());
//			HttpPost httppost = new HttpPost(url);
//			httppost.setHeader("Content-type", "application/json");
//			InputStream is = null;
//			String result = null;
//
//			try {
//				HttpResponse response = httpclient.execute(httppost);
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(is, "UTF-8"));
//				StringBuilder sb = new StringBuilder();
//
//				String line = null;
//
//				while ((line = reader.readLine()) != null) {
//					sb.append(line + "\n");
//				}
//
//				result = sb.toString();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			finally {
//				try {
//					if (is != null)
//						is.close();
//				} catch (Exception e) {
//				}
//			}
//			JSONObject jsonObject; // holds KEY/VALUE pairs
//
//			try {
//
//				// String test =
//				// "{\"courseID\":\"This class satisfies the Second Writing Requiremen\",\"courseName\":\"This class satisfies the Second Writing Requiremen\",\"sectionNum\":1,\"courseInstructor\":\"Robert Kretsinger\",\"meetString\":\"MoWeFr 9:00AM - 9:50AM\",\"meetRoom\":\"Chemistry Bldg 260\"}";
//
//				// chop off "Searching for courses in CLAS"
//				// result = result.substring(29);
//
//				Log.v("JSONParser RESULT ", result);
//
//				jsonObject = new JSONObject(result);
//
//				Log.d("JSONParse", instructor);
//
//				// grab array
//				JSONArray queryArray = jsonObject.names(); // returns all
//				List<String> list = new ArrayList<String>();
//
//				for (int i = 0; i < queryArray.length(); i++) {
//					list.add(queryArray.getString(i));
//				}
//
//				for (String item : list) {
//					Log.v("JSON ARRAY ITEMS", "---");
//				}
//			}
//
//			catch (JSONException e) {
//				e.printStackTrace();
//			}
//
//			return result;
//		}
//
//		protected void onPostExecute(String result) {
//			TextView courseIDTextView = (TextView) findViewById(R.id.courseIDTextView);
//			TextView courseNameTextView = (TextView) findViewById(R.id.courseNameTextView);
//			TextView instructorTextView = (TextView) findViewById(R.id.instructorTextView);
//			TextView sectionNumTextView = (TextView) findViewById(R.id.sectionNumTextView);
//			TextView meetStringTextView = (TextView) findViewById(R.id.meetStringTextView);
//			TextView meetRoomTextView = (TextView) findViewById(R.id.meetRoomTextView);
//
//			courseIDTextView
//					.setText("Course: " + courseID);
//			courseNameTextView.setText(courseName);
//			instructorTextView.setText("Course: " + courseID + " : "
//					+ courseName);
//			sectionNumTextView.setText("Course: " + courseID + " : "
//					+ courseName);
//			meetStringTextView.setText("Course: " + courseID + " : "
//					+ courseName);
//			meetRoomTextView
//					.setText("Course: " + courseID + " : " + courseName);
//
//		}

}

