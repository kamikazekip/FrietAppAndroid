package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;

import svenerik.com.frietappandroid.GroupActivity;
import svenerik.com.frietappandroid.GroupFragment;

/**
 * Created by Erik on 22-4-2015.
 */
public class Group extends AsyncTask<String, String, ResObject> {
    public String _id;
    public String creator;
    public String name;
    public String[] orders;
    public String[] users;
    public String user;
    private GroupFragment groupFragment;

    public Group(String _id, String creator, String name, String[] orders, String[] users, String user){
        this._id = _id;
        this.creator = creator;
        this.name = name;
        this.orders = orders;
        this.users = users;
        this.user = user;
    }

    public void setGroupFragment(GroupFragment groupFragment){
        this.groupFragment = groupFragment;
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
        String url = "https://desolate-bayou-9128.herokuapp.com/groups/" + this._id + "/orders";
        ResObject res = jParser.getJSONFromUrl(url, this.user, true);
        return res;
    }
    @Override
    protected void onPostExecute(ResObject res) {
        // Getting JSON Array
        if(res.hasJSON()){
            if(res.json == null){
                groupFragment.success(res.jsonArr.toString());
            } else {
                groupFragment.success(res.json.toString());
            }
        }  else {
            groupFragment.fail();
        }
    }
}
