package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import svenerik.com.frietappandroid.models.Group;
import svenerik.com.frietappandroid.models.NewGroup;
import svenerik.com.frietappandroid.models.Order;

public class OrderActivity extends ActionBarActivity {

    private String user;
    private Order[] orders;
    private String group_id;
    private OrderFragment orderFragment;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = getIntent().getExtras().getString("user");


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.activity_order);

        this.orderFragment = (OrderFragment) getFragmentManager().findFragmentById(R.id.orderFragment);
        if(getIntent().getExtras().getString("orders") != null && orderFragment != null){
            if(getIntent().getExtras().getString("group_id") != null){
                this.group_id = getIntent().getExtras().getString("group_id");
                this.orderFragment.receiveOrders(getIntent().getExtras().getString("orders"), this.user, this.group_id);
            }
        }
    }

    public void onCreateNewOrder(View v){
        orderFragment.onCreateNewOrder();
    }
}