package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Erik on 20-4-2015.
 */
public class User extends AsyncTask<String, String, JSONObject> {

    private String basicAuthHeader;
    private static final String TAG = User.class.getSimpleName();

    public User(String username, String password){
        basicAuthHeader = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
        bothConstructors();
    }

    public User(String basicAuthHeader){
        this.basicAuthHeader = basicAuthHeader;
        bothConstructors();
    }

    public void bothConstructors(){

    }

    public String getBasicAuthHeader(){
        return this.basicAuthHeader;
    }

    public void login(){
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        JSONParser jParser = new JSONParser();
        // Getting JSON from URL
        JSONObject json = jParser.getJSONFromUrl("https://desolate-bayou-9128.herokuapp.com/login", this.getBasicAuthHeader());
        return json;
    }
    @Override
    protected void onPostExecute(JSONObject json) {
        // Getting JSON Array
        String successful = json.toString();
        Log.i(TAG, successful);
    }
}

