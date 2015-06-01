package svenerik.com.frietappandroid;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SettingsActivity extends ActionBarActivity {

    private Switch sortByActiveSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sortByActiveSwitch = (Switch) findViewById(R.id.switch1);
        SharedPreferences settings = getSharedPreferences("frietapp", 0);
        boolean sortByActive = settings.getBoolean("sortByActive", true);
        sortByActiveSwitch.setChecked(sortByActive);

        sortByActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getSharedPreferences("frietapp", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("sortByActive", isChecked);
                editor.commit();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }
}
