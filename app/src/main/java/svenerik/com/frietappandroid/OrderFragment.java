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

import java.util.ArrayList;

import svenerik.com.frietappandroid.models.NewOrder;
import svenerik.com.frietappandroid.models.Order;
import svenerik.com.frietappandroid.models.ResObject;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OrderFragment extends Fragment {

    private ArrayList<Order> orders;
    private String user;
    private String group_id;
    private CustomAlert alertHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        alertHandler = new CustomAlert(this.getActivity());
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    public void receiveOrders(String ordersString, String user, String group_id){
        this.user = user;
        this.group_id = group_id;
        JSONArray ordersJSON;
        try {
            ordersJSON = new JSONArray(ordersString);
            this.orders = new ArrayList<>();
            for (int i = 0; i < ordersJSON.length(); i++) {
                JSONObject mJsonObject = ordersJSON.getJSONObject(i);
                Order newOrder = makeOrder(mJsonObject);
                newOrder.setOrderFragment(this);
                this.orders.add(newOrder);
            }
            createTableView(this.orders);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    }

    public void createTableView(ArrayList<Order> orders){
        LinearLayout linearLayout = ((LinearLayout) this.getActivity().findViewById(R.id.linearOrders));
        linearLayout.removeAllViews();
        if(orders.size() == 0){
            TextView textView = new TextView(this.getActivity());
            textView.setTextSize(30);
            textView.setText("Deze groep heeft nog geen sessies!");
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(textView);
        }

        for (int i = 0; i < orders.size(); i++) {
            addOrderButton(orders.get(i));
        }
    }

    public Order makeOrder(JSONObject order){
        try {
            String _id = order.getString("_id");
            Boolean active = (order.getString("active") == "true");
            String someGroup_id = order.getString("group_id");
            String date = order.getString("date");
            String creator = order.getString("creator");
            JSONObject snackbar = order.getJSONObject("snackbar");
            String snackbarName = snackbar.getString("snackbar");
            String snackbarUrl = snackbar.getString("url");
            String snackbarPhone = snackbar.getString("telephone");
            String dishes[] = new String[order.getJSONArray("dishes").length()];
            for(int x = 0; x < order.getJSONArray("dishes").length(); x++){
                dishes[x] = order.getJSONArray("dishes").getString(x);
            }
            return new Order(_id, active, someGroup_id, date, creator, snackbarName, snackbarUrl, snackbarPhone, dishes, this.user);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return null;
    }

    public void addOrderButton(Order order){
        LinearLayout linearLayout = ((LinearLayout) this.getActivity().findViewById(R.id.linearOrders));
        Button newButton = new Button(getActivity());
        newButton.setText(order.creator + " - " + order.niceDate);
        newButton.setTag(order);
        if(order.active){
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

    public void onCreateNewOrder(){
        alertHandler.startActivityIndicator("Sessie aanmaken...");
        NewOrder newOrder = new NewOrder(this.group_id, "Overig", "Sorry, geen url", this.user);
        newOrder.setOrderFragment(this);
        newOrder.create();
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
        alertHandler.showAlert("Oeps!", "Er is iets mis gegaan tijdens het ophalen van de sessies!");
    }

    public void successCreate(ResObject res){
        alertHandler.stopActivityIndicator();
        if(res.hasJSON()){
            try{
                Log.i("JSON: ", res.json.toString());
                JSONObject newOrder = res.json.getJSONObject("order");
                Order order = makeOrder(newOrder);
                order.setOrderFragment(this);
                this.orders.add(order);
                this.createTableView(this.orders);
            } catch( JSONException e){
                Log.i("JSONEXCEPTION: ", e.toString());
            }
        }
    }

    public void failCreate(ResObject res){
        alertHandler.stopActivityIndicator();
        alertHandler.showAlert("Oeps!", "Er is iets mis gegaan tijdens het maken van de sessie!");
    }
}