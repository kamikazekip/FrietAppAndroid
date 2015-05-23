package svenerik.com.frietappandroid.models;

import android.os.AsyncTask;
import android.util.Log;

import svenerik.com.frietappandroid.CreateGroupActivity;

/**
 * Created by Erik on 23-5-2015.
 */
public class NewGroup extends AsyncTask<String, String, ResObject> {

    private String groupsName;
    private String user;
    private CreateGroupActivity createGroupActivity;

    public NewGroup(String groupsName, String user){
        this.groupsName = groupsName;
        this.user = user;
    }

    public void setCreateGroupActivity(CreateGroupActivity createGroupActivity){
        this.createGroupActivity = createGroupActivity;
    }

    public void create(){
        Log.i("ONZE GROTE VRIEND USER IS: ", this.user);
        NewGroup newGroup = new NewGroup(this.groupsName, this.user);
        newGroup.setCreateGroupActivity(this.createGroupActivity);
        newGroup.execute();
    }

    @Override
    protected ResObject doInBackground(String... args) {
        JSONParser jParser = JSONParser.getInstance();
        // Getting JSON from URL
        String url = "https://desolate-bayou-9128.herokuapp.com/groups/" + this.groupsName;
        Log.i("User: ", this.user);
        ResObject res = jParser.postJSONFromUrl(url, null, this.user, false);
        return res;
    }
    @Override
    protected void onPostExecute(ResObject res) {
        // Getting JSON Array
        if(res.hasJSON()){
            if(res.json == null){
                createGroupActivity.success(res.jsonArr.toString());
            } else {
                createGroupActivity.success(res.json.toString());
            }
        }  else {
            createGroupActivity.fail();
        }
    }
}
