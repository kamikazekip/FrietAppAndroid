package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import svenerik.com.frietappandroid.models.Order;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OrderFragment extends Fragment {

    private Order[] orders;
    private String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    public void receiveOrders(String ordersString, String user){
        this.user = user;
        Log.i("HALLO", "LOLOL");
        JSONArray ordersJSON;
        try {
            Log.i("Orders: ", ordersString);
            ordersJSON = new JSONArray(ordersString);
            orders = new Order[ordersJSON.length()];
            for (int i = 0; i < ordersJSON.length(); i++) {
                JSONObject mJsonObject = ordersJSON.getJSONObject(i);
                String _id = mJsonObject.getString("_id");
                Boolean active = (mJsonObject.getString("active") == "true");
                String group_id = mJsonObject.getString("group_id");
                String date = mJsonObject.getString("date");
                String creator = mJsonObject.getString("creator");
                JSONObject snackbar = mJsonObject.getJSONObject("snackbar");
                String snackbarName = snackbar.getString("snackbar");
                String snackbarUrl = snackbar.getString("url");
                String snackbarPhone = snackbar.getString("telephone");
                String dishes[] = new String[mJsonObject.getJSONArray("dishes").length()];
                for(int x = 0; x < mJsonObject.getJSONArray("dishes").length(); x++){
                    dishes[x] = mJsonObject.getJSONArray("dishes").getString(x);
                }
                orders[i] = new Order(_id, active, group_id, date, creator, snackbarName, snackbarUrl, snackbarPhone, dishes, this.user);
            }
            Log.i("Orders", this.orders[0]._id);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    }

    public void success(String dishes){

    }

    public void fail(){

    }
}