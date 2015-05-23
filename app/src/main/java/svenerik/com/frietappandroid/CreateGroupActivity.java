package svenerik.com.frietappandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import svenerik.com.frietappandroid.models.NewGroup;


public class CreateGroupActivity extends ActionBarActivity {

    private CustomAlert alertHandler;
    private String lastMadeGroup;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertHandler = new CustomAlert(this);
        this.user = getIntent().getStringExtra("user");
        setContentView(R.layout.activity_create_group);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
        return true;
    }

    public void onCreateGroup(View v){
        EditText groupnameField = (EditText) findViewById(R.id.groupNameField);
        String groupname = groupnameField.getText().toString();
        if(groupname.length() < 3 || groupname.length() > 25){
            alertHandler.showAlert("Oeps!", "De groepsnaam moet tussen de 3 en 25 karakters lang zijn!");
        } else {
            String newGroupName = groupname.replaceAll(" ", "%20");
            this.lastMadeGroup = groupname;
            NewGroup newGroup = new NewGroup(newGroupName, this.user);
            newGroup.setCreateGroupActivity(this);
            alertHandler.startActivityIndicator(groupname + " aanmaken");
            newGroup.create();
        }
    }

    public void success(String newGroup){
        alertHandler.stopActivityIndicator();
        alertHandler.showPositiveAlert("Gelukt!", "De groep " + this.lastMadeGroup + " is aangemaakt en toegevoegd aan uw account!");
    }

    public void fail(){
        alertHandler.stopActivityIndicator();
        alertHandler.showAlert("Oeps!", "Er ging iets mis tijdens het maken van de groep!");
    }
}
