package com.hankyjcheng.mycontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailActivity extends ActionBarActivity {

    private static String TAG = ContactActivity.class.getSimpleName();
    //private String urlJSONObject = "";
    private TextView streetTextView, cityTextView, emailTextView, websiteTextView;
    public static Contact contact;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        //urlJSONObject = contact.getDetailsURL();
        streetTextView = (TextView) findViewById(R.id.street_textView);
        cityTextView = (TextView) findViewById(R.id.city_textView);
        emailTextView = (TextView) findViewById(R.id.email_textView);
        websiteTextView = (TextView) findViewById(R.id.website_textView);

        TextView nameTextView = (TextView) findViewById(R.id.contact_name_textView);
        TextView companyTextView = (TextView) findViewById(R.id.company_name_textView);
        TextView workPhoneTextView = (TextView) findViewById(R.id.work_phone_textView);
        TextView homePhoneTextView = (TextView) findViewById(R.id.home_phone_textView);
        TextView mobilePhoneTextView = (TextView) findViewById(R.id.mobile_phone_textView);
        TextView birthdateTextView = (TextView) findViewById(R.id.birthdate_textView);

        nameTextView.setText(contact.getName());
        companyTextView.setText(contact.getCompany());
        workPhoneTextView.setText(contact.getWorkPhone());
        homePhoneTextView.setText(contact.getHomePhone());
        mobilePhoneTextView.setText(contact.getMobilePhone());
        birthdateTextView.setText(contact.getBirthdate());

        imageLoader = VolleySingleton.getInstance(this).getImageLoader();
        makeJSONObjectRequest();
    }

    private void makeJSONObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, contact.getDetailsURL(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject addressObject = response.getJSONObject("address");
                    String street = addressObject.getString("street");
                    String city = addressObject.getString("city");
                    String state = addressObject.getString("state");
                    String country = addressObject.getString("country");
                    String zip = addressObject.getString("zip");
                    String largeImageURL = response.getString("largeImageURL");
                    streetTextView.setText(street);
                    cityTextView.setText(city + ", " + state + " " + country + " " + zip);
                    NetworkImageView imageView = (NetworkImageView) findViewById(R.id.image);
                    imageView.setImageUrl(largeImageURL, imageLoader);

                    String email = response.getString("email");
                    emailTextView.setText(email);
                    String website = response.getString("website");
                    websiteTextView.setText(website);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    public void onContactClick(View view) {
        Intent myIntent = new Intent(this, ContactActivity.class);
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

}
