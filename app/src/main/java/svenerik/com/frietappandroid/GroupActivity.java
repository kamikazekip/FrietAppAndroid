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

import java.util.ArrayList;

import svenerik.com.frietappandroid.models.Group;


public class GroupActivity extends ActionBarActivity implements GroupFragment.OnItemSelectedListener {

    private ArrayList<Group> groups;
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
            groups = new ArrayList<Group>();
            for (int i = 0; i < groupsJSON.length(); i++) {
                groups.add(makeGroupFromJSON(groupsJSON.getJSONObject(i)));
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
        groupFrag.createTableView(this.groups, this.user);
    }

    private Group makeGroupFromJSON(JSONObject newGroup){
        try{
            orders = new String[newGroup.getJSONArray("orders").length()];
            for(int x = 0; x < newGroup.getJSONArray("orders").length(); x++){
                orders[x] = newGroup.getJSONArray("orders").getString(x);
            }
            users = new String[newGroup.getJSONArray("users").length()];
            for(int y = 0; y < newGroup.getJSONArray("users").length(); y++){
                users[y] = newGroup.getJSONArray("users").getString(y);
            }
            String _id = newGroup.getString("_id");
            String creator = newGroup.getString("creator");
            String name = newGroup.getString("name");
            Group newActualGroup = new Group(_id, creator, name, orders, users, this.user);
            return newActualGroup;
        } catch ( JSONException e ){
            Log.i("JSONEXCEPTION: ", e.toString());
        }
        return null;
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
                Intent i = new Intent(getApplicationContext(), CreateGroupActivity.class);
                i.putExtra("user", this.user);
                startActivityForResult(i, 1);
                return true;
            case(R.id.action_toSettings):
                Intent x = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(x);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onItemSelected(String orders, String group_id) {
        OrderFragment fragment = (OrderFragment) getFragmentManager().findFragmentById(R.id.orderFragment);
        if (fragment != null && fragment.isInLayout()) {
            fragment.receiveOrders(orders, this.user, group_id);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            intent.putExtra("orders", orders);
            intent.putExtra("user", this.user);
            intent.putExtra("group_id", group_id);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){

                String result = intent.getStringExtra("newGroup");
                try{
                    Group newGroup = makeGroupFromJSON(new JSONObject(result).getJSONObject("data"));
                    this.groups.add(newGroup);
                    GroupFragment groupFrag = (GroupFragment) getSupportFragmentManager().findFragmentById(R.id.groupFragment);
                    groupFrag.createTableView(this.groups, this.user);
                    Log.i("NewGroup: ", result);
                } catch ( JSONException e ) {
                    Log.i("JSONEXCEPTION: ", e.toString());
                }
            }
        }
    }
}
