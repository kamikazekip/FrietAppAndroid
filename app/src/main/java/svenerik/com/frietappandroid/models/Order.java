package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import svenerik.com.frietappandroid.OrderFragment;


/**
 * Created by Erik on 18-5-2015.
 */
public class Order extends AsyncTask<String, String, ResObject> {
    public String _id;
    public Boolean active;
    public String group_id;
    public Date date;
    public String creator;
    public String snackbarName;
    public String snackbarUrl;
    public String snackbarPhone;
    public String[] dishes;
    private OrderFragment orderFragment;
    private String user;

    public Order(String _id, Boolean active, String group_id, String date, String creator, String snackbarName, String snackbarUrl, String snackbarPhone, String[] dishes, String user){
        this._id = _id;
        this.active = active;
        this.group_id = group_id;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            this.date = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.creator = creator;
        this.snackbarName = snackbarName;
        this.snackbarUrl = snackbarUrl;
        this.snackbarPhone = snackbarPhone;
        this.dishes = dishes;
        this.user = user;
    }

    public void setOrderFragment(OrderFragment orderFragment){
        this.orderFragment = orderFragment;
    }

    public void getSessions(){
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ResObject doInBackground(String... args) {
        JSONParser jParser = new JSONParser();
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
                orderFragment.success(res.jsonArr.toString());
            } else {
                orderFragment.success(res.json.toString());
            }
        }  else {
            orderFragment.fail();
        }
    }
}