package svenerik.com.frietappandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import svenerik.com.frietappandroid.models.User;


public class RegisterActivity extends ActionBarActivity {

    private CustomAlert alertHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertHelper = new CustomAlert(this);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public void onRegister(View button){
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        EditText passwordRepeatField = (EditText) findViewById(R.id.passwordRepeatField);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String repeatPassword = passwordRepeatField.getText().toString();
        if(username.length() < 3 || password.length() < 3 || repeatPassword.length() < 3){
            alertHelper.showAlert("Oeps!", "Alle velden moeten minstens 3 karakters bevatten!");
        } else if(!password.equals(repeatPassword)){
            alertHelper.showAlert("Oeps!", "Het herhaalde wachtwoord komt niet overeen met het wachtwoord!");
        } else {
            alertHelper.startActivityIndicator(username + " registreren");
            User user = new User(username, password);
            user.setRegisterActivity(this);
            user.register();
        }
    }

    public void successRegister(String json){
        alertHelper.stopActivityIndicator();
        try {
            JSONObject newUser = new JSONObject(json);
            Boolean isSuccessfull = newUser.getBoolean("isSuccessfull");
            if(isSuccessfull){
                alertHelper.showPositiveAlert("Gelukt!", "Het registreren is gelukt");
            } else {
                alertHelper.showAlert("Oeps!", "Deze gebruikersnaam bestaat al!");
            }
        } catch( JSONException e){
            Log.i("JSONEXCEPTION ", e.toString());
        }
    }

    public void failedRegister(){
        alertHelper.stopActivityIndicator();
        alertHelper.showAlert("Oh jee!", "Er is iets fout gegaan tijdens het registreren!" );
    }

}
