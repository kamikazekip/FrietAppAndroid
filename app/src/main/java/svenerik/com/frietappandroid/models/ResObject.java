package svenerik.com.frietappandroid.models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Erik on 20-4-2015.
 */
public class ResObject {
    public JSONObject json;
    public int statusCode;
    public String lastOperation;
    public JSONArray jsonArr;
    public String group_id;

    public ResObject(JSONObject json, int statusCode){
        this.json = json;
        this.statusCode = statusCode;
    }

    public void setGroup_id(String group_id){
        this.group_id = group_id;
    }

    public String getGroup_id(){
        return this.group_id;
    }

    public ResObject(int statusCode){
        this.statusCode = statusCode;
    }

    public ResObject(JSONArray jsonArr, int statusCode){
        this.jsonArr = jsonArr;
        this.statusCode = statusCode;
    }

    public boolean hasJSON(){
        return json != null || jsonArr != null;
    }
}
