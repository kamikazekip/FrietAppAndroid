package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import svenerik.com.frietappandroid.DishFragment;


/**
 * Created by Erik on 18-5-2015.
 */
public class Dish extends AsyncTask<String, String, ResObject> {
    public String _id;
    public String dish;
    public String order_id;
    public Date date;
    public String oldDate;
    public String niceDate;
    public String creator;

    private DishFragment dishFragment;
    private String user;

    public Dish(String dish, String order_id, String date, String _id, String creator, String user){
        this._id = _id;
        this.dish = dish;
        this.order_id = order_id;
        this.oldDate = date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            this.date = format.parse(date);
            format = new SimpleDateFormat("dd/MM/yyyy");
            this.niceDate = format.format(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.creator = creator;
        this.user = user;
    }

    public void setDishFragment(DishFragment dishFragment){
        this.dishFragment = dishFragment;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ResObject doInBackground(String... args) {
        JSONParser jParser = JSONParser.getInstance();
        // Getting JSON from URL
        String url = "https://desolate-bayou-9128.herokuapp.com/orders/" + this._id + "/dishes";
        ResObject res = jParser.getJSONFromUrl(url, this.user, true);
        return res;
    }
    @Override
    protected void onPostExecute(ResObject res) {
        // Getting JSON Array
        if(res.hasJSON()){
            if(res.json == null){
                dishFragment.success(res.jsonArr.toString());
            } else {
                dishFragment.success(res.json.toString());
            }
        }  else {
            dishFragment.fail();
        }
    }
}