package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import svenerik.com.frietappandroid.DishActivity;

/**
 * Created by Erik on 23-5-2015.
 */
public class NewDish extends AsyncTask<String, String, ResObject> {

    private String dish;
    private String user;
    private String order_id;
    private DishActivity dishActivity;
    private String lastOperation;

    public NewDish(String dish, String user, String order_id){
        this.dish = dish;
        this.user = user;
        this.order_id = order_id;
    }

    public void setDishActivity(DishActivity dishActivity){
        this.dishActivity = dishActivity;
    }

    public void create(){
        NewDish newDish = new NewDish(this.dish, this.user, this.order_id);
        newDish.setDishActivity(this.dishActivity);
        newDish.lastOperation = "create";
        newDish.execute(newDish.dish, newDish.lastOperation, newDish.order_id);
    }

    @Override
    protected ResObject doInBackground(String... args) {
        JSONParser jParser = JSONParser.getInstance();
        // Getting JSON from URL
        String url = "https://desolate-bayou-9128.herokuapp.com/orders/" + args[2] + "/dish";
        JSONObject json = null;
        Log.i("User: ", this.user);
        try{
            json = new JSONObject();
            json.accumulate("dish", args[0]);
        } catch( JSONException e ){
            Log.i("JSONEXCEPTION: ", e.toString());
        }
        ResObject res = jParser.postJSONFromUrl(url, json, this.user, false);
        res.lastOperation = args[1];

        return res;
    }
    @Override
    protected void onPostExecute(ResObject res) {
        // Getting JSON Array
        if(res.hasJSON()){
            if(res.json == null){
               dishActivity.success(res);
            } else {
               dishActivity.success(res);
            }
        }  else {
            dishActivity.fail(res);
        }
    }
}
