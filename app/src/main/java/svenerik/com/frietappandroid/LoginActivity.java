package svenerik.com.frietappandroid;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import svenerik.com.frietappandroid.models.User;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private CustomAlert alertHandler;
    private User userToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertHandler = new CustomAlert(this);
        // Create a new HttpClient and Post Header
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onLogin(View button){
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        validateLoginCorrectness(username, password);
    }

    public void validateLoginCorrectness(String username, String password){
        if(username.length() == 0 || password.length() == 0){
            alertHandler.showAlert("Oeps!", "Gebruikersnaam en wachtwoord mogen niet leeg zijn!");
        }  else {
            login(username, password);
        }
    }

    public void login(String username, String password){
        userToLogin = new User(username, password);
        userToLogin.setLoginActivity(this);
        alertHandler.startActivityIndicator("Inloggen :-)");
        userToLogin.login();
    }

    public void successLogin(String json){
        alertHandler.stopActivityIndicator();
        Intent i = new Intent(this, GroupActivity.class);
        i.putExtra("groups", json);
        startActivity(i);
    }

    public void failedLogin(){
        alertHandler.stopActivityIndicator();
        alertHandler.showAlert("Oeps!", "Ongeldige combinatie!");
    }
}
