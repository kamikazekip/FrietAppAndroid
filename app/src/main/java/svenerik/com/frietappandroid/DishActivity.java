package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import svenerik.com.frietappandroid.models.NewDish;
import svenerik.com.frietappandroid.models.ResObject;


public class DishActivity extends ActionBarActivity {

    public String snackbarPhone;
    private String order_id;
    private CustomAlert alertHelper;
    private String user;
    private DishFragment dishFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alertHelper = new CustomAlert(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        String dishes = getIntent().getExtras().getString("dishes");
        this.snackbarPhone = getIntent().getExtras().getString("snackbarPhone");
        this.order_id = getIntent().getExtras().getString("order_id");
        this.user = getIntent().getExtras().getString("user");
        this.dishFrag = (DishFragment) getSupportFragmentManager().findFragmentById(R.id.dishFragment);
        dishFrag.populateListView(dishes, this.user);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dish, menu);
        return true;
    }

    public void onPlaceDish(View button){
        EditText myEditText = (EditText) findViewById(R.id.dishTextField);
        if(myEditText.getText().toString().length() < 2){
            alertHelper.showAlert("Oeps!", "Een bestelling moet minsens 2 karakters bevatten!");
        } else {
            alertHelper.startActivityIndicator("Bestelling: " + myEditText.getText().toString() + " plaatsen...");
            NewDish newDish = new NewDish(myEditText.getText().toString(), this.user, this.order_id);
            newDish.setDishActivity(this);
            newDish.create();
        }
    }

    public void onTapMainView(View v){
        EditText myEditText = (EditText) findViewById(R.id.dishTextField);
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }

    public void success(ResObject response){
        alertHelper.stopActivityIndicator();
        try{
            if(response.lastOperation.equals("create")){
                successCreate(response.json.getJSONObject("dish"));
            } else if (response.lastOperation.equals("finish")){
                successFinish(response.json);
            }
        }
        catch( JSONException e){
            Log.i("JSONEXCEPTION", e.toString());
        }
    }

    public void successCreate(JSONObject response){

        this.dishFrag.addDish(response);
        this.dishFrag.makeListView();
    }

    public void successFinish(JSONObject response){

    }

    public void fail(ResObject res){
        alertHelper.stopActivityIndicator();
        if(res.lastOperation.equals("create")){
            alertHelper.showAlert("Oeps!", "Er ging iets mis tijdens het plaatsen van de bestelling!");
        } else if(res.lastOperation.equals("finish")){
            alertHelper.showAlert("Oeps!", "Er ging iets mis tijdens het afronden van deze sessie!");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + this.snackbarPhone));
            startActivity(callIntent);
            return true;
        }
        if (id == R.id.action_finish) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
