package com.hankyjcheng.mycontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends ActionBarActivity {

    private static String TAG = ContactActivity.class.getSimpleName();
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        contactAdapter = new ContactAdapter(this);
        makeJsonArrayRequest();

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(contactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                List<Contact> contactList = contactAdapter.getContactList();
                DetailActivity.contact = contactList.get(position);
                toContactDetail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    private void toContactDetail() {
        Intent myIntent = new Intent(this, DetailActivity.class);
        startActivity(myIntent);
    }

    private void makeJsonArrayRequest() {
        String urlJSONArray = "someURL.json";
        JsonArrayRequest request = new JsonArrayRequest(urlJSONArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Contact> contactList = new ArrayList<>();

                Log.d(TAG, response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        contactList.add(convertContact(response.getJSONObject(i)));
                    }
                    contactAdapter.setContactList(contactList);
                    contactAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private Contact convertContact(JSONObject object) {
        String smallImageURL = parseImageURLJSON(object);
        String name = parseNameJSON(object);
        String company = parseCompanyJSON(object);
        int id = parseEmployeeIdJSON(object);
        JSONObject phone;
        try {
            phone = object.getJSONObject("phone");
        } catch (JSONException e) {
            e.printStackTrace();
            phone = null;
        }
        String workPhone = parseWorkPhoneJSON(phone);
        String homePhone = parseHomePhoneJSON(phone);
        String mobilePhone = parseMobilePhoneJSON(phone);
        String detailsURL = parseDetailsURLJSON(object);
        String birthdate = parseBirthdateJSON(object);
        return new Contact(name, id, detailsURL, company, smallImageURL, birthdate, workPhone, homePhone, mobilePhone);
    }

    private String parseImageURLJSON(JSONObject object) {
        String smallImageURL = "";
        try {
            smallImageURL = object.getString("smallImageURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return smallImageURL;
    }

    private String parseNameJSON(JSONObject object) {
        String name = "";
        try {
            name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String parseCompanyJSON(JSONObject object) {
        String company = "";
        try {
            company = object.getString("company");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return company;
    }

    private int parseEmployeeIdJSON(JSONObject object) {
        int id = 0;
        try {
            id = object.getInt("employeeId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    private String parseWorkPhoneJSON(JSONObject phone) {
        String workPhone = "";
        try {
            workPhone = phone.getString("work");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return workPhone;
    }

    private String parseHomePhoneJSON(JSONObject phone) {
        String homePhone = "";
        try {
            homePhone = phone.getString("home");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return homePhone;
    }

    private String parseMobilePhoneJSON(JSONObject phone) {
        String mobilePhone = "";
        try {
            mobilePhone = phone.getString("mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mobilePhone;
    }

    private String parseDetailsURLJSON(JSONObject object) {
        try {
            return object.getString("detailsURL");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }

    private String parseBirthdateJSON(JSONObject object) {
        String birthdate = "";
        try {
            birthdate = object.getString("birthdate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return birthdate;
    }

}
