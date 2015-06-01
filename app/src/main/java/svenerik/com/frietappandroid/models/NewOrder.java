package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import svenerik.com.frietappandroid.OrderFragment;

/**
 * Created by Erik on 1-6-2015.
 */
public class NewOrder extends AsyncTask<String, String, ResObject> {

    private String group_id;
    private String snackbar;
    private String url;
    private OrderFragment orderFragment;
    private String lastOperation;
    private String user;

    public NewOrder(String group_id, String snackbar, String url, String user){
        this.group_id = group_id;
        this.snackbar = snackbar;
        this.url = url;
        this.user = user;
    }

    public void setOrderFragment(OrderFragment orderFragment){
        this.orderFragment = orderFragment;
    }

    public void create(){
        NewOrder newOrder = new NewOrder(this.group_id, this.snackbar, this.url, this.user);
        newOrder.setOrderFragment(this.orderFragment);
        newOrder.lastOperation = "create";
        newOrder.execute(newOrder.group_id, newOrder.snackbar, newOrder.url, newOrder.lastOperation, newOrder.user);
    }

    @Override
    protected ResObject doInBackground(String... args) {
        JSONParser jParser = JSONParser.getInstance();
        // Getting JSON from URL
        String url = "https://desolate-bayou-9128.herokuapp.com/groups/" + args[0] + "/order";
        JSONObject json = null;
        try{
            json = new JSONObject();
            json.accumulate("snackbar", args[1]);
            json.accumulate("url", args[2]);
        } catch( JSONException e ){
            Log.i("JSONEXCEPTION: ", e.toString());
        }
        ResObject res = jParser.postJSONFromUrl(url, json, args[4], false);
        res.lastOperation = args[3];

        return res;
    }
    @Override
    protected void onPostExecute(ResObject res) {
        // Getting JSON Array
        if(res.hasJSON()){
            if(res.json == null){
                orderFragment.successCreate(res);
            } else {
                orderFragment.successCreate(res);
            }
        }  else {
            orderFragment.failCreate(res);
        }
    }
}