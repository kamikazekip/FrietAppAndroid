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

import svenerik.com.frietappandroid.LoginActivity;
import svenerik.com.frietappandroid.RegisterActivity;

/**
 * Created by Erik on 20-4-2015.
 */
public class User extends AsyncTask<String, String, ResObject> {

    private String lastOperation;
    private String basicAuthHeader;
    private static final String TAG = User.class.getSimpleName();
    private LoginActivity loginActivity;
    private RegisterActivity registerActivity;
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        basicAuthHeader = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
    }

    public User(String basicAuthHeader){
        this.basicAuthHeader = basicAuthHeader;
    }

    public void setLoginActivity(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    public void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    public String getBasicAuthHeader(){
        return this.basicAuthHeader;
    }

    public void login(){
        User newUser = new User(this.basicAuthHeader);
        newUser.setLoginActivity(this.loginActivity);
        this.lastOperation = "login";
        newUser.execute(this.lastOperation);
    }

    public void register(){
        User newUser = new User(this.basicAuthHeader);
        newUser.setRegisterActivity(this.registerActivity);
        this.lastOperation = "register";
        Log.i("last: ", lastOperation);
        newUser.execute(this.lastOperation, this.username, this.password);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ResObject doInBackground(String... args) {
        JSONParser jParser = new JSONParser();
        // Getting JSON from URL
        ResObject res = null;
        Log.i("Last2: ", args[0]);
        if(args[0].equals("login")){
            res = jParser.getJSONFromUrlPOST("https://desolate-bayou-9128.herokuapp.com/login", this.getBasicAuthHeader(), false);
        } else if(args[0].equals("register")){
            try{
                JSONObject json = new JSONObject();
                json.accumulate("userame", args[1]);
                json.accumulate("password", args[2]);
                res = jParser.postJSONFromUrl("https://desolate-bayou-9128.herokuapp.com/users", json, null, false);
            } catch( JSONException e){
                Log.i("JSONEXCEPTION", e.toString());
            }
        }
        res.lastOperation = args[0];
        return res;
    }
    @Override
    protected void onPostExecute(ResObject res) {
        // Getting JSON Array
        switch(res.lastOperation){
            case("login"):
                if(res.hasJSON()){
                    loginActivity.successLogin(res.json.toString());
                }  else {
                    loginActivity.failedLogin();
                }
                break;
            case("register"):
                if(res.hasJSON()){
                    registerActivity.successRegister(res.json.toString());
                } else {
                    registerActivity.failedRegister();
                }
        }

    }
}

