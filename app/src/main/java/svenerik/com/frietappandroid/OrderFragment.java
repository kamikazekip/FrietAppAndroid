package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import svenerik.com.frietappandroid.models.Order;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OrderFragment extends Fragment {

    private Order[] orders;
    private String user;
    private CustomAlert alertHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        alertHandler = new CustomAlert(this.getActivity());
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    public void receiveOrders(String ordersString, String user){
        this.user = user;
        JSONArray ordersJSON;
        try {
            ordersJSON = new JSONArray(ordersString);
            this.orders = new Order[ordersJSON.length()];
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
                this.orders[i] = new Order(_id, active, group_id, date, creator, snackbarName, snackbarUrl, snackbarPhone, dishes, this.user);
                this.orders[i].setOrderFragment(this);
            }
            createTableView(this.orders);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    }

    public void createTableView(Order[] orders){
        LinearLayout linearLayout = ((LinearLayout) this.getActivity().findViewById(R.id.linearOrders));
        linearLayout.removeAllViews();
        if(orders.length == 0){
            TextView textView = new TextView(this.getActivity());
            textView.setTextSize(30);
            textView.setText("Deze groep heeft nog geen sessies!");
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(textView);
        }
        for (int i = 0; i < orders.length; i++) {
            Button newButton = new Button(getActivity());
            newButton.setText(orders[i].creator + " - " + orders[i].niceDate);
            newButton.setTag(orders[i]);
            if(orders[i].active){
                newButton.setBackgroundColor(Color.parseColor("#ffffffff"));
                newButton.setTextColor(Color.parseColor("#ff0dd400"));
            } else {
                newButton.setBackgroundColor(Color.parseColor("#ffffffff"));
                newButton.setTextColor(getResources().getColor(android.R.color.black));
            }
            newButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Order clickedOrder = (Order) v.getTag();
                    clickedOrder.getDishes();
                    alertHandler.startActivityIndicator("Bestellingen ophalen");
                }
            });
            LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            newButton.setLayoutParams(rl);
            linearLayout.addView(newButton);
        }
    }

    public void success(String dishes, String snackbarPhone, String order_id){
        alertHandler.stopActivityIndicator();
        Intent i = new Intent(this.getActivity().getApplicationContext(), DishActivity.class);
        i.putExtra("dishes", dishes);
        i.putExtra("user", this.user);
        i.putExtra("snackbarPhone", snackbarPhone);
        i.putExtra("order_id", order_id);
        startActivity(i);
    }

    public void fail(){
        alertHandler.stopActivityIndicator();
        alertHandler.showAlert("Oeps!", "Er is iets mis gegaan tijdens het ophalen van de bestellingen!");
    }
}