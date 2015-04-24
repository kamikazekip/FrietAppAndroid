package svenerik.com.frietappandroid.models;

/**
 * Created by Erik on 22-4-2015.
 */
public class Group {
    public String _id;
    public String creator;
    public String name;
    public String[] orders;
    public String[] users;

    public Group(String _id, String creator, String name, String[] orders, String[] users){
        this._id = _id;
        this.creator = creator;
        this.name = name;
        this.orders = orders;
        this.users = users;
    }
}
