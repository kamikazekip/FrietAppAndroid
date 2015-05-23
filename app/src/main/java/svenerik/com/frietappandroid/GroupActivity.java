package svenerik.com.frietappandroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import svenerik.com.frietappandroid.models.Group;


public class GroupActivity extends ActionBarActivity implements GroupFragment.OnItemSelectedListener {

    private Group[] groups;
    private String[] orders;
    private String[] users;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String groupsString = getIntent().getStringExtra("groups");
        this.user = getIntent().getStringExtra("user");
        JSONArray groupsJSON;
        try {
            groupsJSON = new JSONObject(groupsString).getJSONArray("groups");
            groups = new Group[groupsJSON.length()];
            for (int i = 0; i < groupsJSON.length(); i++) {
                JSONObject mJsonObject = groupsJSON.getJSONObject(i);
                orders = new String[mJsonObject.getJSONArray("orders").length()];
                for(int x = 0; x < mJsonObject.getJSONArray("orders").length(); x++){
                     orders[x] = mJsonObject.getJSONArray("orders").getString(x);
                }
                users = new String[mJsonObject.getJSONArray("users").length()];
                for(int y = 0; y < mJsonObject.getJSONArray("users").length(); y++){
                    users[y] = mJsonObject.getJSONArray("users").getString(y);
                }
                String _id = mJsonObject.getString("_id");
                String creator = mJsonObject.getString("creator");
                String name = mJsonObject.getString("name");
                Group newGroup = new Group(_id, creator, name, orders, users, this.user);
                groups[i] = newGroup;
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        createTableView();
    }

    public void createTableView(){
        GroupFragment groupFrag = (GroupFragment) getSupportFragmentManager().findFragmentById(R.id.groupFragment);
        groupFrag.createTableView(groups, user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case(R.id.action_toCreateGroup):

                return true;
            case(R.id.action_toSettings):

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onItemSelected(String orders) {
        OrderFragment fragment = (OrderFragment) getFragmentManager().findFragmentById(R.id.orderFragment);
        if (fragment != null && fragment.isInLayout()) {
            fragment.receiveOrders(orders, this.user);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            intent.putExtra("orders", orders);
            intent.putExtra("user", this.user);
            startActivity(intent);
        }
    }
}
